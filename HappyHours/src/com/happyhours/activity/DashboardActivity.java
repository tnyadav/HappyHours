package com.happyhours.activity;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.app.happyhours.R;
import com.google.gson.Gson;
import com.happyhours.fragments.NavigationDrawerFragment;
import com.happyhours.fragments.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.happyhours.model.Deals;
import com.happyhours.util.TAListener;
import com.jainbooks.web.TAPOSTWebServiceAsyncTask;

public class DashboardActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	public SearchView searchView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
     
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
        // Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(String title,Fragment fragment) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment)
				.commit();
		
	}

	public void onSectionAttached(String title) {
		
			mTitle = title;
			
	}

	@SuppressLint("NewApi")
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.logo);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.menu_text_color));
/*		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.map, menu);
			restoreActionBar();
			  searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
			    searchView.setOnQueryTextListener(queryListener);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	final private OnQueryTextListener queryListener = new OnQueryTextListener() {       

	    @Override
	    public boolean onQueryTextChange(String newText) {
	      /*  if (TextUtils.isEmpty(newText)) {
	            getActivity().getActionBar().setSubtitle("List");               
	            searchString = null;
	        } else {
	            getActivity().getActionBar().setSubtitle("List - Searching for: " + newText);
	            searchString = newText;

	        }   
	        getLoaderManager().restartLoader(0, null, MyListFragment.this); */
	        return false;
	    }

	    @Override
	    public boolean onQueryTextSubmit(String query) {  
	    	searchView.clearFocus();
	    	if (!query.isEmpty()) {
	    	
		        Intent intent =new Intent(DashboardActivity.this, DealsSearchResultActivity.class);
		        intent.putExtra("SEARCH_STRING", query.toLowerCase().replaceAll(" ", "%20"));
		        startActivity(intent);
		       
			}
	    	
	        return false;
	    }
	};
	public static void registorOrAuthenticate(Activity mActivity,
			String argJSONString, TAListener listener,String url) {
		new TAPOSTWebServiceAsyncTask(
				mActivity,
				null,
				listener,
				url,
				argJSONString,null).executeOnExecutor(
				AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
		
	}
	/*@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			//setContentView(R.layout.detail_property);
		}
	}*/
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
}
