package com.happyhours.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.app.happyhours.R;
import com.happyhours.adapter.DealsSearchAdapter;
import com.happyhours.model.Data;
import com.happyhours.model.ListItem;

public class DealsSearchResultActivity extends Activity {
private final int baseId=12345;
private final int basePrice=540000;
Button buttonBack;
View propertySearchFragment;
List<ListItem> propertyList;
List<ListItem> tempPropertyList;
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
        setContentView(R.layout.property_search_result_fragment);
        propertyList=Data.getData();
		String search;
		try {
			tempPropertyList=new ArrayList<ListItem>();
			search = getIntent().getStringExtra("SEARCH_STRING");
			 Iterator<ListItem> iterator=propertyList.iterator();
		        while (iterator.hasNext()) {
		    	   ListItem listItem = (ListItem) iterator.next();

		    	   if (listItem.detailTitle.toLowerCase().contains(search)||listItem.Nutshell.toLowerCase().contains(search)) {
		    		   tempPropertyList.add(listItem);
				}
		        }
		} catch (Exception e) {
			
			e.printStackTrace();
			tempPropertyList=propertyList;
		}
       
		
	
				 
				ListView gridView =(ListView)findViewById(R.id.propertyListGrid);
		        gridView.setAdapter(new DealsSearchAdapter(this,
						R.layout.deals_item, tempPropertyList));
		        gridView.setOnItemClickListener(new OnItemClickListener() {
		        	
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						 ListItem listItem  = tempPropertyList.get(arg2);
						Intent intent=new Intent(DealsSearchResultActivity.this, DealsDetailActivity.class);
						intent.putExtra("index", listItem.id);
						startActivity(intent);
						
					}
				});
		        
		        
		       
		}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	
		getMenuInflater().inflate(R.menu.map, menu);
		
		  searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
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
    	
	        Intent intent =new Intent(DealsSearchResultActivity.this, DealsSearchResultActivity.class);
	        intent.putExtra("SEARCH_STRING", query.toLowerCase().replaceAll(" ", "%20"));
	        startActivity(intent);
	        finish();
	       
		}
    	
        return false;
    }
};
}
