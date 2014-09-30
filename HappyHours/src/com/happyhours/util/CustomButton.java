package com.happyhours.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button {

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomButton(Context context) {
		super(context);
		init();
	}

	private void init() {
		/*Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
		"fonts/calibri.otf");*/
/*Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
"fonts/Roboto-Light.ttf");

		setPadding(10, 10, 10, 10);*/
	}
}