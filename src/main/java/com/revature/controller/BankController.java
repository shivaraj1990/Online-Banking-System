package com.revature.controller;

import io.javalin.http.Context;

public interface BankController {
	
	public void getCustomerByid(Context ctx);
	public void createAccount(Context ctx);
	public void transfer(Context ctx);
	public void getAccounts(Context ctx);
	public void updateStatus(Context ctx);
	public void filter(Context ctx);
	public void deposit(Context ctx);
	public void withdraw(Context ctx);
	
}
