package com.happyhours.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.app.happyhours.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class IconizedWindowAdapter implements InfoWindowAdapter {
	LayoutInflater inflater = null;
	String title;

	public IconizedWindowAdapter(LayoutInflater inflater/*,String title*/) {
		this.inflater = inflater;
		//this.title=title;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return (null);
	}

	@Override
	public View getInfoContents(Marker marker) {

		View popup = inflater.inflate(R.layout.custome_info_window, null);

		TextView textView = (TextView) popup.findViewById(R.id.textView_detail);
		textView.setText(marker.getTitle());
		
		return (popup);
	}
}