package com.jainbooks.web;

import java.io.IOException;
import java.io.InputStream;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.happyhours.util.NotificationUtils;
import com.happyhours.util.TAListener;
import com.happyhours.util.Utils;

public class TAPOSTWebServiceAsyncTask extends AsyncTask<Void, Void, String> {

	private InputStream inputStream;
	// private static BasicHttpContext httpContext = new BasicHttpContext();
	private TAListener mListener;
	private Bundle mBundle;
	private Activity mActivity;
	private String mJSONString;
	private String requestURL;
	private boolean mShowDialog;
	private boolean avoidJSONVerify;
	private String header;

	public TAPOSTWebServiceAsyncTask(Activity argActivity, Bundle argBundle,
			TAListener argListener, String argRequestURL, String argJSONString,String header) {
		mActivity = argActivity;
		mListener = argListener;
		mBundle = argBundle;
		this.requestURL = argRequestURL;
		this.header=header;
		mJSONString = argJSONString;

		mShowDialog = true;
		if (argBundle != null) {
			try {
				mShowDialog = argBundle.getBoolean("showDialog", true);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			try {
				avoidJSONVerify = argBundle.getBoolean("avoidJSONVerify");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		// Check Internet access
		if (!Utils.isNetworkAvailable(mActivity)) {
			// NotificationUtils.showNotificationToast(mActivity,
			// "Internet connection is required to proceed.");
			/*Intent networkIntent = new Intent(mActivity,
					NetworkHandlerFragment.class);
			mActivity.startActivity(networkIntent);
*/
			cancel(true);
			return;
		}

		if (mShowDialog) {
			// Show loading progress dialog in the beginning of AsyncTask.
			NotificationUtils.showProgressDialog(mActivity, "Please wait",
					"Loading..");
		}
	}

	@Override
	protected String doInBackground(Void... params) {

		String mResponseJson = null;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(requestURL);

		try {

			System.out.println("POST JSON: " + mJSONString);
			System.out.println("POST REQ: " + requestURL);
			
			
			StringEntity se = new StringEntity(mJSONString);

			// httppost.setEntity(new UrlEncodedFormEntity(mPOSTParams));
			httppost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-type", "application/json");
			if (header!=null) {
				httppost.setHeader("Authorization", header);
			}

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
			mResponseJson = EntityUtils.toString(httpEntity);
		
			Log.e("API response", mResponseJson);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mResponseJson;

	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		try {
			// Dismiss all progress dialog on task complete
			NotificationUtils.dismissProgressDialog();

			if (!avoidJSONVerify) {
				// Validating JSON response
				try {
					//new Gson().fromJson(result, TARecentVideosModel.class);
					// If this goes by without exceptions, the data received can
					// confirmed to be in JSON format.
				} catch (JsonParseException e) {
					// If an exception is caught, dump the response and end task
					// with failure.
					e.printStackTrace();
					Bundle returnData = new Bundle();
					returnData.putString(TAListener.LISTENER_BUNDLE_STRING_1,
							result);
					mListener.onTaskFailed(returnData);
					return;
				}
			}

			// JSON validated, check for content availability and respond to
			// caller accordingly.
			Bundle returnData = new Bundle();
			returnData.putString(TAListener.LISTENER_BUNDLE_STRING_1, result);
			if (result != null || !TextUtils.isEmpty(result)) {
				mListener.onTaskCompleted(returnData);
			} else {
				mListener.onTaskFailed(returnData);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			Bundle returnData = new Bundle();
			returnData.putString(TAListener.LISTENER_BUNDLE_STRING_1, "");
			mListener.onTaskFailed(returnData);
		}
	}

}