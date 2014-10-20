package com.happyhours.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.app.happyhours.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.happyhours.activity.App;
import com.happyhours.activity.DashboardActivity;
import com.happyhours.activity.DealsDetailActivity;
import com.happyhours.activity.DealsSearchResultActivity;
import com.happyhours.adapter.DealsSearchAdapter;
import com.happyhours.adapter.IconizedWindowAdapter;
import com.happyhours.location.LocationUtils;
import com.happyhours.model.Data;
import com.happyhours.model.Deals;
import com.happyhours.model.ListItem;
import com.happyhours.util.NotificationUtils;
import com.happyhours.util.TAListener;
import com.happyhours.util.Utils;
import com.jainbooks.web.TAPOSTWebServiceAsyncTask;
import com.jainbooks.web.WebServiceConstants;
import com.meetme.android.horizontallistview.HorizontalListView;

public class MapDetailFragment extends BaseFragment {
	private static View detailMapFragment;
	private GoogleMap googleMap;
	private HashMap<String, Deals> eventMarkerMap;
	Deals temp;
	private HorizontalListView mHlvCustomListWithDividerAndFadingEdge;
//	List<ListItem> propertyList;
	List<Deals> deals;
	List<Deals> hotDeals;
	private Button viewHotDeals;
	SearchView searchView ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		eventMarkerMap = new HashMap<String, Deals>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (detailMapFragment != null) {
			ViewGroup parent = (ViewGroup) detailMapFragment.getParent();
			if (parent != null)
				parent.removeView(detailMapFragment);
		}

