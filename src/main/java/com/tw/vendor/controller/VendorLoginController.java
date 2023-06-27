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
			httpSession.getServletContext().setAttribute("vendor", result);
			System.out.println("VendorId" + httpSession.getServletContext().getAttribute("vendor"));
			System.out.println("登入成功");
			 System.out.println(httpSession.getId());
			return 1; 
		}
		
	}
	
//	@PostMapping("/login")
//	public String login(@RequestParam final String email, @RequestParam final String password, final HttpSession session) {
//		final Member member = memberRepository.findMemberByMemberEmail(email);
//		if (member != null) {
//			session.setAttribute("member", member);
//			return "redirect:/home";
//		}
//		return "login";
//	}
//
//	@PostMapping("/register")
//	public Boolean register(@RequestParam final String account, @RequestParam final String email, @RequestParam final String password) {
//		try {
//			// var member = new Member();
//			// member.setMemberAccount(account);
//			// member.setMemberEmail(email);
//			// member.setMemberPassword(password);
//			// memberRepository.save(member);
//			// System.out.println("儲存成功：" + member);
//			return true;
//		} catch (final Exception e) {
//			return false;
//		}
//	}
//	@PostMapping("/login")
//	public String login (@RequestBody String email, @RequestBody String password, HttpSession session) {
//		if(email == login.)
//		return null;
//	}
//	@PostMapping("/login")
//	public String login(@RequestBody String email, @RequestBody String password, HttpSession session) {
//
//		Member result = loginService.login(email);
//		if(result == null) {
//			System.out.println("沒有此帳號");;
//			return "redirect:login";
//		}
//		// 設置Session
//		session.setAttribute("member", result);
//		return "login";
//	}
//	@PostMapping("/register")
//    public String registerMember(@RequestParam("email") String email,
//                                 @RequestParam("password") String password,
//                                 RedirectAttributes redirectAttributes) {
//        boolean registrationStatus = memberService.register(email, password);
//        if (registrationStatus) {
//            redirectAttributes.addFlashAttribute("successMessage", "註冊成功");
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "用戶名已存在");
//        }
//
//        return "redirect:/registration-status";
//    }

//	@PostMapping("/register2")
//	public String register(@RequestParam String email, @RequestParam String password, @RequestParam String account) {
//
//		boolean registerStatus = vendorLoginService.register(email, password, account);
//		if(registerStatus) {
//			System.out.println("success");
//			return "註冊成功";
//		}else {
//			System.out.println("fail");
//			return "redirect:/front-end/supplier_form.html";
//		}
//	}
	@GetMapping("/logout3")
	public String logout(HttpSession session, SessionStatus sessionStatus) {

		if(session.getAttribute("vendor") != null){
			session.removeAttribute("vendor");
			sessionStatus.setComplete();
		}
		return "redirect:/vendor-end/login.html";
	}

}

