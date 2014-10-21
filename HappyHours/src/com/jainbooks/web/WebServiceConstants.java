package com.jainbooks.web;

public class WebServiceConstants {
	//private static final String BASE_URL = "http://115.113.54.10:8080/happyhours/rest/happy-hours-service/";
	
	//********** system ip**********
	
	private static final String BASE_URL="http://172.16.4.2:8080/happyhours/rest/happy-hours-service/";

	public static final String REGISTRATION = BASE_URL + "register-new-user";
	public static final String AUTHENTICATE = BASE_URL + "sign-in";
	public static final String GET_ALL_DEALS = BASE_URL + "geo-search-deal";

	public static final String SEARCH_DEALS = BASE_URL
			+ "search-deal?searchString=";
	public static final String ADD_DEAL = BASE_URL + "save-deal";
	public static final String GET_DEAL_CATEGORY = BASE_URL + "get-category-list";
}
