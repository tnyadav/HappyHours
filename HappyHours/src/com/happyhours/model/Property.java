package com.happyhours.model;

import java.util.HashMap;
import java.util.Map;

public class Property {

	private String propertyType = null;
	private String propertyArea = null;

	private String propertyBedroomCount = null;
	private String propertyBathroomCount = null;

	/**
	 * @return the propertyBathroomCount
	 */
	public String getPropertyBathroomCount() {
		return propertyBathroomCount;
	}

	/**
	 * @param propertyBathroomCount the propertyBathroomCount to set
	 */
	public void setPropertyBathroomCount(String propertyBathroomCount) {
		this.propertyBathroomCount = propertyBathroomCount;
	}

	private String propertyCost;

	// ABOUT
	private String propertyAbout = null;
	private String propertyUse = null;
	private String propertyEnergyRating = null;
	private String propertyYear = null;
	private String propertyZone = null;
	// AREA
	private String propertyGrossBuilt = null;
	private String propertyStorage = null;
	private String propertyTotalLand = null;
	private String propertyBackyard = null;
	private String propertyTerrace = null;
	private String propertyNetBuilt = null;
	// LOCALITY
	private String propertyRoadAccess = null;
	private String propertyServices = null;
	private String propertyAreaCondition = null;
	private String propertyNoiseLevel = null;
	private String propertyPublicTransportation = null;

	// CONTACT
	private String propertyContactNumber;

	// LOCATION
	private String propertyLatitude;
	private String propertyLongitude;

	public Map<String, String> getDummyproperty() {
		Map<String, String> dummyproperty = new HashMap<String, String>();
		dummyproperty.put(propertyType, "Aparment / Flat");
		dummyproperty.put(propertyArea, "69m2");
		dummyproperty.put(propertyBedroomCount, "3");
		dummyproperty.put(propertyBathroomCount, "1");
		dummyproperty.put(propertyCost, "29.000,00 $");
		dummyproperty.put(propertyAbout,
				"Piso En planta Baje Con Grand Patio...");
		dummyproperty.put(propertyUse, "Residential");

		dummyproperty.put(propertyEnergyRating, "G");
		dummyproperty.put(propertyYear, "1958");
		dummyproperty.put(propertyZone, "Near City Center");
		dummyproperty.put(propertyGrossBuilt, "69m2");
		dummyproperty.put(propertyStorage, "");
		dummyproperty.put(propertyTotalLand, "");
		dummyproperty.put(propertyBackyard, "");
		dummyproperty.put(propertyTerrace, "");
		dummyproperty.put(propertyNetBuilt, "");

		dummyproperty.put(propertyRoadAccess, "");
		dummyproperty.put(propertyServices, "");
		dummyproperty.put(propertyAreaCondition, "");
		dummyproperty.put(propertyNoiseLevel, "");
		dummyproperty.put(propertyPublicTransportation, "");

		dummyproperty.put(propertyContactNumber, "18002004545");
		dummyproperty.put(propertyLatitude, "28.6100");
		dummyproperty.put(propertyLatitude, "77.2300");

		return dummyproperty;

	}

	/**
	 * @return the propertyType
	 */
	public String getPropertyType() {
		return propertyType;
	}

	/**
	 * @param propertyType
	 *            the propertyType to set
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	/**
	 * @return the propertyArea
	 */
	public String getPropertyArea() {
		return propertyArea;
	}

	/**
	 * @param propertyArea
	 *            the propertyArea to set
	 */
	public void setPropertyArea(String propertyArea) {
		this.propertyArea = propertyArea;
	}

	/**
	 * @return the propertyBedroomCount
	 */
	public String getPropertyBedroomCount() {
		return propertyBedroomCount;
	}

	/**
	 * @param propertyBedroomCount
	 *            the propertyBedroomCount to set
	 */
	public void setPropertyBedroomCount(String propertyBedroomCount) {
		this.propertyBedroomCount = propertyBedroomCount;
	}

	/**
	 * @return the propertyCost
	 */
	public String getPropertyCost() {
		return propertyCost;
	}

	/**
	 * @param propertyCost
	 *            the propertyCost to set
	 */
	public void setPropertyCost(String propertyCost) {
		this.propertyCost = propertyCost;
	}

	/**
	 * @return the propertyAbout
	 */
	public String getPropertyAbout() {
		return propertyAbout;
	}

	/**
	 * @param propertyAbout
	 *            the propertyAbout to set
	 */
	public void setPropertyAbout(String propertyAbout) {
		this.propertyAbout = propertyAbout;
	}

	/**
	 * @return the propertyUse
	 */
	public String getPropertyUse() {
		return propertyUse;
	}

	/**
	 * @param propertyUse
	 *            the propertyUse to set
	 */
	public void setPropertyUse(String propertyUse) {
		this.propertyUse = propertyUse;
	}

	/**
	 * @return the propertyEnergyRating
	 */
	public String getPropertyEnergyRating() {
		return propertyEnergyRating;
	}

	/**
	 * @param propertyEnergyRating
	 *            the propertyEnergyRating to set
	 */
	public void setPropertyEnergyRating(String propertyEnergyRating) {
		this.propertyEnergyRating = propertyEnergyRating;
	}

	/**
	 * @return the propertyYear
	 */
	public String getPropertyYear() {
		return propertyYear;
	}

