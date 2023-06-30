package com.tw.vendor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class BKVendorLogoutController {
	
	@GetMapping("/bkvendorlogout")
	public String logoout(HttpServletRequest request, HttpServletResponse response) {
		// 清除
	    HttpSession session = request.getSession();
	    if (session != null) {
	        session.invalidate();
	    }

	    return "redirect:/vendor-end/login.html";
	}

}
