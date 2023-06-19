package com.tw.article.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tw.article.dao.ArticleReportRepository;
import com.tw.article.model.ArticleReport;
import com.tw.article.service.ArticleReportSercive;

@Service
public class ArticleReportServiceImpl implements ArticleReportSercive {

    private final ArticleReportRepository articleReportRepository;

    public ArticleReportServiceImpl(ArticleReportRepository articleReportRepository) {
        this.articleReportRepository = articleReportRepository;
    }

    @Override
    public ArticleReport getArticleReportById(Integer id) {
        Optional<ArticleReport> optionalArticleReport = articleReportRepository.findById(id);
        return optionalArticleReport.orElse(null);
    }

    @Override
    public ArticleReport createArticleReport(ArticleReport articleReport) {
        return articleReportRepository.save(articleReport);
    }

    @Override
    public ArticleReport updateArticleReport(Integer id, ArticleReport articleReport) {
        Optional<ArticleReport> optionalArticleReport = articleReportRepository.findById(id);
        if (optionalArticleReport.isPresent()) {
            ArticleReport existingArticleReport = optionalArticleReport.get();
            // 更新現有文章檢舉的屬性
            existingArticleReport.setMemberId(articleReport.getMemberId());
            existingArticleReport.setArticleId(articleReport.getArticleId());
            existingArticleReport.setEmployeeId(articleReport.getEmployeeId());
            existingArticleReport.setArticleReportTime(articleReport.getArticleReportTime());
            existingArticleReport.setArticleReportReason(articleReport.getArticleReportReason());
            existingArticleReport.setArticleReportStatus(articleReport.getArticleReportStatus());
            // 儲存並返回更新後的文章檢舉
            return articleReportRepository.save(existingArticleReport);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteArticleReport(Integer id) {
        Optional<ArticleReport> optionalArticleReport = articleReportRepository.findById(id);
        if (optionalArticleReport.isPresent()) {
            articleReportRepository.delete(optionalArticleReport.get());
            return true;
        } else {
            return false;
        }
    }
}