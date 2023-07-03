package com.tw.article.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tw.article.dao.ArticleRepository;
import com.tw.article.model.Article;

@Component  // 給Spring IoC 託管
public class ArticleTest implements CommandLineRunner { 
	// 這個class會在伺服器部屬完執行(main方法)

    @Autowired   // 注入articleRepository實例(dao)
    private ArticleRepository articleRepository;

    @Override
    public void run(String... args){
//        var article = articleRepository.findAll();
//        //System.out.println(article); // 顯示查詢到的資料
    }
    
//    public static void main(String[] args) {
//    	
//    }
    
}
