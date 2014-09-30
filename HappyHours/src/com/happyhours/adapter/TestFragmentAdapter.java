package com.happyhours.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.happyhours.R;
import com.happyhours.fragments.TestFragment;
import com.viewpagerindicator.IconPagerAdapter;

public class TestFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
protected static final String[] CONTENT = new String[] { "This", "Is", "A", "Test", };
protected   int[] ICONS;
private int mCount = CONTENT.length;
public TestFragmentAdapter(FragmentManager fm,int[] ICONS) {
super(fm);
this.ICONS=ICONS;
}
@Override
public Fragment getItem(int position) {
return TestFragment.newInstance(ICONS[position]);
}
@Override
public int getCount() {
return mCount;
}
@Override
public CharSequence getPageTitle(int position) {
return TestFragmentAdapter.CONTENT[position % CONTENT.length];
}
@Override
public int getIconResId(int index) {
return ICONS[index % ICONS.length];
}
public void setCount(int count) {
if (count > 0 && count <= 10) {
mCount = count;
notifyDataSetChanged();
}
}
}