package com.jainbooks.web;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.happyhours.util.JSONParser;
import com.happyhours.util.NotificationUtils;
import com.happyhours.util.TAListener;
import com.happyhours.util.Utils;

/**
 * Custom AsyncTask responsible for encryption, API request/response handling.
 * 
 * @author Optimus
 * 
 */
public class TAWebServiceAsyncTask extends AsyncTask<Void, Void, String> {

	private TAListener mListener;
	private Activity mActivity;
	private String mRequestURL;
	private boolean mVerifyJSONObject;
	private boolean mShowDialog;
	private Bundle mArgBundle;

	public TAWebServiceAsyncTask(Activity argActivity, Bundle argBundle,
			TAListener argListener, String requestURL, boolean verifyJSONObject) {
		mActivity = argActivity;
		mListener = argListener;
		mRequestURL = requestURL;
		mVerifyJSONObject = verifyJSONObject;
		mArgBundle = argBundle;
		mShowDialog = true;
		if (argBundle != null) {
			try {
				mShowDialog = argBundle.getBoolean("showDialog");
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
			NotificationUtils.showNotificationToast(mActivity,
					"No Network Connection");
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
	protected String doInBackground(Void... arg0) {
		String responseJson = null;

		try {
			System.out.println("request: " + mRequestURL);
			responseJson = new JSONParser().getJSONFromUrl(mRequestURL);
			System.out.println("" + responseJson);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return responseJson;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

			// Dismiss all progress dialog on task complete
			if(mShowDialog)
			NotificationUtils.dismissProgressDialog();

			//if (mVerifyJSONObject) {
		
			Bundle returnData = new Bundle();
			returnData.putString(TAListener.LISTENER_BUNDLE_STRING_1, result);
			if (result != null || !TextUtils.isEmpty(result)) {
				mListener.onTaskCompleted(returnData);
			} else {
				mListener.onTaskFailed(returnData);
			}

		
	
		}
	//}
}
