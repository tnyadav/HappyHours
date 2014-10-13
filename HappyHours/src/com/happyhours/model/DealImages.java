package com.happyhours.model;

import com.google.gson.annotations.Expose;

public class DealImages {
	@Expose
	private Integer id;
	@Expose
	private String image;

	public Integer getId() {
	return id;
	}

	public void setId(Integer id) {
	this.id = id;
	}

	public String getImage() {
	return image;
	}

	public void setImage(String image) {
	this.image = image;
	}

}
