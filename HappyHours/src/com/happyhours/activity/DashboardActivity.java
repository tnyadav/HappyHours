package com.happyhours.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.app.happyhours.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.happyhours.fragments.NavigationDrawerFragment;
import com.happyhours.location.LocationUtils;
import com.happyhours.util.TAListener;
import com.jainbooks.web.TAPOSTWebServiceAsyncTask;

public class DashboardActivity extends FragmentActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks ,
        LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{

	
	   // A request to connect to Location Services
    private LocationRequest mLocationRequest;

    // Stores the current instantiation of the location client in this object
    public LocationClient mLocationClient;
    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;

    /*
     * Note if updates have been turned on. Starts out as "false"; is set to "true" in the
     * method handleRequestSuccess of LocationUpdateReceiver.
     *
     */
    boolean mUpdatesRequested = false;
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
		
		//Location 
		  mLocationRequest = LocationRequest.create();

	        /*
	         * Set the update interval
	         */
	        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

	        // Use high accuracy
	        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	        // Set the interval ceiling to one minute
	        mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

	        // Note that location updates are off until the user turns them on
	        mUpdatesRequested = false;

	        // Open Shared Preferences
	        mPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);

	        // Get an editor
	        mEditor = mPrefs.edit();

	        /*
	         * Create a new location client, using the enclosing class to
	         * handle callbacks.
	         */
	        mLocationClient = new LocationClient(this, this, this);
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
	
	
    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {

        // If the client is connected
        if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }

        // After disconnect() is called, the client is considered "dead".
        mLocationClient.disconnect();

        super.onStop();
    }
    /*
     * Called when the Activity is going into the background.
     * Parts of the UI may be visible, but the Activity is inactive.
     */
    @Override
    public void onPause() {

        // Save the current setting for updates
        mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, mUpdatesRequested);
        mEditor.commit();

        super.onPause();
    }

    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {

        super.onStart();

        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();

    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// If the app already has a setting for getting location updates, get it
        if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
            mUpdatesRequested = mPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);

        // Otherwise, turn off location updates until requested
        } else {
            mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
            mEditor.commit();
        }
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {


        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {

            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
    
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		  if (mUpdatesRequested) {
	            startPeriodicUpdates();
	        }
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	 private void startPeriodicUpdates() {

	        mLocationClient.requestLocationUpdates(mLocationRequest, this);
	        
	    }
	
	    /**
	     * In response to a request to stop updates, send a request to
	     * Location Services
	     */
	    private void stopPeriodicUpdates() {
	        mLocationClient.removeLocationUpdates(this);
	       
	    }
	  
	    public boolean servicesConnected() {

	        // Check that Google Play services is available
	        int resultCode =
	                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

	        // If Google Play services is available
	        if (ConnectionResult.SUCCESS == resultCode) {
	            // In debug mode, log the status
	            Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

	            // Continue
	            return true;
	        // Google Play services was not available for some reason
	        } else {
	            // Display an error dialog
	            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
	            if (dialog != null) {
	                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	                errorFragment.setDialog(dialog);
	                errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
	            }
	            return false;
	        }
	    }
	    public static class ErrorDialogFragment extends DialogFragment {

	        // Global field to contain the error dialog
	        private Dialog mDialog;

	        /**
	         * Default constructor. Sets the dialog field to null
	         */
	        public ErrorDialogFragment() {
	            super();
	            mDialog = null;
	        }

	        /**
	         * Set the dialog to display
	         *
	         * @param dialog An error dialog
	         */
	        public void setDialog(Dialog dialog) {
	            mDialog = dialog;
	        }

	        /*
	         * This method must return a Dialog to the DialogFragment.
	         */
	        @Override
	        public Dialog onCreateDialog(Bundle savedInstanceState) {
	            return mDialog;
	        }
	    }
	    /**
	     * Show a dialog returned by Google Play services for the
	     * connection error code
	     *
	     * @param errorCode An error code returned from onConnectionFailed
	     */
	    private void showErrorDialog(int errorCode) {

	        // Get the error dialog from Google Play services
	        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
	            errorCode,
	            this,
	            LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

	        // If Google Play services can provide an error dialog
	        if (errorDialog != null) {

	            // Create a new DialogFragment in which to show the error dialog
	            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

	            // Set the dialog in the DialogFragment
	            errorFragment.setDialog(errorDialog);

	            // Show the error dialog in the DialogFragment
	            errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
	        }
	    }
}
