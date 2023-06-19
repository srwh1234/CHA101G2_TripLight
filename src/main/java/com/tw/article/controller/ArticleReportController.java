package com.tw.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.article.service.ArticleReportSercive;
import com.tw.article.model.ArticleReport;

@RestController
@RequestMapping("/article-reports")
public class ArticleReportController {
	
	private final ArticleReportSercive articleReportService;

    public ArticleReportController(ArticleReportSercive articleReportService) {
        this.articleReportService = articleReportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleReport> getArticleReportById(@PathVariable("id") Integer id) {
        ArticleReport articleReport = articleReportService.getArticleReportById(id);
        if (articleReport != null) {
            return ResponseEntity.ok(articleReport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<ArticleReport> createArticleReport(@RequestBody ArticleReport articleReport) {
        ArticleReport createdArticleReport = articleReportService.createArticleReport(articleReport);
        return ResponseEntity.ok(createdArticleReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleReport> updateArticleReport(
            @PathVariable("id") Integer id, @RequestBody ArticleReport articleReport) {
        ArticleReport updatedArticleReport = articleReportService.updateArticleReport(id, articleReport);
        if (updatedArticleReport != null) {
            return ResponseEntity.ok(updatedArticleReport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleReport(@PathVariable("id") Integer id) {
        boolean deleted = articleReportService.deleteArticleReport(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
	

