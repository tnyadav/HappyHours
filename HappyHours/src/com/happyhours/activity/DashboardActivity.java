package com.happyhours.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.app.happyhours.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.happyhours.adapter.DealsSearchAdapter;
import com.happyhours.fragments.NavigationDrawerFragment;
import com.happyhours.model.Deals;
import com.happyhours.util.NotificationUtils;
import com.happyhours.util.TAListener;
import com.jainbooks.web.TAPOSTWebServiceAsyncTask;
import com.jainbooks.web.WebServiceConstants;
import com.meetme.android.horizontallistview.HorizontalListView;

public class DashboardActivity extends FragmentActivity implements
ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener,
		OnMarkerClickListener, OnInfoWindowClickListener {

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	public SearchView searchView;
	List<Deals> deals;
	List<Deals> hotDeals;
	private HorizontalListView mHlvCustomListWithDividerAndFadingEdge;
	private Button viewHotDeals;
	private HashMap<String, Deals> markers= new HashMap<String, Deals>();
	//map
	 private GoogleMap mMap;
	 private LocationClient mLocationClient;
	 private static final LocationRequest REQUEST = LocationRequest.create()
	            .setInterval(5000)         // 5 seconds
	            .setFastestInterval(16)    // 16ms = 60fps
	            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	 private boolean isLocationAdded=false;
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
		 setUpMapIfNeeded();
		
	}

