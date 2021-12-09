package com.revature.model;

public class WelcomeManagerPage {

	public static String URL = "http://localhost:5500/welcomemanager.html";
	
	public static String filterDropdown = "//body/nav[1]/div[4]/div[1]/div[1]/div[1]/button[1]";
	public static String pendingFilter = "//a[contains(text(),'PENDING')]";
	public static String approvedFilter = "//a[contains(text(),'APPROVED')]";
	public static String deniedFilter = "//a[contains(text(),'DENIED')]";
	
	public static String logoutButton = "//body/nav[1]/div[4]/div[3]/div[1]/a[1]";
	
	public static String viewReceiptButton = "//tbody/tr[1]/button[1]";
	public static String approveRequestButton = "//tbody/tr[1]/button[2]";
	public static String denyRequestButton = "//tbody/tr[1]/button[3]";
	public static String helper = "//body/p[1]";
	
}
