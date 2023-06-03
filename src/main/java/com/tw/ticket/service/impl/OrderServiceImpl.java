package com.tw.ticket.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tw.ticket.service.OrderService;
import com.tw.ticket.thirdparty.ecpay.payment.integration.AllInOne;
import com.tw.ticket.thirdparty.ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class OrderServiceImpl implements OrderService {

	@Override
	public String ecpayCheckout() {
		final String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

		final AllInOne all = new AllInOne("");

		final AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuId);
		obj.setMerchantTradeDate("2023/06/03 08:05:23");
		obj.setTotalAmount("50");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setReturnURL("http://localhost:8080/callback");
		obj.setClientBackURL("http://localhost:8080/front-end/tickets_order.html");
		obj.setNeedExtraPaidInfo("Y");
		final String form = all.aioCheckOut(obj, null);

		return form;
	}

}
