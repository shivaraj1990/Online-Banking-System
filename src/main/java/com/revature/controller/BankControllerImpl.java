package com.revature.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revaturebank.model.Customer;
import com.revaturebank.model.User;
import com.revaturebank.service.BankService;

import io.javalin.http.Context;

public class BankControllerImpl implements BankController {
	
	BankService service;
	final static Logger loggy = Logger.getLogger(BankControllerImpl.class);
	
	public BankControllerImpl(BankService service) {
		this.service = service;
	}

	@Override
	public void getCustomerByid(Context ctx) {
		String id = ctx.pathParam("id");
		ctx.json(service.getCustomers(Integer.parseInt(id)));
		
	}

	@Override
	public void createAccount(Context ctx) {
		Customer customer = ctx.bodyAsClass(Customer.class);
		customer.setCustomerId(VerificationControllerImpl.user.getId());
		loggy.info("Request received to create account with user info:: " + customer.toString());
		service.applyAccount(
				customer.getAccountType(),
				customer.getFirstName(),
				customer.getLastName(),
				customer.getEmail(),
				customer.getPhoneNumber(),
				customer.getAccountName(),
				customer.getBalance(),
				customer.getCustomerId()	
		);
		loggy.info("Successfully created account with user info:: " + customer.toString());
		ctx.redirect("/home-page/homepage.html");
	}

	@Override
	public void getAccounts(Context ctx) {
		loggy.info("Request received to get account with id: " + VerificationControllerImpl.user.getId());
		List<Customer> customer = new ArrayList();
		System.out.println(VerificationControllerImpl.user.toString());
		if(VerificationControllerImpl.user.getUserType().equals("customer")) {
			customer = service.getCustomers(VerificationControllerImpl.user.getId()); 
		} else {
			customer = service.getCustomers();
		}
		loggy.info("Successfully retreived accounts: " + customer.toString());
		ctx.status(200);
		ctx.json(customer);
	}

	@Override
	public void updateStatus(Context ctx) {
		String accNumber = ctx.formParam("accountnumber");
		String statusNum = ctx.formParam("status");
		String status = null;
		
		status = statusNum.equals("1") ? "active" : "inactive";
		System.out.println(accNumber + " :: " + status);
		service.updatePendingAccounts(status, Integer.parseInt(accNumber));
		ctx.status(200);
		ctx.redirect("/employee-home-page/employee-home-page.html");
		
	}

	@Override
	public void filter(Context ctx) {
		String filter = ctx.pathParam("filter");
		
		Integer id = null;
		try {
			id = Integer.parseInt("filter");
		} catch (Exception e) {
			loggy.error("The filter is account number");
		}
		
		if(id != null) {
			service.getAccountsByCustomerId(id);
		} else {
			service.getCustomersPendingAccounts(filter);
		}
		
		
		System.out.println(filter);
		ctx.status(200);
		ctx.redirect("/employee-home-page/filtered-account.html");
	}

	@Override
	public void deposit(Context ctx) {
		Double amount = Double.parseDouble(ctx.formParam("amount"));
		String accountTypeNum = ctx.formParam("accountType");
		String accountType;
		if(accountTypeNum.equals("1")) {
			accountType = "checking";
		} else {
			accountType = "saving";
		}
		System.out.println(accountType);
		User user = VerificationControllerImpl.user;
		
		List<Customer> customers = service.getCustomers(user.getId());
		
		System.out.println(customers);
		Customer customer = null;
//		for(Customer cust: customers) {
//			System.out.println(cust.getAccountType());
//			customer = cust.getAccountType().equals(accountType) ? cust : null ;
//		}
		customer = customers.stream().filter(cust ->cust.getAccountType().equals(accountType)).findFirst().orElse(null);
		
		System.out.println(customer);
		
		if(customer.getStatus().equals("active") && amount > 0) {
			service.deposit(customer.getAccountNumber(), amount, customer.getBalance());
			ctx.status(200);
			ctx.redirect("/customer_home_page/customer_home_page.html");
		} else {
			ctx.status(406);
			ctx.redirect("/error-page/error-page.html");
		}
	}

	@Override
	public void withdraw(Context ctx) {
		Double amount = Double.parseDouble(ctx.formParam("amount"));
		String accountTypeNum = ctx.formParam("accountType");
		String accountType;
		if(accountTypeNum.equals("1")) {
			accountType = "checking";
		} else {
			accountType = "saving";
		}

		User user = VerificationControllerImpl.user;
		
		List<Customer> customers = service.getCustomers(user.getId());
		Customer customer = null;

		customer = customers.stream().filter(cust ->cust.getAccountType().equals(accountType)).findFirst().orElse(null);
		
		if(customer.getStatus().equals("active") && amount > 0 && amount <= customer.getBalance()) {
			service.withdraw(customer.getAccountNumber(), amount, customer.getBalance());
			ctx.status(200);
			ctx.redirect("/customer_home_page/customer_home_page.html");
		} else {
			ctx.status(406);
			ctx.redirect("/error-page/error-page.html");
		}
	}

	@Override
	public void transfer(Context ctx) {
		String accountTypeNum = ctx.formParam("accountType");
		Double amount = Double.parseDouble(ctx.formParam("amount"));
		Integer payToAccountNumber = Integer.parseInt(ctx.formParam("payTo"));
	
		String accountType;
		if(accountTypeNum.equals("1")) {
			accountType = "checking";
		} else {
			accountType = "saving";
		}

		User user = VerificationControllerImpl.user;
		
		List<Customer> customers = service.getCustomers(user.getId());
		Customer customer = null;

		customer = customers.stream().filter(cust ->cust.getAccountType().equals(accountType)).findFirst().orElse(null);
		
		if(customer.getStatus().equals("active") && amount > 0 && amount <= customer.getBalance()) {
			service.transfer(customer.getAccountNumber(), amount, customer.getBalance(), payToAccountNumber);
			ctx.status(200);
			ctx.redirect("/customer_home_page/customer_home_page.html");
		} else {
			ctx.status(406);
			ctx.redirect("/error-page/error-page.html");
		}
			
	}
		
}


