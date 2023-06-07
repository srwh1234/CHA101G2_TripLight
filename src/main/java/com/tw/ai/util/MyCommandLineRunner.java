package com.tw.ai.util;

import com.tw.ai.service.AiService;
import com.tw.member.dao.MemberRepository;
import com.tw.member.model.Member;
import com.tw.trip.service.TripService;
import com.tw.ticket.service.TicketService;
import com.tw.trip.repository.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripRepository tripRepository;
    private TicketService ticketService;
    private AiService aiService;

    private TripService tripService;

    private MemberRepository memberRepository;
    private final Logger logger
            = LoggerFactory.getLogger(this.getClass());

    public MyCommandLineRunner(TripRepository tripRepository, TicketService ticketService, AiService aiService,TripService tripService,MemberRepository memberRepository) {
        this.tripRepository = tripRepository;
        this.ticketService = ticketService;
        this.aiService = aiService;
        this.tripService = tripService;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args)  {
        var member = new Member();
        member.setMemberEmail("y2sssssss");
        member.setMemberPassword("asdasdasdasd");
        member.setMemberAccount("jay");
        memberRepository.save(member);
        logger.info("伺服器部屬完畢");
    }
}
