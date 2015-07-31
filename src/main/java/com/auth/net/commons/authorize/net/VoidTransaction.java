package com.auth.net.commons.authorize.net;

import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Result;
import net.authorize.aim.Transaction;

public class VoidTransaction {
	private static final String apiLoginID ="72mNC7gyq";
	private static final String transactionKey ="8W6YC22g58PrkEvA";
	
	public static void main(String[] args) {
		Merchant merchant = Merchant.createMerchant(Environment.SANDBOX, apiLoginID, transactionKey);
		
		BigDecimal totalAmount = new BigDecimal("15.00");
		net.authorize.aim.Transaction voidTransaction = merchant.createAIMTransaction(
				TransactionType.VOID, totalAmount);
		voidTransaction.setTransactionId("2237721860");
		
		@SuppressWarnings("unchecked")
		Result<Transaction> result = (Result<Transaction>)merchant.postTransaction(voidTransaction);
		System.out.println("Result : ["+result.getResponseText()+"]");
		System.out.println("Reason Response Code : ["+ result.getReasonResponseCode()+"]");
		System.out.println("Response Code : ["+result.getResponseCode()+"]");
		System.out.println("Response Text : ["+result.getResponseText()+"]");
		
		System.out.println("Transaction ID : ["+result.getTarget().getTransactionId()+"]");
		System.out.println("Transaction Type : ["+result.getTarget().getTransactionType()+"]");
	}

}
