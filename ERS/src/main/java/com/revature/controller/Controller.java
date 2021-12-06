package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.ERS_reimbursement;
import com.revature.model.ERS_user;
import com.revature.service.Service;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
	
	private Service service;
	private Logger logger; 
	
	public Controller(Service service) {
		
		this.service = service;
		logger = LoggerFactory.getLogger(Controller.class);
		
	}
	
	
	public Handler createUser = (ctx) -> {
		
		logger.info("Create user method called");
		
		try {
			
			ERS_user newUser = ctx.bodyAsClass(ERS_user.class);	//Gotta be doing some more input validation here, so itll throw either a SQLException or IllegalArgumentException
			ERS_user createdUser = service.createUser(newUser);	
			
			ctx.status(200);
			ctx.json(createdUser);
			logger.info("New user created successfully: " + createdUser);
			
			
			
		}
		catch(IllegalArgumentException e) {
			
			ctx.status(400);
			ctx.result(e.getMessage());
			logger.error(e.getMessage());
			
		}
		
		catch(SQLException e) {
			
			ctx.status(404);
			ctx.result(e.getMessage());
			logger.error(e.getMessage());
			
		}
		
		
	};
	
	public Handler deleteUser = (ctx) -> {
		
		
		int user_id = Integer.parseInt(ctx.pathParam("user_id")); //Get the id of the user we want to delete from the URI
		
		ERS_user currentUser = (ERS_user) ctx.req.getAttribute("currentuser");
		
		try {
		
			if(currentUser.getUser_role().compareTo("employee") == 0) {	//employees should only be able to delete themselves
				
				user_id = currentUser.getUser_id();
				
			} else if(currentUser.getUser_role().compareTo("manager") != 0) {
				
				throw new IllegalArgumentException("Role is not authorized");
				
			}		
			
			
			if(service.deleteUser(user_id)) {
				
				ctx.result("User with id of " + user_id + " successfully deleted.");
				ctx.status(200);				
				
			}else {
				
				ctx.result("Unable to delete user with id of " + user_id + ". User does not exist");
				ctx.status(400);
				
			}
			ctx.result("User with id of " + user_id + " successfully deleted.");
			ctx.status(200);
			
			
		}
		
		catch(SQLException e) {
			
			ctx.result(e.getMessage());
			ctx.status(400);
			
		}
	
		
		
	};
	
	public Handler updateUser = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler getAllUsers = (ctx) -> {
		
		ArrayList<ERS_user> user_list = new ArrayList<>();
		logger.info("getAllUsers called");
		
		ERS_user currentUser = (ERS_user) ctx.req.getAttribute("currentuser");
		
		try {
			
			if(currentUser.getUser_role().compareTo("manager") != 0) {
				
				throw new SQLException("Role is not authorized");
				
			}
			
			user_list = service.getAllUsers();
			
			ctx.json(user_list);
			ctx.status(200);
			logger.info(user_list.toString());
			
		}
		
		catch(Exception e) {
			
			ctx.result(e.getMessage());
			ctx.status(400);
			
		}
		
		
		
	};
	
	public Handler getUser = (ctx) -> {
		
		int user_id = Integer.parseInt(ctx.pathParam("user_id"));
		
		ERS_user currentUser = (ERS_user) ctx.req.getAttribute("currentuser");
		
		try {
			
			if(currentUser.getUser_role().compareTo("employee") == 0) {	//employees should only be able to acess their own info
				
				user_id = currentUser.getUser_id();
				
			} else if(currentUser.getUser_role().compareTo("manager") != 0) {
				
				throw new IllegalArgumentException("Role is not authorized");
				
			}
			
			
				
			ctx.json(service.getUser(user_id));
			ctx.status(200);
				
		}
		
		catch(SQLException e) {
			
			ctx.result(e.getMessage());
			ctx.status(400);
			
			
		}
		
		
		
		
	};
	
	public Handler createRequest = (ctx) -> {
		
		String param = ctx.pathParam("user_id");
		//int user_id = Integer.parseInt(param);
		
		ERS_reimbursement reimbursement = ctx.bodyAsClass(ERS_reimbursement.class);
		UploadedFile file = ctx.uploadedFile("reimb_receipt");
		InputStream content = file.getContent();
		//reimbursement.setReimb_author(user_id);		//I dont think i really need this
		
		ERS_user currentUser = (ERS_user) ctx.req.getSession().getAttribute("currentuser");
		
		try {
			
			reimbursement = service.createRequest(reimbursement, content, currentUser);
			ctx.json("Request successfully created");
			ctx.status(200);
			
		}
		
		catch(Exception e) {
			
			ctx.status(400);
			ctx.json(e);
			
		}
		
		
	};
	
	public Handler deleteRequest = (ctx) -> {
		
		//method stub
		
	};	
	
	public Handler updateRequest = (ctx) -> {
		
		String param = ctx.pathParam("reimb_id");
		int reimb_id = Integer.parseInt(param);
		
		ERS_reimbursement reimb = ctx.bodyAsClass(ERS_reimbursement.class);	//I only want to update the status, but ill load the whole class anyways
		reimb.setReimb_id(reimb_id);
		HttpSession session = ctx.req.getSession();
		ERS_user currentUser = (ERS_user) session.getAttribute("currentuser");
		
		
		try {
			
			reimb = service.updateRequest(reimb, currentUser);
			ctx.status(200);
			ctx.json(reimb);
			
		}
		
		catch(Exception e) {
			
			ctx.status(400);
			ctx.json(e);
			
		}
		
	};
	
	public Handler getUserRequests = (ctx) -> {
		
		//method stub
		
	};
	
	public Handler getAllRequests = (ctx) -> {
		
		ERS_user currentUser = (ERS_user) ctx.req.getSession().getAttribute("currentuser");
		
		try {
			
			ArrayList<ERS_reimbursement> reimbList = service.getAllRequests(currentUser);
			ctx.json(reimbList);
			ctx.status(200);
			
			
		}
		
		catch(Exception e) {
			
			ctx.json(e);
			ctx.status(400);
			
		}
		
	};
	
	public Handler getAllRequestsByStatus = (ctx) -> {
		
		ERS_user currentUser = (ERS_user) ctx.req.getSession().getAttribute("currentuser");
		String status = ctx.pathParam("status");
	
		
		try {
			
			ArrayList<ERS_reimbursement> reimbList = service.getAllRequests(status, currentUser);
			ctx.json(reimbList);
			ctx.json(200);
			
		}
		
		catch(Exception e) {
			
			ctx.json(e);
			ctx.status(400);
			
		}
		
	};
	
	public Handler loginUser = (ctx) -> {
		
		try {
			
			ERS_user user = ctx.bodyAsClass(ERS_user.class);
			
			user = service.getUserByUsernamePassword(user);
			
			HttpServletRequest req = ctx.req; 
			
			HttpSession session = req.getSession();
			
			session.setAttribute("currentuser", user);
			
			ctx.status(200);
			ctx.json(user);
			
			
		}
		
		catch(SQLException e) {
			
			ctx.result(e.getMessage());
			ctx.status(400);
			
		}
		
		
	};
	
	public Handler logoutUser = (ctx) -> {
		
		HttpServletRequest req = ctx.req; 
		req.getSession().invalidate();
		ctx.status(200);
		ctx.result("successfully logged out");
		
	};
	
//	public Handler getSelf = (ctx) -> {
//		
//		//method stub
//		
//	};
	
	

	public void registerEndpoints(Javalin app) {
		//ers_users table endpoints
		app.post("/ers_users/login", loginUser);	
		app.post("/ers_users", createUser);		
		app.delete("/ers_users/{user_id}", deleteUser);
		//app.patch("/ers_users", updateUser);
		app.get("/ers_users", getAllUsers);
		app.get("/ers_users/{user_id}", getUser);
		//app.get("/ers_users", getSelf);
		//ers_reimbursements endpoints
		app.post("/ers_reimbursements", createRequest);
		//app.delete("/ers_users/{user_id}", deleteRequest);
		app.post("ers_reimbursements/{reimb_id}", updateRequest);
		//app.get("ers_users/{user_id}", getUserRequests);
		app.get("/ers_reimbursements", getAllRequests);	//has dual functionality for employee and managers
		app.get("/ers_reimbursements/{status}", getAllRequestsByStatus);	//only for managers. Will probably modify later if I want to expand beyond MVP
		
		
		
		
	}

}
