package com.pharmeasy.movies.dto;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pharmeasy.movies.utils.JSONUtils;

public class Movie extends BaseObject{
	
	private static final long serialVersionUID = -27678442683675601L;

	private String title;
	private int realeseYear;
	private String productionComapny;
	private List<Location> locations;
	private List<String> directors;
	private List<String> writers;
	private List<String> actors;

	@Override
	public void toJsonInternal(JSONObject jsonObject) {
		jsonObject.put("title", title);
		jsonObject.put("realeseYear", realeseYear);
		jsonObject.put("productionComapny", productionComapny);
		jsonObject.put("locations", JSONUtils.convertListToJSON(locations));
		jsonObject.put("directors", JSONUtils.convertListToJSON(directors));
		jsonObject.put("writers", JSONUtils.convertListToJSON(writers));
		jsonObject.put("actors", JSONUtils.convertListToJSON(actors));
	}

	@Override
	public void fromJsonInternal(JSONObject jsonObject) {
		setTitle((String) jsonObject.get("title"));
		if(jsonObject.get("realeseYear") instanceof Number){
			setRealeseYear(((Number)jsonObject.get("realeseYear")).intValue());
		}
		setProductionComapny((String) jsonObject.get("productionComapny"));
		setLocations(JSONUtils.convertFromJson((JSONArray) jsonObject.get("locations"), Location.class));
		setDirectors(JSONUtils.convertFromJson((JSONArray) jsonObject.get("directors"), String.class));
		setWriters(JSONUtils.convertFromJson((JSONArray) jsonObject.get("writers"), String.class));
		setActors(JSONUtils.convertFromJson((JSONArray) jsonObject.get("actors"), String.class));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRealeseYear() {
		return realeseYear;
	}

	public void setRealeseYear(int realeseYear) {
		this.realeseYear = realeseYear;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<String> getDirectors() {
		return directors;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	public List<String> getWriters() {
		return writers;
	}

	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public String getProductionComapny() {
		return productionComapny;
	}

	public void setProductionComapny(String productionComapny) {
		this.productionComapny = productionComapny;
	}

}
