package com.tw.article.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tw.article.model.ArticleMessage;
import com.tw.article.service.ArticleMessageService;

@Controller
@RequestMapping("/articleMessage")
public class ArticleMessageController {
  
  @Autowired
  private ArticleMessageService articleMessageService;
  
  // 處理 GET 請求，顯示所有文章留言
  @GetMapping("/")
  public String getAllArticleMessages(Model model) {
    List<ArticleMessage> articleMessages = articleMessageService.getAllArticleMessages();
    model.addAttribute("articleMessages", articleMessages);
    return "articleMessageList"; // 返回 articleMessageList.jsp 或其他顯示留言的視圖
  }
  
  // 處理 GET 請求，顯示單個文章留言詳細資訊
  @GetMapping("/{id}")
  public String getArticleMessageById(@PathVariable("id") int id, Model model) {
    String articleMessage = articleMessageService.getArticleMessageById(id);
    model.addAttribute("articleMessage", articleMessage);
    return "articleMessageDetail"; // 返回 articleMessageDetail.jsp 或其他顯示留言詳細資訊的視圖
  }
  
  // 處理 POST 請求，新增文章留言
  @PostMapping("/")
  public String addArticleMessage(@ModelAttribute("articleMessage") ArticleMessage articleMessage) {
    articleMessageService.addArticleMessage(articleMessage);
    return "redirect:/articleMessage/"; // 重新導向到所有文章留言的頁面
  }
  
  // 處理 POST 請求，更新文章留言
  @PostMapping("/{id}")
  public String updateArticleMessage(@PathVariable("id") int id, @ModelAttribute("articleMessage") ArticleMessage articleMessage) {
    articleMessage.setArticleMessageId(id);
    articleMessageService.updateArticleMessage(articleMessage);
    return "redirect:/articleMessage/"; // 重新導向到所有文章留言的頁面
  }
  
  // 處理 POST 請求，刪除文章留言
  @PostMapping("/{id}/delete")
  public String deleteArticleMessage(@PathVariable("id") int id) {
    articleMessageService.deleteArticleMessage(id);
    return "redirect:/articleMessage/"; // 重新導向到所有文章留言的頁面
  }
}
