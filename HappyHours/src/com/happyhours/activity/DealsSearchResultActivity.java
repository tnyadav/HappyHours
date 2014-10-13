package com.happyhours.activity;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.app.happyhours.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.happyhours.adapter.DealsSearchAdapter;
import com.happyhours.model.Deals;
import com.happyhours.util.TAListener;
import com.jainbooks.web.TAPOSTWebServiceAsyncTask;
import com.jainbooks.web.TAWebServiceAsyncTask;
import com.jainbooks.web.WebServiceConstants;

public class DealsSearchResultActivity extends Activity {
	private final int baseId = 12345;
	private final int basePrice = 540000;
	Button buttonBack;
	View propertySearchFragment;
	List<Deals> deals;
	// List<Deals> tempPropertyList;
	public SearchView searchView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setIcon(R.drawable.logo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.menu_text_color));
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getActionBar().setTitle("Happy Hours");
		setContentView(R.layout.fragment_deals_search_result);
		final ListView gridView = (ListView) findViewById(R.id.propertyListGrid);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Deals listItem = deals.get(arg2);
				Intent intent = new Intent(DealsSearchResultActivity.this,
						DealsDetailActivity.class);
				String deal = new Gson().toJson(listItem);
				intent.putExtra("deal", deal);
				startActivity(intent);

			}
		});
		String search=null;
		
		try {
			
			search = getIntent().getStringExtra("SEARCH_STRING");
			if (search == null || TextUtils.isEmpty(search)) {
				App app = (App) getApplication();
				deals = app.getDeals();
				if (deals != null) {
					gridView.setAdapter(new DealsSearchAdapter(this,
							R.layout.item_deals, deals));
				}
				
			} else {
				
			

				TAListener taListener = new TAListener() {

					@Override
					public void onTaskFailed(Bundle argBundle) {

					}

					@Override
					public void onTaskCompleted(Bundle argBundle) {

						String responseJSON = argBundle
								.getString(TAListener.LISTENER_BUNDLE_STRING_1);
						Type type = new TypeToken<List<Deals>>() {
						}.getType();
						Gson gson = new Gson();

						try {
							deals = gson.fromJson(responseJSON, type);
							/*App app = (App) getApplication();
							app.setDeals(deals);*/
							if (deals != null) {
								gridView.setAdapter(new DealsSearchAdapter(
										DealsSearchResultActivity.this,
										R.layout.item_deals, deals));
							}

						} catch (Exception e) {
							Log.e("****", "" + e);

						}

					}
				};
				new TAWebServiceAsyncTask(DealsSearchResultActivity.this, null, taListener, WebServiceConstants.SEARCH_DEALS+search, false).executeOnExecutor(
						AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
				
			}

		} catch (Exception e) {

		}
		/*
		 * App app=(App) getApplication(); deals=app.getDeals(); if
		 * (deals!=null) { gridView.setAdapter(new DealsSearchAdapter(this,
		 * R.layout.item_deals, deals)); }
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.map, menu);

		searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setOnQueryTextListener(queryListener);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item = menu.findItem(R.id.action_example);
		item.setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			this.onBackPressed();

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
			/*
			 * if (TextUtils.isEmpty(newText)) {
			 * getActivity().getActionBar().setSubtitle("List"); searchString =
			 * null; } else {
			 * getActivity().getActionBar().setSubtitle("List - Searching for: "
			 * + newText); searchString = newText;
			 * 
			 * } getLoaderManager().restartLoader(0, null, MyListFragment.this);
			 */
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			searchView.clearFocus();
			if (!query.isEmpty()) {

				Intent intent = new Intent(DealsSearchResultActivity.this,
						DealsSearchResultActivity.class);
				intent.putExtra("SEARCH_STRING", query.toLowerCase()
						.replaceAll(" ", "%20"));
				startActivity(intent);
				finish();

			}

			return false;
		}
	};
}
