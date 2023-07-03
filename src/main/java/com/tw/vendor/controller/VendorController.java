package com.tw.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.form.service.EmailSenderService;
import com.tw.form.util.HTMLFormat;
import com.tw.ticket.Config;
import com.tw.vendor.dao.VendorRepository;
import com.tw.vendor.model.Vendor;
import com.tw.vendor.service.VendorService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RestController
public class VendorController {

	@Autowired
	private VendorService vendorService;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private EmailSenderService emailSenderService; // 寄信用

	@Autowired
	private Config config;

	@GetMapping("/vendors")  // 對應前端get, 傳資料給前端
	public List<Vendor> getveVendors() {
		return vendorService.findAll();
	}

	// @GetMapping("/vendors/info")
	// public Vendor getVendorInfo(HttpSession httpSession) {
	// Vendor vendor = (Vendor) httpSession.getServletContext().getAttribute("vendor");
	// //System.out.println(vendor +httpSession.getId());
	// return vendor;
	// }

	@GetMapping("/vendors/info")
	public Vendor getVendorInfo(final HttpSession httpSession) {
		final Vendor vendor = (Vendor) httpSession.getAttribute("vendor");
		return vendor;
	}

	@PostMapping("/vendors")
	public String processVendor(@RequestBody final Vendor vendor) {
		//System.out.println("Get");
		vendorService.save(vendor);
		final String htmlFormat = HTMLFormat.getHTMLFormat("TripLight廠商申請表單", "blue", vendor.toString(), "前往後臺審核!", config.getEcpayReturnUrl()
				+ "/back-end/index.html");
		emailSenderService.sendHTMLEmail("triplight0411@gmail.com", "廠商申請表單", htmlFormat); // 寄信
		return "成功拿到資料";
	}

	// 更改狀態- 啟用
	@PutMapping("/vendors1/{vendorId}")
	public String enableVendor(@PathVariable final int vendorId) throws MessagingException {
		final Vendor vendor = vendorService.findById(vendorId);
		vendor.setReview(1);
		vendorService.save(vendor);
		final String message = "您的註冊email為：%s<br>您的帳號為：%s<br>您的密碼為：%s";
		final String content = String.format(message, vendor.getVendorEmail(), vendor.getLoginAccount(), vendor.getLoginPassword());
		final String htmlFormat = HTMLFormat.getHTMLFormat("恭喜TripLight廠商帳號啟用成功", "green", content, "前往登入!", config.getEcpayReturnUrl()
				+ "/vendor-end/login.html");
		emailSenderService.sendHTMLEmail(vendor.getVendorEmail(), "恭喜TripLight廠商帳號啟用成功", htmlFormat);
		return "廠商啟用成功";
	}

	// 更改狀態- 停權
	@PutMapping("/vendors2/{vendorId}")
	public String suspensionVendor(@PathVariable final int vendorId) throws MessagingException {
		final Vendor vendor = vendorService.findById(vendorId);
		vendor.setReview(2);
		vendorService.save(vendor);
		final String content = "您的廠商帳號已被停權，如有問題請聯繫客服<br>客服電話：0987987987";
		final String htmlFormat = HTMLFormat.getHTMLFormat("TripLight廠商帳號停權通知", "red", content, "聯繫客服", config.getEcpayReturnUrl()
				+ "/front-end/index.html");
		emailSenderService.sendHTMLEmail(vendor.getVendorEmail(), "TripLight廠商帳號停權通知", htmlFormat);
		return "廠商停權成功";
	}

}
