package com.auth.net.commons.authorize.net;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.authorize.Environment;
import net.authorize.api.contract.v1.GetSettledBatchListRequest;
import net.authorize.api.contract.v1.GetSettledBatchListResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.controller.GetSettledBatchListController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetSettledBatchList {
	public static final String apiLoginId= "72mNC7gyq";
	public static final String transactionKey= "8W6YC22g58PrkEvA";

	public static void main(String[] args) throws ParseException, DatatypeConfigurationException {
		GregorianCalendar gc=new GregorianCalendar();
		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		MerchantAuthenticationType merchantAuthenticationType= new MerchantAuthenticationType() ;
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		GetSettledBatchListRequest getRequest = new GetSettledBatchListRequest();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		Date firstSettlementDate = df.parse("2015-01-26");
		gc.setTime(firstSettlementDate);
		
		Date lastSettlementDate = df.parse("2015-05-05");
		gc.setTime(lastSettlementDate);
		
		getRequest.setFirstSettlementDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
		getRequest.setLastSettlementDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
		getRequest.setMerchantAuthentication(merchantAuthenticationType);
		GetSettledBatchListController controller = new GetSettledBatchListController(getRequest);
		controller.execute();
		
		GetSettledBatchListResponse getResponse = new GetSettledBatchListResponse();
		if (getResponse!=null) {

			if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {
				System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
				System.out.println(getResponse.getMessages().getMessage().get(0).getText());
			}
			else{
				System.out.println("Failed to get settled batch list:  " + getResponse.getMessages().getResultCode());
			}
		}
	}
}
