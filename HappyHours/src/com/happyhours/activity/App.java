package com.happyhours.activity;

import java.util.List;

import android.app.Application;

import com.happyhours.model.Deals;

public final class App extends Application {
	private  List<Deals> deals;
	

	public List<Deals> getDeals() {
		return deals;
	}


	public void setDeals(List<Deals> deals) {
		this.deals = deals;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		
		/*ErrorReporter errReporter = new ErrorReporter();
		errReporter.Init(this);
		errReporter.SaveLog(this,"JainBook");
		enableHttpCaching();*/
	}

	
	@Override
	public void onTerminate() {
		
		super.onTerminate();
	}


}
