package com.tw.ai.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tw.ai.service.AiService;
import com.tw.member.dao.MemberRepository;
import com.tw.ticket.service.TicketService;
import com.tw.trip.repository.TripRepository;
import com.tw.trip.service.TripService;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

	private final TripRepository tripRepository;
	private final TicketService ticketService;
	private final AiService aiService;

	private final TripService tripService;

	private final MemberRepository memberRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public MyCommandLineRunner(final TripRepository tripRepository, final TicketService ticketService, final AiService aiService,
			final TripService tripService, final MemberRepository memberRepository) {
		this.tripRepository = tripRepository;
		this.ticketService = ticketService;
		this.aiService = aiService;
		this.tripService = tripService;
		this.memberRepository = memberRepository;
	}

	@Override
	public void run(final String... args) {
		logger.info("伺服器部屬完畢");
	}
}
