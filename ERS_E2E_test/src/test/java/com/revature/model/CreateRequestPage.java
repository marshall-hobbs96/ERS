package com.revature.model;

public class CreateRequestPage {

	public static String URL = "http://localhost:5500/createrequest.html";
	
	public static String menuDropdown = "//body/nav[1]/div[4]/div[1]/div[1]";
	public static String homeButton = "//a[contains(text(),'Home')]";
	public static String createRequestButton = "//a[contains(text(),'New Request')]";
	
	public static String logoutButton = "//body/nav[1]/div[4]/div[2]/div[1]/a[1]";
	
	public static String typeInput = "//body/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]";
	public static String amountInput = "//body/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/input[1]";
	public static String receiptInput = "//body/div[2]/div[2]/div[1]/div[5]/div[1]/label[1]/input[1]";
	public static String descriptionInput = "//body/div[2]/div[2]/div[1]/div[7]/div[1]/div[1]/div[1]/textarea[1]";
	
	public static String submitButton = "//button[contains(text(),'Submit')]";
	public static String submitHelper = "//body/div[2]/div[2]/div[1]/div[9]/p[1]";
	
}
