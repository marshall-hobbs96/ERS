package com.revature.app;

import com.revature.DAL.DAL;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionMappingController;
import com.revature.service.Service;

import io.javalin.Javalin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	public static void main(String[] args) {
		
		
		System.out.println(DAL.establishConnection(System.getenv("db_url"), System.getenv("db_username"), System.getenv("db_password")));
		
		Javalin app = Javalin.create((config) -> {
			
			config.enableCorsForOrigin("http://localhost:5500", "http://127.0.0.1:5500", "http://revature-project-1.s3-website.us-east-2.amazonaws.com");	//will need to add S3 when deploying to AWS
			
		});
		
		Logger logger = LoggerFactory.getLogger(Application.class);
		
		app.before(ctx -> {
			
			logger.info(ctx.method() + " request received to the " + ctx.path() + " endpoint");
			
			
		});
		
		Controller controller = new Controller(new Service(new DAL()));
		controller.registerEndpoints(app);
		
		ExceptionMappingController exceptionController = new ExceptionMappingController();
		exceptionController.mapExceptions(app);
		
		app.start(8081);

	}

}
