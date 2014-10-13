package com.happyhours.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.happyhours.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.happyhours.adapter.TestFragmentAdapter;
import com.happyhours.fragments.TestFragment;
import com.happyhours.model.Data;
import com.happyhours.model.DealOffers;
import com.happyhours.model.Deals;
import com.happyhours.model.ListItem;
import com.viewpagerindicator.CirclePageIndicator;


public class DealsDetailActivity extends FragmentActivity implements ActionBar.OnNavigationListener{

	private static View detailFragment = null;
	private ScrollView mainScrollView;
	private GoogleMap googleMap;
	MapFragment mapFragment;
	private ViewPager pager;
	public TextView option1;
	public TextView option2;
	public TextView option3;
	public TextView detailTitle;
    public TextView nutshell;
	public TextView info;
	public TextView moreAbout,remainingDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setIcon(R.drawable.logo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.menu_text_color));
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getActionBar().setTitle("Happy Hours");
	  //  getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ArrayList<String> itemList = new ArrayList<String>();
		itemList.add("More");
		itemList.add("Find location");
		itemList.add("Check into the deal");
		itemList.add("Connect with people");
		itemList.add("Write a review");
		ArrayAdapter<String> aAdpt = new ArrayAdapter<String>(this,R.layout.list, android.R.id.text1, itemList);
		getActionBar().setListNavigationCallbacks(aAdpt, this);
		setContentView(R.layout.activity_deal_details);
		
		
		Deals deals=new Gson().fromJson(getIntent().getStringExtra("deal"), Deals.class);
		
		
		detailTitle=(TextView)findViewById(R.id.title);
		option1=(TextView)findViewById(R.id.option1);
		option2=(TextView)findViewById(R.id.option2);
		option3=(TextView)findViewById(R.id.option3);
		nutshell=(TextView)findViewById(R.id.nutshell);
	    info=(TextView)findViewById(R.id.info);
		moreAbout=(TextView)findViewById(R.id.moreInfo);
		remainingDay=(TextView)findViewById(R.id.remainingDay);
		
		
		detailTitle.setText(deals.getTitle());
		long remaing=Long.parseLong(deals.getEndDate())-System.currentTimeMillis();
		long days = TimeUnit.MILLISECONDS.toDays(remaing);
		remainingDay.setText(" "+days+" Days");
		List<DealOffers> dealOffersList=deals.getDealOffersList();
		
		option1.setText(dealOffersList.get(0).getOfferName());
		option2.setText(dealOffersList.get(1).getOfferName());
		option3.setText(dealOffersList.get(2).getOfferName());
		nutshell.setText(deals.getSubTitle());
		info.setText(deals.getDescription());
		//moreAbout.setText(listItem.moreAbout);
		pager = (ViewPager)findViewById(R.id.pager);
		TestFragmentAdapter mAdapter = new TestFragmentAdapter(getSupportFragmentManager(), deals.getDealImagesList(),DealsDetailActivity.this);

		 pager.setAdapter(mAdapter);
         CirclePageIndicator mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
	     mIndicator.setViewPager(pager);
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		// Check Google Play Service Available
		try {
			if (status != ConnectionResult.SUCCESS) {
				GooglePlayServicesUtil.getErrorDialog(status, this, 1).show();
			}
		} catch (Exception e) {
			Log.e("Error: GooglePlayServiceUtil: ", "" + e);
		}

		try {
			initilizeMap();
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			googleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);

			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			googleMap.getUiSettings().setZoomGesturesEnabled(true);
			double[] randomLocation = {Double.parseDouble(deals.getLatitude()) ,Double.parseDouble(deals.getLongitude())};
		//	double[] randomLocation = createRandLocation(Long.parseLong(listItem.lat), Long.parseLong(listItem.lang));

			// Adding a marker
			MarkerOptions marker = new MarkerOptions().position(
					new LatLng(randomLocation[0], randomLocation[1]))
					.title(deals.getTitle());
        	marker.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.pin_red));

			googleMap.addMarker(marker);

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(new LatLng(randomLocation[0], randomLocation[1]))
					.zoom(15).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	

	@Override
	public void onResume() {
		super.onResume();
		initilizeMap();
		mainScrollView = (ScrollView)findViewById(R.id.main_scrollview);
		ImageView transparentImageView = (ImageView) findViewById(R.id.transparent_image);

		transparentImageView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					// Disallow ScrollView to intercept touch events.
					mainScrollView.requestDisallowInterceptTouchEvent(true);
					// Disable touch on transparent view
					return false;

				case MotionEvent.ACTION_UP:
					// Allow ScrollView to intercept touch events.
					mainScrollView.requestDisallowInterceptTouchEvent(false);
					return true;

				case MotionEvent.ACTION_MOVE:
					mainScrollView.requestDisallowInterceptTouchEvent(true);
					return false;

				default:
					return true;
				}
			}

		});

	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap(/* Bundle savedInstanceState */) {
		if (googleMap == null) {

			mapFragment = (MapFragment) getFragmentManager()
					.findFragmentById(R.id.detailMap);

			googleMap = mapFragment.getMap();
			/*googleMap.setInfoWindowAdapter(new IconizedWindowAdapter(this
					.getLayoutInflater()));
			
			 * googleMap = ( (MapFragment)
			 * this.getFragmentManager().findFragmentById( R.id.map1)).getMap();
			 */

			// check if map is created successfully or not
			if (googleMap == null) {
				
				 Toast.makeText(this, "Sorry! unable to create maps",
				 Toast.LENGTH_SHORT).show();
				 
			}
			// removeMapView.removeMap(mMapView,googleMap);
		}
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			this.onBackPressed();
        break;
		case R.id.action_settings:
			
        break;

		
	}
		 
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
			//getMenuInflater().inflate(R.menu.main, menu);
			/*ImageButton locButton = (ImageButton) menu.findItem(R.id.action_settings).getActionView();
			locButton.setOnClickListener(new OnClickListener() {
				
				@Override
			public void onClick(View v) {
				QuickAction mQuickAction = new QuickAction(
						PropertyDetailActivity.this);

				ActionItem downloadItem = new ActionItem(1, "Download");
				ActionItem playItem = new ActionItem(2, "Play");

				mQuickAction.addActionItem(downloadItem);
				mQuickAction.addActionItem(playItem);

				mQuickAction
						.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
							@Override
							public void onItemClick(QuickAction quickAction,
									int pos, int actionId) {
								ActionItem actionItem = quickAction
										.getActionItem(pos);

								if (actionId == 1) {
									System.out.println("Download");
									Toast.makeText(PropertyDetailActivity.this,
											"Add item selected",
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(
											PropertyDetailActivity.this,
											actionItem.getTitle() + " selected",
											Toast.LENGTH_SHORT).show();
									System.out.println("Play");
								}
							}
						});
				mQuickAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
				mQuickAction.show(v);

			}
			});*/
		return true;
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

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		switch (arg0) {
		case 1:
			mainScrollView.fullScroll(View.FOCUS_DOWN);
			break;

		default:
			break;
		}
		return true;
	}
}
