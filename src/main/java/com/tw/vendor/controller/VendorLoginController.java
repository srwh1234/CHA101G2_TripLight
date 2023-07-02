package com.tw.vendor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.tw.vendor.controller.model.VendorLoginRequest;
import com.tw.vendor.model.Vendor;
import com.tw.vendor.service.VendorLoginService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class VendorLoginController {


	public final VendorLoginService vendorLoginService;
	
	@PostMapping("/vendor/login")
	public Integer vendorlogin(@RequestBody VendorLoginRequest request  , HttpSession httpSession) {
		
		System.out.println(request);
		Vendor result = vendorLoginService.vendorlogin(request.getVendorEmail(), request.getLoginAccount(), request.getLoginPassword());
		if(result == null) {
			System.out.println("查無此帳號");
			return 0;
		} else {
			//設置session
			httpSession.setAttribute("vendor", result);
			System.out.println("VendorId" + httpSession.getAttribute("vendor"));
			System.out.println("登入成功");
			 System.out.println(httpSession.getId());
			return 1; 
		}
		
	}
	
	@GetMapping("/logout3")
	public String logout(HttpSession session, SessionStatus sessionStatus) {

		if(session.getAttribute("vendor") != null){
			session.removeAttribute("vendor");
			sessionStatus.setComplete();
		}
		return "redirect:/vendor-end/login.html";
	}

}