		detailMapFragment = inflater.inflate(R.layout.fragment_map_detail,
				container, false);
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());

		// Check Google Play Service Available
		try {
			if (status != ConnectionResult.SUCCESS) {
				GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 1)
						.show();
			}
		} catch (Exception e) {
			Log.e("Error: GooglePlayServiceUtil: ", "" + e);
		}

		

		return detailMapFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		
		
     

	}
	private void location() {
		   // If Google Play Services is available
				if (dashboardActivity.servicesConnected()) {

					// Get the current location
					Location currentLocation = dashboardActivity.mLocationClient
							.getLastLocation();
					String[] randomLocation = LocationUtils.getLatLng(
							dashboardActivity, currentLocation);
					// remove 
					//randomLocation=null;
					if (randomLocation != null) {
						randerDataOnMap(randomLocation[0], randomLocation[1]);
					}

				} else {
					 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							 dashboardActivity);
				 
							// set title
							alertDialogBuilder.setTitle(R.string.app_name);
				 
							// set dialog message
							alertDialogBuilder
								.setMessage("Unable to fond location.Try again ?")
								.setCancelable(false)
								.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										dialog.cancel();
										location();
									}
								  })
								.setNegativeButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dashboardActivity.finish();
									}
								});
				 
								// create alert dialog
								AlertDialog alertDialog = alertDialogBuilder.create();
				 
								// show it
								alertDialog.show();
				}
	}
	private void initilizeMap() {
		if (googleMap == null) {

			MapFragment mapFragment = (MapFragment) getActivity()
					.getFragmentManager().findFragmentById(R.id.detailMap);

			googleMap = mapFragment.getMap();
		

			if (googleMap == null) {
			
			}
		}
	}

	@Override
	public void onResume() {
		initilizeMap();
		super.onResume();
		mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView) detailMapFragment
				.findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);
		
		mHlvCustomListWithDividerAndFadingEdge.setVisibility(View.GONE);
		viewHotDeals = (Button) detailMapFragment
				.findViewById(R.id.viewHotDeals);

		viewHotDeals.setText("Show hot deals");
		viewHotDeals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
		if (hotDeals==null||hotDeals.size()==0) {
			NotificationUtils.showNotificationToast(dashboardActivity, "No hot deals available");
			return;
		}
		        
		            int listVisibility = mHlvCustomListWithDividerAndFadingEdge
							.getVisibility();
					if (listVisibility == View.VISIBLE) {
						Animation bottomUp = AnimationUtils.loadAnimation(
								getActivity(), R.anim.bottom_down);
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
								getActivity(), R.anim.bottom_up);
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
		

		if (!Utils.isNetworkAvailable(dashboardActivity)) {
			NotificationUtils.showNotificationToast(dashboardActivity, "No network connection");
			return;
		}
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				location();
			}
		}, 1000);
	}

	@Override
	void setupUiComponent() {
		
		
		try {
			// Loading map
			initilizeMap();

			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			// Showing / hiding your current location
			googleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(true);

			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			/*
			 * double latitude = 28.6100; double longitude = 77.2300;
			 */
			// lets place some 10 random markers
			Iterator<Deals> iterator = deals.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i = i + 1;
				Deals listItem = iterator.next();
				double[] randomLocation = { Double.parseDouble(listItem.getLatitude()),
						Double.parseDouble(listItem.getLongitude()) };
				MarkerOptions marker = new MarkerOptions().position(
						new LatLng(randomLocation[0], randomLocation[1]))
						.title(listItem.getTitle());
				marker.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.pin_red));
				googleMap.addMarker(marker);

				eventMarkerMap.put(listItem.getTitle(), listItem);
				final IconizedWindowAdapter iconizedWindowAdapter = new IconizedWindowAdapter(
						dashboardActivity.getLayoutInflater());
				googleMap.setInfoWindowAdapter(iconizedWindowAdapter);

				googleMap
						.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

							@Override
							public void onInfoWindowClick(Marker marker) {
								temp = eventMarkerMap.get(marker.getTitle());
								Intent intent = new Intent(getActivity(),
										DealsDetailActivity.class);
								String deal=new Gson().toJson(temp);
								intent.putExtra("deal", deal);
								dashboardActivity.startActivity(intent);

							}
						});

			if (i == 3) {
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(randomLocation[0],
									randomLocation[1])).zoom(10).build();

					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		hotDeals=getHotDeals(deals);
		mHlvCustomListWithDividerAndFadingEdge
				.setAdapter(new DealsSearchAdapter(getActivity(),
						R.layout.item_hot_deals, hotDeals));
	
	}
	

	private List<Deals> getHotDeals(List<Deals> deals) {
		List<Deals> hotDeals=new ArrayList<Deals>();
		Iterator<Deals> iterator=deals.iterator();
		while (iterator.hasNext()) {
			Deals deals2=iterator.next();
			if (deals2.getDealType()) {
				hotDeals.add(deals2);
			}
			
		}
		
		return hotDeals;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub

		
		inflater.inflate(R.menu.map, menu);
		
		   searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
		  searchView.setOnQueryTextListener(queryListener);
		  super.onCreateOptionsMenu(menu, inflater);
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
	    	
		        Intent intent =new Intent(dashboardActivity, DealsSearchResultActivity.class);
		        intent.putExtra("SEARCH_STRING", query.toLowerCase().replaceAll(" ", "%20"));
		        startActivity(intent);
		       
			}
	    	
	        return false;
	    }
	};
	
	private void randerDataOnMap(String lat,String lang) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("latitude", 25.198342);
			jsonObject.put("longitude", 55.274140);
/*			jsonObject.put("latitude", lat);
			jsonObject.put("longitude", lang);*/
			jsonObject.put("requiredDistance", 1000);

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
					App app=(App) dashboardActivity.getApplication();
					app.setDeals(deals);
					setupUiComponent();
					
				} catch (JsonSyntaxException e) {
					Log.e("****", ""+e);
					
				}
		
				
				
			
				
			}
		};
		new TAPOSTWebServiceAsyncTask(getActivity(), null, taListener,
				WebServiceConstants.GET_ALL_DEALS, jsonObject.toString(), null)
				.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						(Void[]) null);
		


	
	}
}
