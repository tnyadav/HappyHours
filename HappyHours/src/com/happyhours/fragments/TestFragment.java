package com.happyhours.fragments;

import com.app.happyhours.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TestFragment extends Fragment{
	static int resId;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(
				R.layout.galery, container, false);	
		ImageView imageView=(ImageView)view.findViewById(R.id.image);
		imageView.setImageResource(resId);
				return view;
		
	}
	 public static TestFragment newInstance(int id) {
		 resId=id;
		 TestFragment fragment = new TestFragment();
		 
		 return fragment;
		 }
}
