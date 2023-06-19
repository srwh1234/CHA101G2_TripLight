package com.tw.ticket.service.impl;

import static com.tw.ticket.model.Ticket.DISABLED;
import static com.tw.ticket.model.TicketSn.NOT_USED;
import static com.tw.ticket.service.impl.ImageServiceImpl.IMG_URL;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.tw.ticket.Config;
import com.tw.ticket.MyUtils;
import com.tw.ticket.controller.BkTicketController.SearchResponse;
import com.tw.ticket.controller.BkTicketController.TicketResponse;
import com.tw.ticket.controller.BkTicketController.TikcetDto;
import com.tw.ticket.controller.TicketController.SearchRequest;
import com.tw.ticket.model.Ticket;
import com.tw.ticket.model.TicketSn;
import com.tw.ticket.model.TicketType;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.ticket.model.dao.TicketSnRepository;
import com.tw.ticket.model.dao.TicketTypeRepository;
import com.tw.ticket.service.BkImageService;
import com.tw.ticket.service.BkTicketService;

@Service
public class BkTicketServiceImpl implements BkTicketService {

	private static final Logger log = LoggerFactory.getLogger(BkImageServiceImpl.class);

	@Autowired
	private Config config;

	@Autowired
	private GeoApiContext geoApiContext;

	@Autowired
	private TicketRepository repository;

	@Autowired
	private TicketSnRepository snRepository;

	@Autowired
	private TicketTypeRepository ticketTypeRepository;

	@Autowired
	private BkImageService bkImageService;

	@Autowired
	private ImageServiceImpl imageService;

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

		// 圖片url
		final List<String> images = imageService.findImgUrls(ticketId);
		images.remove(IMG_URL + 0);
		if (!images.isEmpty()) {
			dto.setImages(images);
		}

		return dto;
	}

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

		ticket.setRatingSum(dto.getRating() * dto.getRatingPerson());
		ticket.setRatingCount(dto.getRatingPerson());

		// TODO 經緯度
		if (ticket.getAddress() != dto.getAddress()) {

			final LatLng latLng = getLatLngFromAddress(dto.getAddress());
			if (latLng == null) {
				ticket.setLatitude(24.9576355);
				ticket.setLongitude(121.2250227);
			} else {
				ticket.setLatitude(latLng.lat);
				ticket.setLongitude(latLng.lng);
			}
			ticket.setAddress(dto.getAddress());
		}

		// 更新票券
		repository.save(ticket);
		return true;
	}

	// 後台新增票券(form)
	@Override
	public boolean addItems(final String jsonString, final MultipartFile[] files) {
		TikcetDto dto = null;
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			dto = objectMapper.readValue(jsonString, TikcetDto.class);

		} catch (final JsonProcessingException e) {
			return false;
		}

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

		// TODO 經緯度
		if (!MyUtils.isEmpty(dto.getAddress())) {
			final LatLng latLng = getLatLngFromAddress(dto.getAddress());
			if (latLng == null) {
				ticket.setLatitude(24.9576355);
				ticket.setLongitude(121.2250227);
			} else {
				ticket.setLatitude(latLng.lat);
				ticket.setLongitude(latLng.lng);
			}
		}

		// 新增票券
		repository.save(ticket);

		// 隨機序號
		createSerialNumber(ticket, dto.getAvailable());

		// 加入新的圖片
		bkImageService.addImages(ticket.getTicketId(), files);
		return true;
	}

	// ----------------------------------------------------------------------------------

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

	// 使用Google Api取得指定地址的經緯度
	private LatLng getLatLngFromAddress(final String address) {
		try {
			final GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();

			if (results != null && results.length > 0) {
				return results[0].geometry.location;
			}
		} catch (final Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return null;
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
