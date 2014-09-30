package com.happyhours.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.app.happyhours.R;

public abstract class BaseActivity extends Activity{

	protected Activity activity;
	protected SearchView searchView;

	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setIcon(R.drawable.logo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.top_background_gradient));
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		activity = this;
	}
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		overridePendingTransition(R.animator.slide_out_right,
				R.animator.slide_in_left);
	}
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.animator.slide_in_right,
				R.animator.slide_out_left);
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
	    searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
	    searchView.setOnQueryTextListener(queryListener);
		return true;
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case android.R.id.home:
			this.onBackPressed();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*final protected OnQueryTextListener queryListener = new OnQueryTextListener() {       

	    @Override
	    public boolean onQueryTextChange(String newText) {
	     
	        return false;
	    }

	    @Override
	    public boolean onQueryTextSubmit(String query) {  
	    	searchView.clearFocus();
	    	if (!query.isEmpty()) {
	    	
		        Intent intent =new Intent(activity, EBookSearchResultActicity.class);
		        intent.putExtra(JainBooksConstants.SEARCH_STRING, query.replaceAll(" ", "%20"));
		        startActivity(intent);
		       
			}
	    	
	        return false;
	    }
	};
	*/
	protected void showLog(String msg) {
		Log.e("jain", msg);
	}
}
