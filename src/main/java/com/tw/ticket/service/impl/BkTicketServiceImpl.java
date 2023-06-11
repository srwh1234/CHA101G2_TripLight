package com.tw.ticket.service.impl;

import static com.tw.ticket.model.Ticket.DISABLED;
import static com.tw.ticket.model.TicketSn.NOT_USED;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tw.ticket.MyUtils;
import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.BkTicketController.TicketResponse;
import com.tw.ticket.controller.BkTicketController.TikcetDto;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketImage;
import com.tw.ticket.model.TicketSn;
import com.tw.ticket.model.TicketType;
import com.tw.ticket.model.dao.TicketImageRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.model.dao.TicketTypeRepository;
import com.tw.ticket.service.BkTicketService;

@Service
public class BkTicketServiceImpl implements BkTicketService {
	private static final Logger log = LoggerFactory.getLogger(BkTicketServiceImpl.class);

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private TicketTypeRepository ticketTypeRepository;

	@Autowired
	private TicketImageRepository ticketImageRepository;

	// 後台上下架票券
	@Override
	public boolean enableItems(final Map<String, Object> map) {
		final int ticketId = (int) map.get("ticketId");
		final int status = (int) map.get("status");
		final Ticket ticket = repository.findById(ticketId).orElse(null);

		if (ticket == null) {
			return false;
		}

		ticket.setStatus(status);
		repository.save(ticket);
		return true;
	}

	// 後台增加票券數量
	@Override
	public boolean addItemCount(final Map<String, Object> map) {
		final int ticketId = (int) map.get("ticketId");
		final int addCount = (int) map.get("addCount");
		if (addCount <= 0) {
			return false;
		}
		final Ticket ticket = repository.findById(ticketId).orElse(null);

		if (ticket == null) {
			return false;
		}
		// 隨機序號
		return createSerialNumber(ticket, addCount).size() > 0;
	}

	// 後台取得要編輯的票券
	@Override
	public TikcetDto getItem(final int ticketId) {
		final Ticket ticket = repository.findById(ticketId).orElse(null);

		if (ticket == null) {
			return null;
		}
		final TikcetDto dto = new TikcetDto();

		dto.setTicketId(ticketId);
		dto.setTicketType(ticket.getTicketType().getName());
		dto.setName(ticket.getName());
		dto.setPrice(ticket.getPrice());
		dto.setAvailable(0);
		dto.setTotalSales(ticket.getTotalSales());
		dto.setExpiryDate(ticket.getExpiryDate());
		dto.setDescription(ticket.getDescription());
		dto.setContent(ticket.getContent());
		dto.setNote(ticket.getNote());
		dto.setSupplierName(ticket.getSupplierName());
		dto.setCity(ticket.getCity());
		dto.setAddress(ticket.getAddress());
		dto.setLatitude(ticket.getLatitude());
		dto.setLongitude(ticket.getLongitude());
		dto.setRating(ticket.getRatingSum() / ticket.getRatingCount());
		dto.setRatingPerson(ticket.getRatingCount());

		// 圖片轉回base64
		final List<String> images = new ArrayList<>();
		for (final TicketImage image : ticket.getTicketImages()) {
			final String base64String = Base64.getEncoder().encodeToString(image.getImage());
			images.add(base64String);
		}
		dto.setImages(images);
		return dto;
	}

	// 後台編輯票券
	@Override
	public boolean updateItem(final TikcetDto dto) {

		// 判斷票券類型
		final TicketType type = ticketTypeRepository.findByName(dto.getTicketType());

		if (type == null) {
			return false;
		}
		// 檢查內容
		if (!isValidData(dto)) {
			return false;
		}

		final Ticket ticket = repository.findById(dto.getTicketId()).orElse(null);

		if (ticket == null) {
			return false;
		}

		ticket.setName(dto.getName());
		ticket.setTicketType(type);
		ticket.setPrice(dto.getPrice());
		ticket.setExpiryDate(dto.getExpiryDate());
		ticket.setDescription(dto.getDescription());
		ticket.setContent(dto.getContent());
		ticket.setNote(dto.getNote());
		ticket.setSupplierName(dto.getSupplierName());
		ticket.setCity(dto.getCity());
		ticket.setAddress(dto.getAddress());
		ticket.setRatingSum(dto.getRating() * dto.getRatingPerson());
		ticket.setRatingCount(dto.getRatingPerson());

		// TODO 經緯度
		if (ticket.getAddress() != dto.getAddress()) {
			ticket.setLatitude(24.9576355);
			ticket.setLongitude(121.2250227);
		}

		// 圖片SHA不相等才要更新
		boolean isUpdateImg = false;

		final String shaString = calcSha(dto);
		if (!ticket.getImageSha().equals(shaString)) {
			ticket.setImageSha(shaString);
			isUpdateImg = true;
		}

		// 更新票券
		repository.save(ticket);

		// 圖片SHA不相等才要更新

		if (isUpdateImg) {

			// 刪掉舊的
			ticketImageRepository.deleteAll(ticket.getTicketImages());

			// 加入新的圖片
			final List<TicketImage> ticketImages = new ArrayList<>();
			for (final String base64Str : dto.getImages()) {
				final byte[] array = Base64.getDecoder().decode(base64Str);

				final TicketImage image = new TicketImage();
				image.setTicketId(ticket.getTicketId());
				image.setImage(array);
				image.setUploadTime(new Timestamp(System.currentTimeMillis()));
				ticketImages.add(image);
			}
			ticketImageRepository.saveAll(ticketImages);
		}
		return true;
	}

