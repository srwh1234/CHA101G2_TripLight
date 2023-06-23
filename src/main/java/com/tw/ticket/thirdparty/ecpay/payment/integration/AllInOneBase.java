package com.tw.ticket.thirdparty.ecpay.payment.integration;

import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.tw.ticket.thirdparty.ecpay.payment.integration.ecpayOperator.EcpayFunction;
import com.tw.ticket.thirdparty.ecpay.payment.integration.errorMsg.ErrorMessage;
import com.tw.ticket.thirdparty.ecpay.payment.integration.exception.EcpayException;

@SuppressWarnings("all")
public class AllInOneBase {
	protected static String operatingMode;
	protected static String mercProfile;
	protected static String isProjectContractor;
	protected static String HashKey;
	protected static String HashIV;
	protected static String MerchantID;
	protected static String PlatformID;
	protected static String aioCheckOutUrl;
	protected static String doActionUrl;
	protected static String queryCreditTradeUrl;
	protected static String queryTradeInfoUrl;
	protected static String queryTradeUrl;
	protected static String tradeNoAioUrl;
	protected static String fundingReconDetailUrl;
	protected static String createServerOrderUrl;
	protected static Document verifyDoc;
	protected static String[] ignorePayment;

	public AllInOneBase() {

		final String confPath = "/payment_conf.xml";
		Document doc;
		/* when using web project*/
		// final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// final String configPath = URLDecoder.decode(classLoader.getResource("/payment_conf.xml").getPath(), "UTF-8");

		final URL fileURL = this.getClass().getResource(confPath);
		doc = EcpayFunction.xmlParser(fileURL.toString());

		/* when using testing code*/
		// final String paymentConfPath = "./src/main/resources/payment_conf.xml";
		// doc = EcpayFunction.xmlParser(paymentConfPath);

		doc.getDocumentElement().normalize();
		// OperatingMode
		Element ele = (Element) doc.getElementsByTagName("OperatingMode").item(0);
		operatingMode = ele.getTextContent();
		// MercProfile
		ele = (Element) doc.getElementsByTagName("MercProfile").item(0);
		mercProfile = ele.getTextContent();
		// IsProjectContractor
		ele = (Element) doc.getElementsByTagName("IsProjectContractor").item(0);
		isProjectContractor = ele.getTextContent();
		// MID, HashKey, HashIV, PlatformID
		NodeList nodeList = doc.getElementsByTagName("MInfo");
		for (int i = 0; i < nodeList.getLength(); i++) {
			ele = (Element) nodeList.item(i);
			if (ele.getAttribute("name").equalsIgnoreCase(mercProfile)) {
				MerchantID = ele.getElementsByTagName("MerchantID").item(0).getTextContent();
				HashKey = ele.getElementsByTagName("HashKey").item(0).getTextContent();
				HashIV = ele.getElementsByTagName("HashIV").item(0).getTextContent();
				PlatformID = isProjectContractor.equalsIgnoreCase("N") ? "" : MerchantID;
			}
		}
		// IgnorePayment
		ele = (Element) doc.getElementsByTagName("IgnorePayment").item(0);
		nodeList = ele.getElementsByTagName("Method");
		ignorePayment = new String[nodeList.getLength()];
		for (int i = 0; i < nodeList.getLength(); i++) {
			ignorePayment[i] = nodeList.item(i).getTextContent();
		}
		if (HashKey == null) {
			throw new EcpayException(ErrorMessage.MInfo_NOT_SETTING);
		}

	}
}
