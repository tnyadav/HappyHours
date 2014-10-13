package com.happyhours.fragments;

import com.app.happyhours.R;
import com.happyhours.util.ImageLoader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TestFragment extends Fragment {
	private  String url;
	ImageLoader imageLoader;

	public TestFragment(String url,Context context ) {
		this.url = url;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.galery, container, false);
		ImageView imageView = (ImageView) view.findViewById(R.id.image);
		try {
			imageLoader.displayImage(url, imageView);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;

	}

}
