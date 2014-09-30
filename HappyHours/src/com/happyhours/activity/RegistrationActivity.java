package com.happyhours.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.app.happyhours.R;
import com.google.gson.Gson;
import com.happyhours.model.Login;
import com.happyhours.util.NotificationUtils;
import com.happyhours.util.SharedPreferencesUtil;
import com.happyhours.util.TAListener;
import com.happyhours.util.Utils;
import com.jainbooks.web.WebServiceConstants;


public class RegistrationActivity extends BaseActivity {
  
	private EditText etUserName,etEmail,etPassword1,etPassword2;
	private Button btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setTitle("Registration");
		setContentView(R.layout.activity_registration);
		
		etUserName = (EditText) 
				findViewById(R.id.etUserName);
		etEmail = (EditText) 
				findViewById(R.id.etEmail);
		etPassword1 = (EditText) 
				findViewById(R.id.etPassword1);
		etPassword2 = (EditText) 
				findViewById(R.id.etPassword2);
		
		
		btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				register();
			}
		});
		  
		
	}
	private void register() {


		if (!Utils.isNetworkAvailable(activity)) {
			NotificationUtils.showNotificationToast(activity, "No network connection");
			//return;
		}

		String strUserName,strEmail,strpassword1,strpassword2;
		strUserName = etUserName.getText().toString();
		strEmail=etEmail.getText().toString();
		strpassword1 = etPassword1.getText().toString();
		strpassword2 = etPassword2.getText().toString();
		
		if (strUserName == null || TextUtils.isEmpty(strUserName)) {
			NotificationUtils.showNotificationToast(activity,
					"Please fill in your User name");
			return;
		}else if (strEmail == null || TextUtils.isEmpty(strEmail)) {
			NotificationUtils.showNotificationToast(activity,
					"Please fill in your Email Id");
			return;
		} 
		else if (strpassword1 == null || TextUtils.isEmpty(strpassword1)) {
			NotificationUtils.showNotificationToast(activity,
					"Please fill in your Password");
			return;
		}else if (!strpassword1.equals(strpassword2)) {
				NotificationUtils.showNotificationToast(activity,
						"Password did not match");
				return;
			}
		else if (!strpassword1.equals(strpassword2)) {
			NotificationUtils.showNotificationToast(activity,
					"Password did not match");
			return;
		}
	
		

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("userID", strUserName);
			jsonObject.put("password", strpassword1);
			jsonObject.put("email", strEmail);
				
		} catch (JSONException e) {
			e.printStackTrace();
			NotificationUtils.showNotificationToast(activity,
					"Please re-enter Username and Password");
		} catch (NullPointerException e) {
			e.printStackTrace();
			NotificationUtils.showNotificationToast(activity,
					"Please re-enter Username and Password");
		}

		TAListener taListener = new TAListener() {

			@Override
			public void onTaskFailed(Bundle argBundle) {

			}

			@Override
			public void onTaskCompleted(Bundle argBundle) {

				String responseJSON = argBundle
						.getString(TAListener.LISTENER_BUNDLE_STRING_1);
				Log.e("RegistrationActivity", responseJSON);
				Gson gson=new Gson();
				Login login = gson.fromJson(responseJSON,
						Login.class);

				if (null != login) {

					if (login.getCode().equalsIgnoreCase(
							"rsgisterUser001")) {
						NotificationUtils.showNotificationToast(activity,
								login.getMessage());
						SharedPreferencesUtil.savePreferences(activity, SharedPreferencesUtil.LOGIN, responseJSON);
						finish();

					}
					NotificationUtils.showNotificationToast(activity,
							login.getMessage());
					finish();

				} else {
					NotificationUtils.showNotificationToast(activity,
							"Server not responds");
				}

			}
		};

		DashboardActivity.registorOrAuthenticate(activity,
				jsonObject.toString(), taListener,
				WebServiceConstants.REGISTRATION);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		return true;
	}
}
