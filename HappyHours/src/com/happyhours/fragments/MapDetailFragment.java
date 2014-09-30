package com.happyhours.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.happyhours.activity.DealsDetailActivity;
import com.happyhours.adapter.IconizedWindowAdapter;
import com.happyhours.adapter.DealsSearchAdapter;
import com.happyhours.model.Data;
import com.happyhours.model.ListItem;
import com.meetme.android.horizontallistview.HorizontalListView;

public class MapDetailFragment extends BaseFragment {
private static 	View detailMapFragment;
	private GoogleMap googleMap;
	private HashMap<String, ListItem> eventMarkerMap;
	ListItem temp;
	 private HorizontalListView mHlvCustomListWithDividerAndFadingEdge;
	 List<ListItem> propertyList;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			 eventMarkerMap = new HashMap<String, ListItem>();
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

					/*double latitude = 28.6100;
					double longitude = 77.2300;*/
					ArrayList<ListItem> propertyList=Data.getData();
					// lets place some 10 random markers
					Iterator<ListItem> iterator=propertyList.iterator();
					int i = 0;
					while (iterator.hasNext()) {
						 i = i+1;
						ListItem listItem =  iterator.next();
						double[] randomLocation = {Double.parseDouble(listItem.lat) ,Double.parseDouble(listItem.lang)};
						MarkerOptions marker = new MarkerOptions().position(
								new LatLng(randomLocation[0], randomLocation[1]))
								.title(listItem.title1);
						marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						googleMap.addMarker(marker);
						
						eventMarkerMap.put(listItem.title1, listItem);
						final IconizedWindowAdapter iconizedWindowAdapter=new IconizedWindowAdapter(
				                dashboardActivity.getLayoutInflater());
						googleMap.setInfoWindowAdapter(iconizedWindowAdapter);
						
						googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
							
							@Override
							public void onInfoWindowClick(Marker marker) {
								temp=eventMarkerMap.get(marker.getTitle());
								Intent intent=new Intent(getActivity(), DealsDetailActivity.class);
								intent.putExtra("index", temp.id);
								dashboardActivity.startActivity(intent);
							
							}
						});
						
						if ( i==3 ) {
							CameraPosition cameraPosition = new CameraPosition.Builder()
									.target(new LatLng(randomLocation[0],
											randomLocation[1])).zoom(8).build();

							googleMap.animateCamera(CameraUpdateFactory
									.newCameraPosition(cameraPosition));
						}
					}
				/*	for (int i = 0; i < 10; i++) {
						// random latitude and logitude
						double[] randomLocation = createRandLocation(latitude,
								longitude);

						// Adding a marker
						MarkerOptions marker = new MarkerOptions().position(
								new LatLng(randomLocation[0], randomLocation[1]))
								.title("Hello Maps " + i);
						
						Log.e("Random", "> " + randomLocation[0] + ", "
								+ randomLocation[1]);

						// changing marker color
						if (i == 0)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 1)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 2)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 3)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 4)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 5)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 6)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 7)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 8)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
						if (i == 9)
							marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red));
							marker.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

						googleMap.addMarker(marker);
						

						// Move the camera to last position with a zoom level
						if (i == 9) {
							CameraPosition cameraPosition = new CameraPosition.Builder()
									.target(new LatLng(randomLocation[0],
											randomLocation[1])).zoom(15).build();

							googleMap.animateCamera(CameraUpdateFactory
									.newCameraPosition(cameraPosition));
						}
					}*/

				} catch (Exception e) {
					e.printStackTrace();
				}
			
				 mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView) detailMapFragment.findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);
				 propertyList=Data.getData();
				 mHlvCustomListWithDividerAndFadingEdge.setAdapter(new DealsSearchAdapter(getActivity(),
							R.layout.deals_item, propertyList));

		return detailMapFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	
	private void initilizeMap() {
		if (googleMap == null) {
			
		MapFragment mapFragment=(MapFragment) getActivity().getFragmentManager().findFragmentById(
				R.id.detailMap);
		
		googleMap=mapFragment.getMap();
		/*googleMap.setInfoWindowAdapter(new IconizedWindowAdapter(
                getActivity().getLayoutInflater()));*/
			/*googleMap = ( (MapFragment) getActivity().getFragmentManager().findFragmentById(
					R.id.map1)).getMap();*/
			
			// check if map is created successfully or not
			if (googleMap == null) {
				/*Toast.makeText(getActivity(), "Sorry! unable to create maps",
						Toast.LENGTH_SHORT).show();*/
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
		// TODO Auto-generated method stub
		
	}

	
}
