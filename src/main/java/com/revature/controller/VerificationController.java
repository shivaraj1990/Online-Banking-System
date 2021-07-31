package com.revature.controller;

import io.javalin.http.Context;

public interface VerificationController {
	
	public void signup(Context ctx);
	
	public void singin(Context ctx);

}
