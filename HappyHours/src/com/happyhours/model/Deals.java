package com.happyhours.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Deals {

	
	@Expose
	private String id;
	@Expose
	private String title;
	@Expose
	private String subTitle;
	public String getSubTitle() {
		return subTitle;
	}

	

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@Expose
	private String description;
	@Expose
	private String location;
	@Expose
	private String latitude;
	@Expose
	private String longitude;
	@Expose
	private String originalPrice;
	@Expose
	private String newPrice;
	@Expose
	private String discount;
	@Expose
	private String dealMainImage;
	@Expose
	private List<DealOffers> dealOffersList = new ArrayList<DealOffers>();
	@Expose
	private List<DealImages> dealImagesList = new ArrayList<DealImages>();
	@Expose
	private String startDate;
	@Expose
	private String endDate;
	@Expose
	private Boolean dealType;
	@Expose
	private Boolean isExpired;
	@Expose
	private String relativeDistance;
	@Expose
	private String requiredDistance;

	public String getId() {
	return id;
	}

	public void setId(String id) {
	this.id = id;
	}

	public String getTitle() {
	return title;
	}

	public void setTitle(String title) {
	this.title = title;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	public String getLocation() {
	return location;
	}

	public void setLocation(String location) {
	this.location = location;
	}

	public String getLatitude() {
	return latitude;
	}

	public void setLatitude(String latitude) {
	this.latitude = latitude;
	}

	public String getLongitude() {
	return longitude;
	}

	public void setLongitude(String longitude) {
	this.longitude = longitude;
	}

	public String getOriginalPrice() {
	return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
	this.originalPrice = originalPrice;
	}

	public String getNewPrice() {
	return newPrice;
	}

	public void setNewPrice(String newPrice) {
	this.newPrice = newPrice;
	}

	public String getDiscount() {
	return discount;
	}

	public void setDiscount(String discount) {
	this.discount = discount;
	}

	public String getDealMainImage() {
	return dealMainImage;
	}

	public void setDealMainImage(String dealMainImage) {
	this.dealMainImage = dealMainImage;
	}

	public List<DealOffers> getDealOffersList() {
	return dealOffersList;
	}

	public void setDealOffersList(List<DealOffers> dealOffersList) {
	this.dealOffersList = dealOffersList;
	}

	public List<DealImages> getDealImagesList() {
	return dealImagesList;
	}

	public void setDealImagesList(List<DealImages> dealImagesList) {
	this.dealImagesList = dealImagesList;
	}

	public String getStartDate() {
	return startDate;
	}

	public void setStartDate(String startDate) {
	this.startDate = startDate;
	}

	public String getEndDate() {
	return endDate;
	}

	public void setEndDate(String endDate) {
	this.endDate = endDate;
	}

	public Boolean getDealType() {
	return dealType;
	}

	public void setDealType(Boolean dealType) {
	this.dealType = dealType;
	}

	public Boolean getIsExpired() {
	return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
	this.isExpired = isExpired;
	}

	public String getRelativeDistance() {
	return relativeDistance;
	}

	public void setRelativeDistance(String relativeDistance) {
	this.relativeDistance = relativeDistance;
	}

	public Object getRequiredDistance() {
	return requiredDistance;
	}

	public void setRequiredDistance(String requiredDistance) {
	this.requiredDistance = requiredDistance;
	}
	@Override
	public String toString() {
		return "Deals [id=" + id + ", title=" + title + ", subTitle="
				+ subTitle + ", description=" + description + ", location="
				+ location + ", latitude=" + latitude + ", longitude="
				+ longitude + ", originalPrice=" + originalPrice
				+ ", newPrice=" + newPrice + ", discount=" + discount
				+ ", dealMainImage=" + dealMainImage + ", dealOffersList="
				+ dealOffersList + ", dealImagesList=" + dealImagesList
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", dealType=" + dealType + ", isExpired=" + isExpired
				+ ", relativeDistance=" + relativeDistance
				+ ", requiredDistance=" + requiredDistance + "]";
	}
}
