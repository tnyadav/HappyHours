package com.happyhours.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.happyhours.R;

public class TASlidingMenuListAdapter extends BaseAdapter {

	private Activity mActivity;
	private LayoutInflater inflater;
	private String[] mFragmentTitles;
	private String[] mCount;

	public TASlidingMenuListAdapter(Activity argActivity) {
		mActivity = argActivity;
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mFragmentTitles = mActivity.getResources().getStringArray(
				R.array.drawer_items);
		
	}

	@Override
	public int getCount() {
		return mFragmentTitles.length;
	}

	@Override
	public Object getItem(int arg0) {
		return mFragmentTitles[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.menu_list_item, null);
				}

		TextView textViewDrawerMenuItem = (TextView) arg1
				.findViewById(R.id.textViewDrawerMenuItem);
		ImageView imageViewDrawerMenuItem = (ImageView) arg1
				.findViewById(R.id.imageViewDrawerMenuItem);
		if (textViewDrawerMenuItem != null)
			textViewDrawerMenuItem.setText(mFragmentTitles[arg0]);
		
		switch (arg0) {/*

		case 0:
			imageViewDrawerMenuItem.setImageDrawable(mActivity.getResources()
					.getDrawable(R.drawable.ic_drawer_store));
			
			break;
		case 1:
			imageViewDrawerMenuItem.setImageDrawable(mActivity.getResources()
					.getDrawable(R.drawable.ic_drawer_books));
			break;
		case 2:
			imageViewDrawerMenuItem.setImageDrawable(mActivity.getResources()
					.getDrawable(R.drawable.ic_drawer_authors));
			break;
		case 3:
			imageViewDrawerMenuItem.setImageDrawable(mActivity.getResources()
					.getDrawable(R.drawable.ic_drawer_settings));
			break;
		
		
		default:
			break;
		*/}
		
		return arg1;
	}

}
