package com.happyhours.activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.app.happyhours.R;
import com.google.gson.Gson;
import com.happyhours.model.Deals;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class EntryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry_screen);
	}
	public void onClickAdmin(View view) {
		startActivity(new Intent(EntryActivity.this, DashboardActivity.class));
	}
    public void onClickUser(View view) {
    	startActivity(new Intent(EntryActivity.this, DashboardActivity.class));	
	}
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				String path = Environment.getExternalStorageDirectory()
						+ "/a.jpg";
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://172.16.9.35:8081/happyhours/rest/happy-hours-service/uploadImage");
				//httppost.setHeader("dealID", "1");
				try {
				MultipartEntity entity = new MultipartEntity();
				Deals deals=new Deals();
				deals.setDescription("tau");
				String s=new Gson().toJson(deals);
				entity.addPart("deal",  new StringBody(s));
				/*entity.addPart("dealID",  new StringBody("1"));
				entity.addPart("mainImage",  new StringBody("true"));
				entity.addPart("file" , new FileBody(new File(path)));
				*/
				httppost.setEntity(entity);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity getresponse = response.getEntity();
				String responseData = EntityUtils.toString(getresponse);
				Log.e("UploadFile", responseData);
				
				}catch (Exception e) {
				e.printStackTrace();
				
				}

				return null;
			}
		}.execute();
    }
}
