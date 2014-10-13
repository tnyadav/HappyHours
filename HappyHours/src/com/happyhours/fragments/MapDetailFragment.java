package com.happyhours.fragments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

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
import com.happyhours.activity.DealsDetailActivity;
import com.happyhours.adapter.DealsSearchAdapter;
import com.happyhours.adapter.IconizedWindowAdapter;
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
	private Button viewHotDeals;

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

		if (!Utils.isNetworkAvailable(dashboardActivity)) {
			NotificationUtils.showNotificationToast(dashboardActivity, "No network connection");
			return;
		}
		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("latitude", 25.198342);
			jsonObject.put("longitude", 55.274140);
			jsonObject.put("requiredDistance", 500);

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

		mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView) detailMapFragment
				.findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);
	//	propertyList = Data.getData();
		
		
		mHlvCustomListWithDividerAndFadingEdge
				.setAdapter(new DealsSearchAdapter(getActivity(),
						R.layout.item_hot_deals, getHotDeals(deals)));
		
		
		mHlvCustomListWithDividerAndFadingEdge.setVisibility(View.GONE);
		viewHotDeals = (Button) detailMapFragment
				.findViewById(R.id.viewHotDeals);

		viewHotDeals.setText("Show hot deals");
		viewHotDeals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

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
}