/*	@Override
	public void onNavigationDrawerItemSelected(String title,Fragment fragment) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment)
				.commit();
		
	}*/

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
			/*  searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
			    searchView.setOnQueryTextListener(queryListener);*/
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

	@Override
	public void onInfoWindowClick(Marker arg0) {
		
		Deals deals = markers.get(arg0.getId());
		Intent intent = new Intent(DashboardActivity.this,
				DealsDetailActivity.class);
		String deal=new Gson().toJson(deals);
		intent.putExtra("deal", deal);
		startActivity(intent);
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	 /** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements InfoWindowAdapter {
      
        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
        	render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
           
            return null;
        }

        private void render(Marker marker, View view) {
            int badge;
           
            badge = R.drawable.plus;
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null /*&& snippet.length() > 12*/) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, snippet.length(), 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), snippet.length(), snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
            	 mMap.getUiSettings().setZoomControlsEnabled(true);
               
            }
        }
    }
    
    private void addMarkersToMap(String latitude,String longitude) {


		JSONObject jsonObject = new JSONObject();
		try {
			/*jsonObject.put("latitude", 25.198342);
			jsonObject.put("longitude", 55.274140);*/
			jsonObject.put("latitude", latitude);
			jsonObject.put("longitude", longitude);
			jsonObject.put("requiredDistance", 10000);

		} catch (JSONException e) {
			e.printStackTrace();

		}

		TAListener taListener = new TAListener() {

			@Override
			public void onTaskFailed(Bundle argBundle) {

			}

			@Override
			public void onTaskCompleted(Bundle argBundle) {

				String responseJSON = argBundle
						.getString(TAListener.LISTENER_BUNDLE_STRING_1);
				 Type type = new TypeToken<List<Deals>>() {}.getType();
				 Gson gson=new Gson();
				
				try {
					deals= gson.fromJson(responseJSON, type);
					App app=(App) getApplication();
					app.setDeals(deals);
					setupUiComponent();
					
				} catch (JsonSyntaxException e) {
					Log.e("****", ""+e);
					
				}
		
				
			}

			
		};
		new TAPOSTWebServiceAsyncTask(DashboardActivity.this, null, taListener,
				WebServiceConstants.GET_ALL_DEALS, jsonObject.toString(), null)
				.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						(Void[]) null);
		


	
	
    	

    }
    private void setupUiComponent() {
		
    	
    	final Iterator<Deals> iterator = deals.iterator();
	    int i=0;
		while (iterator.hasNext()) {
			i++;
			Deals listItem = iterator.next();
			double[] randomLocation = { Double.parseDouble(listItem.getLatitude()),
					Double.parseDouble(listItem.getLongitude()) };
			
			 Marker marker=mMap.addMarker(new MarkerOptions()
            .position(new LatLng(randomLocation[0],randomLocation[1]))
            .title(listItem.getTitle())
            .snippet(listItem.getLocation())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

				if (i == 3) {
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(new LatLng(randomLocation[0],
										randomLocation[1])).zoom(10).build();

						mMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
					}
				
				markers.put(marker.getId(), listItem);
		
		}
		mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        
        mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView)findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);
        viewHotDeals = (Button) findViewById(R.id.viewHotDeals);
		mHlvCustomListWithDividerAndFadingEdge.setVisibility(View.GONE);
		viewHotDeals = (Button) findViewById(R.id.viewHotDeals);
		viewHotDeals.setBackgroundColor(Color.parseColor("#CB2934"));
		viewHotDeals.setText("Show hot deals");
		viewHotDeals.setVisibility(View.VISIBLE);
		viewHotDeals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
		if (hotDeals==null||hotDeals.size()==0) {
			NotificationUtils.showNotificationToast(DashboardActivity.this, "No hot deals available");
			return;
		}
		        
		            int listVisibility = mHlvCustomListWithDividerAndFadingEdge
							.getVisibility();
					if (listVisibility == View.VISIBLE) {
						Animation bottomUp = AnimationUtils.loadAnimation(
								DashboardActivity.this, R.anim.bottom_down);
						bottomUp.setAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationStart(Animation arg0) {
								viewHotDeals.setVisibility(View.INVISIBLE);

							}

							@Override
							public void onAnimationRepeat(Animation arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onAnimationEnd(Animation arg0) {
								viewHotDeals.setVisibility(View.VISIBLE);

							}
						});

						mHlvCustomListWithDividerAndFadingEdge
								.startAnimation(bottomUp);

						mHlvCustomListWithDividerAndFadingEdge
								.setVisibility(View.GONE);
						viewHotDeals.setText("Show hot deals");
					} else {
						Animation bottomUp = AnimationUtils.loadAnimation(
								DashboardActivity.this, R.anim.bottom_up);
						bottomUp.setAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationStart(Animation arg0) {
								viewHotDeals.setVisibility(View.INVISIBLE);

							}

							@Override
							public void onAnimationRepeat(Animation arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onAnimationEnd(Animation arg0) {
								viewHotDeals.setVisibility(View.VISIBLE);

							}
						});

						mHlvCustomListWithDividerAndFadingEdge
								.startAnimation(bottomUp);
						mHlvCustomListWithDividerAndFadingEdge
								.setVisibility(View.VISIBLE);
						viewHotDeals.setText("Hide hot deals");
					}
		            
		  
				

			}
		});
		hotDeals=getHotDeals(deals);
		mHlvCustomListWithDividerAndFadingEdge
				.setAdapter(new DealsSearchAdapter(DashboardActivity.this,
						R.layout.item_hot_deals, hotDeals));
  
	}

	private List<Deals> getHotDeals(List<Deals> deals) {
		List<Deals> hotDeals=new ArrayList<Deals>();
		Iterator<Deals> iterator=deals.iterator();
		while (iterator.hasNext()) {
			Deals deals2=iterator.next();
			if (deals2.getDealCategory().getCategoryName().equals("Hot Deals")) {
				hotDeals.add(deals2);
			}
			
		}
		
		return hotDeals;
	}
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
    }
   

	@Override
	public void onLocationChanged(Location location) {
		if (location!=null&&!isLocationAdded) {
			addMarkersToMap(""+location.getLatitude(), ""+location.getLongitude());
			isLocationAdded=true;
			
		}
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		mLocationClient.requestLocationUpdates(
	             REQUEST,
	             this);  // LocationListener
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	private void setUpLocationClientIfNeeded() {
	    if (mLocationClient == null) {
	        mLocationClient = new LocationClient(
	                getApplicationContext(),
	                this,  // ConnectionCallbacks
	                this); // OnConnectionFailedListener
	    }
	}
}