	// 後台新增票券
	@Override
	public boolean addItems(final TikcetDto dto) {

		// 如果是0張
		if (dto.getAvailable() <= 0) {
			return false;
		}

		// 判斷票券類型
		final TicketType type = ticketTypeRepository.findByName(dto.getTicketType());

		if (type == null) {
			return false;
		}

		// 檢查內容
		if (!isValidData(dto)) {
			return false;
		}

		// 預設為未上架
		final Ticket ticket = new Ticket();

		ticket.setName(dto.getName());
		ticket.setTicketType(type);
		ticket.setStatus(DISABLED);
		ticket.setPrice(dto.getPrice());
		ticket.setTotalSales(dto.getTotalSales());
		ticket.setExpiryDate(dto.getExpiryDate());
		ticket.setDescription(dto.getDescription());
		ticket.setContent(dto.getContent());
		ticket.setNote(dto.getNote());
		ticket.setSupplierName(dto.getSupplierName());
		ticket.setCity(dto.getCity());
		ticket.setAddress(dto.getAddress());
		ticket.setLatitude(dto.getLatitude());
		ticket.setLongitude(dto.getLongitude());
		ticket.setRatingSum(dto.getRating() * dto.getRatingPerson());
		ticket.setRatingCount(dto.getRatingPerson());
		ticket.setImageSha(calcSha(dto));

		// TODO 經緯度
		if (ticket.getLatitude() <= 0 || ticket.getLongitude() <= 0) {
			ticket.setLatitude(24.9576355);
			ticket.setLongitude(121.2250227);
		}

		// 新增票券
		repository.save(ticket);

		// 隨機序號
		createSerialNumber(ticket, dto.getAvailable());

		// 圖片
		final List<TicketImage> ticketImages = new ArrayList<>();
		for (final String base64Str : dto.getImages()) {
			final byte[] array = Base64.getDecoder().decode(base64Str);

			final TicketImage image = new TicketImage();
			image.setTicketId(ticket.getTicketId());
			image.setImage(array);
			image.setUploadTime(new Timestamp(System.currentTimeMillis()));
			ticketImages.add(image);
		}

		ticketImageRepository.saveAll(ticketImages);

		return true;
	}

	// 後台票券清單 (分頁)
	@Override
	public SearchResponse getItems(final SearchRequest request) {
		final Pageable pageable = PageRequest.of(	//
				request.getPage(),		// 查詢的頁數，從0起算
				request.getSize()		// 查詢的每頁筆數
		);
		final Page<Ticket> page = repository.searchTicketByKeyword(	//
				request.getKeyword(),	// 關鍵字
				pageable				// 分頁物件
		);
		// 轉成自己定義的物件
		final SearchResponse response = new SearchResponse();
		response.setCurPage(request.getPage());
		response.setTotalPage(page.getTotalPages());

		page.getContent().forEach(ticket -> {

			final int available = snRepository.countUsableSn(ticket.getTicketId());

			final TicketResponse ticketResponse = new TicketResponse();
			ticketResponse.setTicketId(ticket.getTicketId());
			ticketResponse.setTicketName(ticket.getName());
			ticketResponse.setTicketType(ticket.getTicketType().getName());
			ticketResponse.setTicketStatus(ticket.getStatus());
			ticketResponse.setTicketCity(ticket.getCity());
			ticketResponse.setSupplierName(ticket.getSupplierName());
			ticketResponse.setAvailable(available);
			response.getTickets().add(ticketResponse);

		});
		return response;
	}

	// ----------------------------------------------------------------------------------

	// 計算上傳圖片的SHA
	private String calcSha(final TikcetDto dto) {
		String hexString = "";
		try {
			// 使用SHA1 雖然不完全安全 但比SHA3快
			final MessageDigest digest = MessageDigest.getInstance("SHA-1");
			for (final String base64Str : dto.getImages()) {
				final byte[] array = Base64.getDecoder().decode(base64Str);
				digest.update(array);
			}
			hexString = new BigInteger(1, digest.digest()).toString(16);

		} catch (final NoSuchAlgorithmException e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return hexString;
	}

	// 票券用的隨機序號
	private List<TicketSn> createSerialNumber(final Ticket ticket, final int createCount) {
		final List<TicketSn> result = new ArrayList<>();
		for (int i = 0; i < createCount; i++) {
			final TicketSn ticketSn = new TicketSn();
			ticketSn.setTicket(ticket);

			final String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20).toUpperCase();
			ticketSn.setSerialNumber(uuId);
			ticketSn.setStatus(NOT_USED);
			result.add(ticketSn);
		}
		return snRepository.saveAll(result);
	}

	// 檢查規格
	private boolean isValidData(final TikcetDto dto) {
		// 價格
		if (dto.getPrice() <= 0) {
			return false;
		}
		// 到期日
		if (MyUtils.isBeforeNow(dto.getExpiryDate())) {
			return false;
		}

		// 預設評價
		if (dto.getRating() <= 0 || dto.getRatingPerson() <= 0) {
			return false;
		}

		// 空字串判斷
		if (MyUtils.isEmpty(dto.getName())					// 名稱
				|| MyUtils.isEmpty(dto.getDescription()) 	// 票券描述
				|| MyUtils.isEmpty(dto.getContent()) 		// 票券說明
				|| MyUtils.isEmpty(dto.getNote()) 			// 注意事項
				|| MyUtils.isEmpty(dto.getSupplierName())	// 供應商名稱
				|| MyUtils.isEmpty(dto.getCity()) 			// 縣市
				|| MyUtils.isEmpty(dto.getAddress())) { 	// 地址
			return false;
		}
		return true;
	}

}
