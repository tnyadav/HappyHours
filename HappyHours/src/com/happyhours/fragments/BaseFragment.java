package com.happyhours.fragments;


import com.happyhours.activity.DashboardActivity;

import android.app.Activity;
import android.app.Fragment;

public abstract class BaseFragment extends Fragment{
	
	public DashboardActivity dashboardActivity;
	

	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		dashboardActivity=(DashboardActivity) activity;
		
		
	}
	
	abstract void setupUiComponent(); 

}
