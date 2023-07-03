package com.tw.member.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.core.impl.MementoMessage;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.member.controller.LoginMemberController.MemberDetail;
import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

@Service
public class LoginMemberService {
	@Autowired
	private MemberRepository memberRepository;

	// 查詢全部
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	public MemberDetail getItem(final int id) {
		final Member member = memberRepository.findByMemberId(id);

		if (member == null) {
			return null;
		}
		final MemberDetail memberDetail = new MemberDetail(member);

		return memberDetail;
	}

	// update
	public Member updateMember(int id, Member member) {
		Member existingMember = (Member) memberRepository.findByMemberId(id);
		if (existingMember != null) {
			existingMember.setMemberNameLast(member.getMemberNameLast());
			existingMember.setMemberNameFirst(member.getMemberNameFirst());
			existingMember.setMemberIdCard(member.getMemberIdCard());
			existingMember.setMemberBirth(member.getMemberBirth());
			existingMember.setMemberPhone(member.getMemberPhone());
			existingMember.setMemberGender(member.getMemberGender());
			existingMember.setMemberCity(member.getMemberCity());
			existingMember.setMemberDist(member.getMemberDist());
			existingMember.setMemberAddress(member.getMemberAddress());
			existingMember.setMemberEmail(member.getMemberEmail());
			return memberRepository.save(existingMember);
		}
		return null;
	}

	// get old pwd
	public boolean getPwd(int id, String password) {
		Member member = memberRepository.findByMemberId(id);
		if (password.equals(member.getMemberPassword())) {
			//System.out.println("get old pwd");
			return true;
		}
		//System.out.println("get none");
		return false;
	}

	// change password
	public void changePwd(int id, String password) {
		Member existingMember = memberRepository.findByMemberId(id);
		if (existingMember != null) {
			existingMember.setMemberPassword(password);
			memberRepository.save(existingMember);
			//System.out.println("changed pwd successfully");
		}
	}
	//讀圖片
	public byte[] getPic(int id) {
		Member member = memberRepository.findByMemberId(id);
		//如果資料庫沒有圖片
		if(member.getMemberPic() == null) {
			
			final ClassPathResource resource = new ClassPathResource("images/member.jpg");
			byte[] bytes = null;
			try (final InputStream is = resource.getInputStream();) {
				bytes = new byte[is.available()];
				is.read(bytes);

			} catch (final IOException e) {
				e.printStackTrace();
			}
			return bytes;
		}
		return member.getMemberPic();
	}

	// 存圖片
	public void savePic( int id, MultipartFile multipartFile) {
		try {
			Member image = memberRepository.findByMemberId(id);
			image.setMemberPic(multipartFile.getBytes());
			memberRepository.save(image);
		 
			//System.out.println("上傳照片上傳照片");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
