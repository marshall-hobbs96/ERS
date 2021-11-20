package com.revature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.service.Service;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class Controller {
	
	private Service service;
	private Logger logger; 
	
	public Controller(Service service) {
		
		this.service = service;
		logger = LoggerFactory.getLogger(Controller.class);
		
	}
	
	
	public Handler createUser = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler deleteUser = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler updateUser = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler getAllUsers = (ctx) -> {
		
		
		
	};
	
	public Handler getUser = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler createRequest = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler deleteRequest = (ctx) -> {
		
		//method stub
		
	};	
	
	public Handler updateRequest = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler getUserRequests = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler getAllRequests = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler loginUser = (ctx) -> {
		
		//method stub
		
	};
	
	

	public void registerEndpoints(Javalin app) {
		//ers_users table endpoints
		app.get("/ers_users/login", loginUser);
		app.post("/ers_users", createUser);
		app.delete("/ers_users", deleteUser);
		app.patch("/ers_users", updateUser);
		app.get("/ers_users", getAllUsers);
		app.get("/ers_users", getUser);
		//ers_reimbursements endpoints
		app.post("/ers_reimbursements/{user_id}", createRequest);
		app.delete("/ers_users/{user_id}", deleteRequest);
		app.patch("ers_reimbursements/{user_id}", updateRequest);
		app.get("ers_users/{user_id}", getUserRequests);
		app.get("/ers_reimbursements", getAllRequests);
		
		
		
	}

}