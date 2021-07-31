package com.revaturebank;

import com.revature.controller.BankController;
import com.revature.controller.BankControllerImpl;
import com.revature.controller.VerificationController;
import com.revature.controller.VerificationControllerImpl;
import com.revaturebank.service.BankService;

import io.javalin.Javalin;

public class MainDriver {
	
	public static void main(String[] args) {
		
		final String CUSTOMER_BY_ID_PATH = "/customer/:id";
		BankController bankController = new BankControllerImpl(new BankService());
		
		final String CREATE_ACCOUNT_PATH = "/create-account";
		final String TRANSFER_PATH = "/transfer-fund/transfer-fund.html";
		final String SIGNUP_PATH = "/signup/signup.html";
		final String SIGNIN_PATH = "/sign-in/sign-in.html";
		final String VIEW_ACCOUNT_PATH = "/view-accounts";
		final String APPROVE_REJECT_PATH = "employee-home-page/employee-home-page.html";
		final String FILTER_PATH = "/employee-home-page/filter";
		final String FILTER_UPDATED_PATH = "/view-filtered-accounts/:filter";
		final String DEPOSIT_PATH = "/Deposit/deposit.html";
		final String WITHDRAW_PATH = "/Withdraw/withdraw.html";
		VerificationController verificationController = new VerificationControllerImpl();
		
		Javalin app = Javalin.create(
				config -> {
					config.addStaticFiles("/public");
					config.enableCorsForAllOrigins();
				}
			).start(9000);
		
        app.get(CUSTOMER_BY_ID_PATH, ctx -> bankController.getCustomerByid(ctx));
        app.post(CREATE_ACCOUNT_PATH, ctx -> bankController.createAccount(ctx));
        app.post(SIGNUP_PATH, ctx -> verificationController.signup(ctx));
        app.post(SIGNIN_PATH, ctx -> verificationController.singin(ctx));
        app.post(TRANSFER_PATH, ctx -> bankController.transfer(ctx));
        app.get(VIEW_ACCOUNT_PATH, ctx -> bankController.getAccounts(ctx));
        app.post(APPROVE_REJECT_PATH, ctx -> bankController.updateStatus(ctx));
        //app.post(FILTER_PATH, ctx -> bankController.filter(ctx));
        app.get(FILTER_UPDATED_PATH, ctx -> bankController.filter(ctx));
        app.post(DEPOSIT_PATH, ctx -> bankController.deposit(ctx));
        app.post(WITHDRAW_PATH, ctx-> bankController.withdraw(ctx));
	}
	

}
