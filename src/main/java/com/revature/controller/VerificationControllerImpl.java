package com.revature.controller;

import org.apache.log4j.Logger;

import com.revaturebank.model.User;
import com.revaturebank.presentation.BankTerminal;
import com.revaturebank.service.BankService;

import io.javalin.http.Context;
import jdk.internal.net.http.common.Log;



public class VerificationControllerImpl implements VerificationController  {
	 BankService bankService = new BankService();
	 static User user = new User();
	 final static Logger loggy = Logger.getLogger(VerificationControllerImpl.class);

	@Override
	public void signup(Context ctx) {
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");

		try {
			bankService.saveUser(username, password);
			loggy.info("Sign Up successfull for user: " + username);
			ctx.status(200);
			ctx.redirect("/home-page/homepage.html");
		   
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}


	@Override
	public void singin(Context ctx){		
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		User customer = null;
		User employee = null;
		try {
			customer = bankService.customerLogIn(username, password);
			employee = bankService.employeeLogIn(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(customer != null) {
			user.setId(customer.getId());
			user.setUserName(customer.getUserName());
			user.setUserType(customer.getUserType());
			loggy.info("LogIn successfull for customer: " + customer.getUserName());
			ctx.status(200);
			ctx.json(customer);
			ctx.redirect("/customer_home_page/customer_home_page.html");
		} else if (employee != null) {
			user.setId(employee.getId());
			user.setUserName(employee.getUserName());
			user.setUserType(employee.getUserType());
			loggy.info("LogIn successfull for employee: " + user.getUserName());
			ctx.status(200);
			ctx.json(employee);
			ctx.redirect("/employee-home-page/employee-home-page.html");
			
		} else {
			ctx.status(400);
			loggy.info("LogIn Failed for user: "+ username);
		}
	}
	}	
		
	

	
