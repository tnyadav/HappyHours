package com.happyhours.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.happyhours.R;
import com.google.gson.Gson;
import com.happyhours.activity.LoginActivity;
import com.happyhours.model.Login;
import com.happyhours.util.SharedPreferencesUtil;


public class UserAccountFragment extends BaseFragment {

	TextView loginStatus;
	Button loginLogout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_account,
				container, false);
		loginStatus=(TextView)view.findViewById(R.id.loginStatus);
		loginLogout=(Button)view.findViewById(R.id.loginLogout);
	

		return view;
	}

	@Override
	public void onResume() {
		setupUiComponent();
		super.onResume();
	}
	@Override
	void setupUiComponent() {
		
		final String userString=SharedPreferencesUtil.getPreferences(dashboardActivity, SharedPreferencesUtil.LOGIN, null);
		//Login user=null;
		if (null!=userString) {
			
			// user=new Gson().fromJson(userString, Login.class);
			loginLogout.setText("Logout");
		    loginStatus.setText(Html.fromHtml("<font color=\"red\">"+"Online-" + "</font> T.N.Yadav"));		
			
		} else {
			loginLogout.setText("Login");
			loginStatus.setText("Offline ");
		}
		loginLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if (null==userString) {
				dashboardActivity.startActivity(new Intent(dashboardActivity, LoginActivity.class));
				
			}else {
				SharedPreferencesUtil.deletePreferences(dashboardActivity, SharedPreferencesUtil.LOGIN);
				setupUiComponent();
				
			}	
			
			}
		});
		
		
		
		
	
			loginLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				dashboardActivity.startActivity(new Intent(dashboardActivity, LoginActivity.class));
				
			}
		});

	}

	@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//super.onCreateOptionsMenu(menu, inflater);
	    menu.clear();
	    dashboardActivity.invalidateOptionsMenu();
		    
}
}
