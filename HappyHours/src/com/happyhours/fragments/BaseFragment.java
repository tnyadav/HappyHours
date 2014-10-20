package com.happyhours.fragments;


import com.happyhours.activity.DashboardActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment{
	
	public DashboardActivity dashboardActivity;
	protected View view;

	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		dashboardActivity=(DashboardActivity) activity;
		
		
	}
	
	abstract void setupUiComponent();

	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return null;
	} 

}
