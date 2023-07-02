package com.tw.article.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.article.dao.ArticleMessageRepository;
import com.tw.article.model.Article;
import com.tw.article.model.ArticleMessage;
import com.tw.article.service.ArticleMessageService;
import com.tw.member.model.Member;
import com.tw.member.model.dao.MemberRepository;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/articleMessage")
public class ArticleMessageController {

	@Autowired
	private ArticleMessageService articleMessageService;

	@Autowired
	private ArticleMessageRepository articleMessageRepository;

	@Autowired
	private MemberRepository memberRepository;

	// 處理 GET 請求，顯示所有文章留言
	@GetMapping("/articleMessages")
	public List<DataMessage> getAllArticleMessages() {

		List<DataMessage> result = new ArrayList<>();

		for (ArticleMessage articleMessage : articleMessageService.getAllArticleMessages()) {

			Member member = memberRepository.findByMemberId(articleMessage.getMemberId());

			if (member != null) {
				DataMessage dataMessage = new DataMessage(articleMessage);
				dataMessage.setMemberName(member.getMemberNameFirst() + member.getMemberNameLast());
				result.add(dataMessage);
			}
		}
		return result;
	}

	// 處理 GET 請求，顯示單個文章留言詳細資訊
	@GetMapping("/{id}")
	public String getArticleMessageById(@PathVariable("id") int id, Model model) {
		String articleMessage = articleMessageService.getArticleMessageById(id);
		model.addAttribute("articleMessage", articleMessage);
		return "articleMessageDetail"; // 返回 articleMessageDetail.jsp 或其他顯示留言詳細資訊的視圖
	}

	// 處理 POST 請求，新增文章留言
	@PostMapping("/addmessage")
	public ArticleMessage addmessage(@RequestParam("message") String jsonString) {

		ArticleMessage message = null;

		try {
			message = new ObjectMapper().readValue(jsonString, ArticleMessage.class);
			// service
//			ArticleMessage msg;			
//			articleMessageRepository.save(msg);
//			dao.save (msg)
			message.setMessagePostTime(new Timestamp(System.currentTimeMillis()));
			articleMessageService.save(message);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return message;
	}

//  @Data
//  private static class DtoMessage {
//	  private Integer   articleMessageId;
//	  private Integer  articleId;
//	  private Integer  memberId;
//	  private String  messagePostContent;
//	  private Date  messagePostTime;
//	 // private Integer  messagePrevious;
//	  private Integer  messageStatus;

	// 處理 POST 請求，更新文章留言
	@PostMapping("/updatearticlemessage")
	public ArticleMessage updaterticleMessage(@PathVariable("id") int id,
			@ModelAttribute("articleMessage") @RequestParam("articleMessage") String jsonString) {

		ArticleMessage message = null;

		try {
			message = new ObjectMapper().readValue(jsonString, ArticleMessage.class);
			ArticleMessage aMessage = articleMessageService.findbyId(message.getArticleMessageId());

			if (aMessage != null) {
				aMessage.setMessagePostcontent(message.getMessagePostcontent());
				aMessage.setMessagePostTime(message.getMessagePostTime());
				aMessage.setMessageStatus(message.getMessageStatus());
				articleMessageService.save(aMessage);
				message = aMessage;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	// 處理 POST 請求，刪除文章留言
	@PostMapping("/deleteArticleMessage/{id}/")
	public boolean deleteArticleMessage(@PathVariable Integer id) {
		ArticleMessage articlemessage = articleMessageService.findbyId(id);
		articlemessage.setMessageStatus(1);
		articleMessageService.save(articlemessage);
		return true;
	}

	// 處理 GET 請求，顯示單篇文章的所有留言
	@GetMapping("/messages/{articleId}")
	public List<DataMessage> findByArticleId(@PathVariable int articleId) {

		List<DataMessage> result = new ArrayList<>();

		List<ArticleMessage> Messages = articleMessageService.getAllArticleMessages();

		for (ArticleMessage articleMessage : Messages) {
			if (articleMessage.getArticleId() == articleId) {
				Member member = memberRepository.findByMemberId(articleMessage.getMemberId());

				if (member != null) {
					DataMessage dataMessage = new DataMessage(articleMessage);
					dataMessage.setMemberName(member.getMemberNameFirst() + member.getMemberNameLast());
					result.add(dataMessage);
				}
			}
		}
		return result;
	}

	@Data
	public static class DataMessage {

		public DataMessage(ArticleMessage articleMessage) {
			this.articleId = articleMessage.getArticleId();
			this.articleMessageId = articleMessage.getArticleMessageId();
//			this.memberId = articleMessage.getMemberId();
			this.messagePostContent = articleMessage.getMessagePostcontent();
			this.messagePostTime = articleMessage.getMessagePostTime();
			this.messagePreviousId = articleMessage.getMessagePreviousId();
			this.messageStatus = articleMessage.getMessageStatus();
//			this.articlePicture = article.getArticlePicture();
		}

//		public void setMemberName(String string) {
//			
//		}

		private Integer articleMessageId;
		private String memberName;
		private Integer articleId;
//		private String articleTitle;
		private String messagePostContent;
		private Timestamp messagePostTime;
		private Integer messagePreviousId;
		private Integer messageStatus;
//		private byte[] articlePicture;
	}

}