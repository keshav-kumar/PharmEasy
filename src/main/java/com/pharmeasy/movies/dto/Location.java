package com.pharmeasy.movies.dto;

import org.json.simple.JSONObject;

public class Location extends BaseObject{

	
	private static final long serialVersionUID = 4498540362490098082L;
	
	private double lattitude;
	private double longitude;
	private String locationName;
	private String street;
	private String city;
	private String state;
	private String country;
	
	@Override
	public void toJsonInternal(JSONObject jsonObject) {
		jsonObject.put("lattitude", lattitude);
		jsonObject.put("longitude", longitude);
		jsonObject.put("locationName", locationName);
		jsonObject.put("street", street);
		jsonObject.put("city", city);
		jsonObject.put("state", state);
		jsonObject.put("country", country);
	}
	@Override
	public void fromJsonInternal(JSONObject jsonObject) {
		if(jsonObject.get("lattitude") instanceof Number){
			setLattitude(((Number)jsonObject.get("lattitude")).doubleValue());
		}
		if(jsonObject.get("longitude") instanceof Number){
			setLongitude(((Number)jsonObject.get("longitude")).doubleValue());
		}
		setLocationName((String) jsonObject.get("locationName"));
		setStreet((String) jsonObject.get("street"));
		setCity((String) jsonObject.get("city"));
		setState((String) jsonObject.get("state"));
		setCountry((String) jsonObject.get("country"));
	}
	
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	

}
