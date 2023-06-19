package com.tw.form.controller;


import com.tw.ai.repository.AiFavoriteRepository;
import com.tw.article.dao.ArticleRepository;
import com.tw.form.dto.CountData;
import com.tw.member.model.dao.MemberRepository;
import com.tw.ticket.model.dao.TicketRepository;
import com.tw.trip.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountController {
    private MemberRepository memberRepository;
    private TripRepository tripRepository;
    private TicketRepository ticketRepository;
    private ArticleRepository articleRepository;

    private AiFavoriteRepository aiFavoriteRepository;
    private CountData countData;

    @Autowired
    public CountController(MemberRepository memberRepository, TripRepository tripRepository, TicketRepository ticketRepository,ArticleRepository articleRepository,AiFavoriteRepository aiFavoriteRepository,CountData countData) {
        this.memberRepository = memberRepository;
        this.tripRepository = tripRepository;
        this.ticketRepository = ticketRepository;
        this.articleRepository = articleRepository;
        this.aiFavoriteRepository = aiFavoriteRepository;
        this.countData = countData;
    }


    @GetMapping("/counts")
    public CountData processCount(){
        countData.setMemberNumber((int) memberRepository.count());
        countData.setTripNumber((int) tripRepository.count());
        countData.setTicketNumber((int) ticketRepository.count());
        countData.setArticleNumber((int) articleRepository.count());
        countData.setAiFavoriteNumber((int)aiFavoriteRepository.count());
        return countData;
    }


}
