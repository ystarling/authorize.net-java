package com.auth.net.commons.authorize.net;

import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.api.contract.v1.BankAccountType;
import net.authorize.api.contract.v1.BankAccountTypeEnum;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionResponse;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class CreditBankAccount {
	public static final String apiLoginId= "72mNC7gyq";
	public static final String transactionKey= "8W6YC22g58PrkEvA";

	public static void main(String[] args) {
		//Common code to set for all requests
		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		// Populate the payment data
		PaymentType paymentType = new PaymentType();
		BankAccountType bankAccountType = new BankAccountType();
		bankAccountType.setAccountType(BankAccountTypeEnum.CHECKING);
		bankAccountType.setRoutingNumber("125000024");
		bankAccountType.setAccountNumber("12345678");
		bankAccountType.setNameOnAccount("John Doe");
		paymentType.setBankAccount(bankAccountType);

		// Create the payment transaction request
		TransactionRequestType txnRequest = new TransactionRequestType();
		txnRequest.setTransactionType(TransactionTypeEnum.REFUND_TRANSACTION.value());
		txnRequest.setRefTransId("2148889729");
		txnRequest.setPayment(paymentType);
		txnRequest.setAmount(new BigDecimal(500.00));

		// Make the API Request
		CreateTransactionRequest apiRequest = new CreateTransactionRequest();
		apiRequest.setTransactionRequest(txnRequest);
		CreateTransactionController controller = new CreateTransactionController(apiRequest);
		controller.execute();


		CreateTransactionResponse response = controller.getApiResponse();

		if (response!=null) {

			// If API Response is ok, go ahead and check the transaction response
			if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

				TransactionResponse result = response.getTransactionResponse();
				if (result.getResponseCode().equals("1")) {
					System.out.println(result.getResponseCode());
					System.out.println("Successful Credit Bank Transaction");
					System.out.println(result.getAuthCode());
					System.out.println(result.getTransId());
				}else{
					System.out.println("Failed Transaction"+result.getResponseCode());
				}
			}else{
				System.out.println("Failed Transaction:  "+response.getMessages().getResultCode());
			}
		}
	}
}
