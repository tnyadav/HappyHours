package com.happyhours.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.app.happyhours.R;
import com.google.gson.Gson;
import com.happyhours.model.Login;
import com.happyhours.util.NotificationUtils;
import com.happyhours.util.SharedPreferencesUtil;
import com.happyhours.util.TAListener;
import com.happyhours.util.Utils;
import com.jainbooks.web.WebServiceConstants;


public class LoginActivity extends BaseActivity {
  
	private EditText editTextLoginUsername;
	private EditText editTextLoginPassword;
	private CheckBox checkBoxSignInRememberMe;
	private Button imageButtonLoginButton;
	private Button buttonLoginForgotPassword;
	private Button buttonLoginSignUp;
    String mobileNumber = null;
	
	
	private static final String TAG = "LoginActivity";


	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle("Login");
		setContentView(R.layout.activity_login);
			bindContents();
       
	}

	private void bindContents() {
		buttonLoginSignUp = (Button)findViewById(R.id.buttonLoginSignUp);

		editTextLoginUsername = (EditText)findViewById(R.id.editTextLoginUsername);
		editTextLoginPassword = (EditText)findViewById(R.id.editTextLoginPassword);
		checkBoxSignInRememberMe = (CheckBox) findViewById(R.id.checkBoxSignInRememberMe);
		imageButtonLoginButton = (Button) findViewById(R.id.imageButtonLoginButton);
		buttonLoginForgotPassword = (Button) findViewById(R.id.buttonLoginForgotPassword);
		

		
		String userName = SharedPreferencesUtil.getPreferences(activity,
				SharedPreferencesUtil.USER_NAME, "");
		String password = SharedPreferencesUtil.getPreferences(activity,
				SharedPreferencesUtil.PASSWORD, "");

		if (!TextUtils.isEmpty(userName)) {
			editTextLoginUsername.setText(userName);
		}

		if (!TextUtils.isEmpty(password)) {
			editTextLoginPassword.setText(password);
			checkBoxSignInRememberMe.setChecked(true);
		}

		imageButtonLoginButton.setOnClickListener(new View.OnClickListener() {
			private String username;
			private String password;

			@Override
			public void onClick(View v) {
				

				username = editTextLoginUsername.getText().toString();
				password = editTextLoginPassword.getText().toString();

				if (checkBoxSignInRememberMe.isChecked()) {
					SharedPreferencesUtil.savePreferences(activity,
							SharedPreferencesUtil.USER_NAME, username);
					SharedPreferencesUtil.savePreferences(activity,
							SharedPreferencesUtil.PASSWORD, password);

				}

				if ((username == null || TextUtils.isEmpty(username))
						&& (password == null || TextUtils.isEmpty(password))) {
					NotificationUtils.showNotificationToast(activity,
							"Please fill in your Username and Password");
					return;
				} else {
					if (username == null || TextUtils.isEmpty(username)) {
						NotificationUtils.showNotificationToast(activity,
								"Please fill in your Username");
						return;
					}

					if (password == null || TextUtils.isEmpty(password)) {
						NotificationUtils.showNotificationToast(activity,
								"Please fill in your Password");
						return;
					}
				}
				if (!Utils.isNetworkAvailable(activity)) {
					return;
				}

				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("userID", username);
					jsonObject.put("password", password);
					
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

						Gson gson = new Gson();
						String responseJSON = argBundle
								.getString(TAListener.LISTENER_BUNDLE_STRING_1);
						
	
						 
						Login login = gson.fromJson(responseJSON, Login.class);

						if (null != login) {
							if (login.getCode().equalsIgnoreCase(
									"signIn001")) {
								NotificationUtils.showNotificationToast(activity,
										login.getMessage());
								finish();
					
							}else {
								NotificationUtils.showNotificationToast(activity,
										login.getMessage());
							}
						

						} else {
							NotificationUtils.showNotificationToast(activity,
									"Server not responds");
						}

					}
				};

				DashboardActivity.registorOrAuthenticate(activity,
						jsonObject.toString(), taListener,
						WebServiceConstants.AUTHENTICATE);

			
				}
		});

/*		buttonLoginForgotPassword
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
*/
		buttonLoginSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(activity, RegistrationActivity.class));
				/*String mobileNumber=SharedPreferencesUtil.getPreferences(activity, JainBooksConstants.MOBILE_NUMBER, null);
				if (mobileNumber!=null||!TextUtils.isEmpty(mobileNumber)) {
					
				}else {
					startActivity(new Intent(activity, VerificationActivity.class));
				}*/
				

			}
		});
	
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		return true;
	}
}
