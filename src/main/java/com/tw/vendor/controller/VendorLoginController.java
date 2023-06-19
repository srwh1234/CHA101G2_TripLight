//package com.tw.vendor.controller;
//
//import javax.sound.midi.Soundbank;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.support.SessionStatus;
//
//import com.tw.vendor.dao.VendorRepository;
//import com.tw.vendor.service.VendorLoginServicer;
//
//import jakarta.servlet.http.HttpSession;
//
//@RestController
//public class VendorLoginController {
//	private final VendorRepository vendorRepository;
//
//	@Autowired
//	public VendorLoginController(final VendorRepository vendorRepository) {
//		this.vendorRepository = vendorRepository;
//	}
//	@Autowired
//	public VendorLoginServicer vendorLoginService;
//
////	@PostMapping("/login")
////	public String login(@RequestParam final String email, @RequestParam final String password, final HttpSession session) {
////		final Member member = memberRepository.findMemberByMemberEmail(email);
////		if (member != null) {
////			session.setAttribute("member", member);
////			return "redirect:/home";
////		}
////		return "login";
////	}
////
////	@PostMapping("/register")
////	public Boolean register(@RequestParam final String account, @RequestParam final String email, @RequestParam final String password) {
////		try {
////			// var member = new Member();
////			// member.setMemberAccount(account);
////			// member.setMemberEmail(email);
////			// member.setMemberPassword(password);
////			// memberRepository.save(member);
////			// System.out.println("儲存成功：" + member);
////			return true;
////		} catch (final Exception e) {
////			return false;
////		}
////	}
////	@PostMapping("/login")
////	public String login (@RequestBody String email, @RequestBody String password, HttpSession session) {
////		if(email == login.)
////		return null;
////	}
////	@PostMapping("/login")
////	public String login(@RequestBody String email, @RequestBody String password, HttpSession session) {
////
////		Member result = loginService.login(email);
////		if(result == null) {
////			System.out.println("沒有此帳號");;
////			return "redirect:login";
////		}
////		// 設置Session
////		session.setAttribute("member", result);
////		return "login";
////	}
////	@PostMapping("/register")
////    public String registerMember(@RequestParam("email") String email,
////                                 @RequestParam("password") String password,
////                                 RedirectAttributes redirectAttributes) {
////        boolean registrationStatus = memberService.register(email, password);
////        if (registrationStatus) {
////            redirectAttributes.addFlashAttribute("successMessage", "註冊成功");
////        } else {
////            redirectAttributes.addFlashAttribute("errorMessage", "用戶名已存在");
////        }
////
////        return "redirect:/registration-status";
////    }
//
//	@PostMapping("/register")
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
//	@GetMapping("/logout")
//	public String logout(HttpSession session, SessionStatus sessionStatus) {
//
//		if(session.getAttribute("vendor") != null){
//			session.removeAttribute("vendor");
//			sessionStatus.setComplete();
//		}
//		return "redirect:/vendor-end/login.html";
//	}
//
//}
//
