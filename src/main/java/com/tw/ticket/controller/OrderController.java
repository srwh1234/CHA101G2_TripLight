package com.tw.ticket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tw.ticket.service.OrderService;

import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	// 訂單清單
	@PostMapping("/ticketorders")
	public OrderPageResponse ticketOrders(@RequestBody final OrderRequest request) {
		return orderService.getItems(request);
	}

	// 結帳(test)
	@GetMapping("/testCheckout")
	public boolean testCheckout(//
			@RequestParam("memberId") final int memberId,//
			@RequestParam("couponId") final int couponId) {
		return orderService.testOrder(memberId, couponId);
	}

	/**
	 * 安全碼 : 222
	 * 一般信用卡測試卡號 : 4311-9522-2222-2222
	 */
	// 付款介面新增訂單(綠界)
	@PostMapping("/ecpayCheckout")
	@ResponseBody
	public String ecpayCheckout(@RequestBody final Map<String, Object> map) {
		return orderService.ecpayCheckoutMake(map);
	}

	// 付款介面現有訂單(綠界)
	@PostMapping("/ecpayCheckoutorder")
	@ResponseBody
	public String ecpayCheckoutOrder(@RequestBody final Map<String, Object> map) {
		return orderService.ecpayCheckoutOrder(map);
	}

	/**
	 * 綠界回傳的參數有這些
	 * {AlipayID=, AlipayTradeNo=, amount=50, ATMAccBank=, ATMAccNo=, auth_code=777777, card4no=2222,
	 * card6no=431195, CustomField1=, CustomField2=, CustomField3=, CustomField4=, eci=0, ExecTimes=,
	 * Frequency=, gwsr=12687927, MerchantID=2000132, MerchantTradeNo=544cb316c0a44e5f99b7, PayFrom=,
	 * PaymentDate=2023/06/04 20:16:57, PaymentNo=, PaymentType=Credit_CreditCard,
	 * PaymentTypeChargeFee=5, PeriodAmount=, PeriodType=, process_date=2023/06/04 20:16:57, red_dan=0,
	 * red_de_amt=0, red_ok_amt=0, red_yet=0, RtnCode=1, RtnMsg=������\, SimulatePaid=0, staed=0,
	 * stage=0, stast=0, StoreID=, TenpayTradeNo=, TotalSuccessAmount=, TotalSuccessTimes=, TradeAmt=50,
	 * TradeDate=2023/06/04 20:16:05, TradeNo=2306042016059597, WebATMAccBank=, WebATMAccNo=,
	 * WebATMBankName=, CheckMacValue=F0E7662E986E40A503D16F81E64827ED8BCECFFF2DE9DE91DE70227E96F067C3}
	 *
	 * XXX 需要Https才收的到callback
	 */
	// 結帳(綠界)
	@PostMapping("/callback")
	public ResponseEntity<String> handleCallback(@RequestParam final Map<String, String> map) {
		final int memberId = Integer.parseInt(map.get("CustomField1"));
		final int couponId = Integer.parseInt(map.get("CustomField2"));
		final int orderId = Integer.parseInt(map.get("CustomField3"));
		final int rtnCode = Integer.parseInt(map.get("RtnCode"));
		final String payType = map.get("PaymentType");

		// 結帳
		if (rtnCode == 1) {
			orderService.ecpayConfirm(memberId, orderId, payType);
		}
		return ResponseEntity.ok("OK");
	}

	// 定義請求物件
	@Data
	public static class OrderRequest {
		private int memberId;
		private int page;
		private int size;
	}

	// 定義回傳物件
	@Data
	public static class OrderPageResponse {
		private int curPage;
		private int totalPage;
		private ArrayList<OrderResponse> orders = new ArrayList<>();
	}

	@Data
	public static class OrderResponse {
		private int ticketOrderId;
		private int ticketCount;
		private int couponId;
		private String couponName;
		private Timestamp payDate;
		private String payType;
		private int actualPrice;
	}
}
