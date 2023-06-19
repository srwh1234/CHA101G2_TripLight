package com.tw.vendor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;
import com.tw.vendor.dao.VendorRepository;
import com.tw.vendor.model.Vendor;

@Service
public class VendorLoginServicer {
	@Autowired
	private VendorRepository vendorRepository;


	
	public boolean register(String email, String account, String password) {
        Vendor existingMember = vendorRepository.findByVendorEmail(email);
        if (existingMember != null) {
            return false; // 用戶名已存在
        }
        Vendor vendor = new Vendor();
        vendor.setVendorEmail(email);
        vendor.setLoginAccount(account);
        vendor.setLoginPassword(password);
        vendorRepository.save(vendor);
        
        return true; // 註冊成功
    }

    public Vendor login(String email, String password) {
    	Vendor vendor =  vendorRepository.findByVendorEmail(email);
        if (vendor != null && password.equals(vendor.getLoginAccount())) {
            return vendor; // 登入成功
        }
        return null; // 登入失敗
    }
	
//	public Member login(String email) {
//		
//		Member data = memberRepository.findByMemberEmail(email);
//		if(data == null) {
//			System.out.println("無此帳號");
//			return null;
//		}else {
//			System.out.println("成功登入");
//			Member member = new Member();
//			member.setMemberEmail(data.getMemberEmail());
//			member.setMemberPassword(data.getMemberPassword());
//			return member;
//			}
//		if(data != null) {
//			if(.equals(data.getMemberPassword())) return null;
//			
//		}
		
		// 取得對應Member 資料
//		Member member = memberService.findMemberByMa_id(data.getId());
//		if(member == null) return null;
//		
//		// 組合資料為MemberAccountVO
//		MemberAccountVO memberAccountVO = new MemberAccountVO();
//		memberAccountVO.setUsername(memberAccount.getUsername());
//		memberAccountVO.setName(member.getName());
//		return memberAccountVO;
//	}

//	public Optional<String> register(MemberAccountVO memberAccountVO) {
//		// TODO Auto-generated method stub
//		// 驗證欄位是否填寫及格式
//		if(!ValidFormat.isEmail(memberAccountVO.getUsername())) return Optional.of("帳號必須是Email 格式");
//		if(!ValidFormat.isPassword(memberAccountVO.getPassword())) return Optional.of("密碼必須為長度6~16位碼大小寫英文加數字");
//		if(!memberAccountVO.getPassword().equals(memberAccountVO.getCheckPassword())) return Optional.of("兩次輸入密碼不相符");
//		
//		// 檢查帳號是否重複註冊
//		MemberAccount data = memberAccountDao.findMemberAccountByUsername(memberAccountVO.getUsername());
//		if(data != null) return Optional.of("該帳號已被使用");
//		
//		// 產生鹽值
//		String salt = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
//		
//		// 密碼加密
//		String md5Password = getMd5Password(memberAccountVO.getPassword(), salt);
//
//		// 新增MemberAccount 資料
//		MemberAccount memberAccount = new MemberAccount();
//		memberAccount.setUsername(memberAccountVO.getUsername());
//		memberAccount.setPassword(md5Password);
//		memberAccount.setSalt(salt);
//		memberAccount.setCreate_by(memberAccountVO.getUsername());
//		memberAccount.setUpdate_by(memberAccountVO.getUsername());
//		Integer id = memberAccountDao.insert(memberAccount);
//		if(id == 0) return Optional.of("新增會員帳號時發生錯誤");
//
//		// 新增Member 資料
//		Member member = new Member();
//		member.setMa_id(String.valueOf(id));
//		member.setName(memberAccountVO.getName());
//		member.setCreate_by(memberAccountVO.getUsername());
//		member.setUpdate_by(memberAccountVO.getUsername());
//		Integer result = memberService.insert(member);
//		if(result == 0) return Optional.of("新增會員資料時發生錯誤");
//		
//		return Optional.empty();
//	}

//	@Override
//	public MemberAccount findMemberAccountByUsername(String username) {
//		// TODO Auto-generated method stub
//		return memberAccountDao.findMemberAccountByUsername(username);
//	}

}
