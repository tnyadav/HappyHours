package com.happyhours.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.happyhours.R;
import com.happyhours.fragments.TestFragment;
import com.happyhours.model.DealImages;
import com.viewpagerindicator.IconPagerAdapter;

public class TestFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {
/*	protected static final String[] CONTENT = new String[] { "This", "Is", "A",
			"Test", };*/
	protected int[] ICONS;
	private int mCount;/* = CONTENT.length;*/
	Context context;
	List<DealImages> dealImages;
	
	    public TestFragmentAdapter(FragmentManager fm, List<DealImages> dealImages,Context context) {
		super(fm);
		this.dealImages = dealImages;
		this.context=context;
		mCount=dealImages.size();
		 
	}

	@Override
	public Fragment getItem(int position) {
		return new TestFragment(dealImages.get(position).getImage(),context);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		//return TestFragmentAdapter.CONTENT[position % CONTENT.length];
		return "";
	}

	@Override
	public int getIconResId(int index) {
		//return ICONS[index % ICONS.length];
		return 0;
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}
}