	/**
	 * @param propertyYear
	 *            the propertyYear to set
	 */
	public void setPropertyYear(String propertyYear) {
		this.propertyYear = propertyYear;
	}

	/**
	 * @return the propertyZone
	 */
	public String getPropertyZone() {
		return propertyZone;
	}

	/**
	 * @param propertyZone
	 *            the propertyZone to set
	 */
	public void setPropertyZone(String propertyZone) {
		this.propertyZone = propertyZone;
	}

	/**
	 * @return the propertyGrossBuilt
	 */
	public String getPropertyGrossBuilt() {
		return propertyGrossBuilt;
	}

	/**
	 * @param propertyGrossBuilt
	 *            the propertyGrossBuilt to set
	 */
	public void setPropertyGrossBuilt(String propertyGrossBuilt) {
		this.propertyGrossBuilt = propertyGrossBuilt;
	}

	/**
	 * @return the propertyStorage
	 */
	public String getPropertyStorage() {
		return propertyStorage;
	}

	/**
	 * @param propertyStorage
	 *            the propertyStorage to set
	 */
	public void setPropertyStorage(String propertyStorage) {
		this.propertyStorage = propertyStorage;
	}

	/**
	 * @return the propertyTotalLand
	 */
	public String getPropertyTotalLand() {
		return propertyTotalLand;
	}

	/**
	 * @param propertyTotalLand
	 *            the propertyTotalLand to set
	 */
	public void setPropertyTotalLand(String propertyTotalLand) {
		this.propertyTotalLand = propertyTotalLand;
	}

	/**
	 * @return the propertyBackyard
	 */
	public String getPropertyBackyard() {
		return propertyBackyard;
	}

	/**
	 * @param propertyBackyard
	 *            the propertyBackyard to set
	 */
	public void setPropertyBackyard(String propertyBackyard) {
		this.propertyBackyard = propertyBackyard;
	}

	/**
	 * @return the propertyTerrace
	 */
	public String getPropertyTerrace() {
		return propertyTerrace;
	}

	/**
	 * @param propertyTerrace
	 *            the propertyTerrace to set
	 */
	public void setPropertyTerrace(String propertyTerrace) {
		this.propertyTerrace = propertyTerrace;
	}

	/**
	 * @return the propertyNetBuilt
	 */
	public String getPropertyNetBuilt() {
		return propertyNetBuilt;
	}

	/**
	 * @param propertyNetBuilt
	 *            the propertyNetBuilt to set
	 */
	public void setPropertyNetBuilt(String propertyNetBuilt) {
		this.propertyNetBuilt = propertyNetBuilt;
	}

	/**
	 * @return the propertyRoadAccess
	 */
	public String getPropertyRoadAccess() {
		return propertyRoadAccess;
	}

	/**
	 * @param propertyRoadAccess
	 *            the propertyRoadAccess to set
	 */
	public void setPropertyRoadAccess(String propertyRoadAccess) {
		this.propertyRoadAccess = propertyRoadAccess;
	}

	/**
	 * @return the propertyServices
	 */
	public String getPropertyServices() {
		return propertyServices;
	}

	/**
	 * @param propertyServices
	 *            the propertyServices to set
	 */
	public void setPropertyServices(String propertyServices) {
		this.propertyServices = propertyServices;
	}

	/**
	 * @return the propertyAreaCondition
	 */
	public String getPropertyAreaCondition() {
		return propertyAreaCondition;
	}

	/**
	 * @param propertyAreaCondition
	 *            the propertyAreaCondition to set
	 */
	public void setPropertyAreaCondition(String propertyAreaCondition) {
		this.propertyAreaCondition = propertyAreaCondition;
	}

	/**
	 * @return the propertyNoiseLevel
	 */
	public String getPropertyNoiseLevel() {
		return propertyNoiseLevel;
	}

	/**
	 * @param propertyNoiseLevel
	 *            the propertyNoiseLevel to set
	 */
	public void setPropertyNoiseLevel(String propertyNoiseLevel) {
		this.propertyNoiseLevel = propertyNoiseLevel;
	}

	/**
	 * @return the propertyPublicTransportation
	 */
	public String getPropertyPublicTransportation() {
		return propertyPublicTransportation;
	}

	/**
	 * @param propertyPublicTransportation
	 *            the propertyPublicTransportation to set
	 */
	public void setPropertyPublicTransportation(
			String propertyPublicTransportation) {
		this.propertyPublicTransportation = propertyPublicTransportation;
	}

	/**
	 * @return the propertyContactNumber
	 */
	public String getPropertyContactNumber() {
		return propertyContactNumber;
	}

	/**
	 * @param propertyContactNumber
	 *            the propertyContactNumber to set
	 */
	public void setPropertyContactNumber(String propertyContactNumber) {
		this.propertyContactNumber = propertyContactNumber;
	}

	/**
	 * @return the propertyLatitude
	 */
	public String getPropertyLatitude() {
		return propertyLatitude;
	}

	/**
	 * @param propertyLatitude
	 *            the propertyLatitude to set
	 */
	public void setPropertyLatitude(String propertyLatitude) {
		this.propertyLatitude = propertyLatitude;
	}

	/**
	 * @return the propertyLongitude
	 */
	public String getPropertyLongitude() {
		return propertyLongitude;
	}

	/**
	 * @param propertyLongitude
	 *            the propertyLongitude to set
	 */
	public void setPropertyLongitude(String propertyLongitude) {
		this.propertyLongitude = propertyLongitude;
	}

}
