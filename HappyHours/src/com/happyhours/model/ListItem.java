package com.happyhours.model;

import android.widget.TextView;

public class ListItem {

	public int id;
	public int propertyIcon;
	public String title1;
	public String title2;
	public String title3;
	public String oldPrice;
	public String newPrice;
	
	public String option1;
	public String option2;
	public String option3;
	public String detailTitle;
    public String Nutshell;
	public String info;
	public String moreAbout;
	public String off;
	public String lat;
	public String lang;
	public  int []image;
	
	public ListItem(int id,int propertyIcon, String title1, String title2,
			String title3, String oldPrice, String newPrice, String option1,
			String option2, String option3, String detailTitle,
			String nutshell, String info, String moreAbout,String off,String lat,String lang,int []image) {
		super();
		this.id=id;
		this.propertyIcon = propertyIcon;
		this.title1 = title1;
		this.title2 = title2;
		this.title3 = title3;
		this.oldPrice = oldPrice;
		this.newPrice = newPrice;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.detailTitle = detailTitle;
		Nutshell = nutshell;
		this.info = info;
		this.moreAbout = moreAbout;
		this.off=off;
		this.lat=lat;
		this.lang=lang;
		this.image=image;
	}

	@Override
	public String toString() {
		return "ListItem [propertyIcon=" + propertyIcon + ", title1=" + title1
				+ ", title2=" + title2 + ", title3=" + title3 + ", oldPrice="
				+ oldPrice + ", newPrice=" + newPrice + ", option1=" + option1
				+ ", option2=" + option2 + ", option3=" + option3
				+ ", detailTitle=" + detailTitle + ", Nutshell=" + Nutshell
				+ ", info=" + info + ", moreAbout=" + moreAbout + ", off="
				+ off + ", lat=" + lat + ", lang=" + lang + "]";
	}
	

	
	
}
