package com.pharmeasy.movies.be.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmeasy.movies.db.MongoDBManager;
import com.pharmeasy.movies.dto.Location;
import com.pharmeasy.movies.dto.Movie;

@Service
public class DaoService {

	private static Logger logger = LoggerFactory.getLogger(DaoService.class);
	
	private static String movieCollectionName = "movieCol";
	private static String locationCollectionName = "locationCol";
	
	@Autowired
	private MongoDBManager mongoDBManager;
	
	public List<Movie> getAllMoviesForCity(String city){
		List<Movie> movies = new ArrayList<>();
		try{
			Map<String, Object> queryParams =new HashMap<>();
			queryParams.put("locations.city", city);
			List<Document> objects = mongoDBManager.getObjects(movieCollectionName, queryParams);
			if(objects!=null){
				for(Document doc:objects){
					Movie movie = new Movie();
					movie.fromJsonObject((JSONObject) JSONValue.parse(doc.toJson()));
					movies.add(movie);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return movies;
	}
	
	public List<Movie> getMoviesForTitle(String title){
		List<Movie> movies = new ArrayList<>();
		try{
			Map<String, Object> queryParams =new HashMap<>();
			queryParams.put("title", Pattern.compile(title, Pattern.CASE_INSENSITIVE));
			List<Document> objects = mongoDBManager.getObjects(movieCollectionName, queryParams);
			if(objects!=null){
				for(Document doc:objects){
					Movie movie = new Movie();
					movie.fromJsonObject((JSONObject) JSONValue.parse(doc.toJson()));
					movies.add(movie);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return movies;
	}
	
	public List<Location> getLocations(String locationName){
		List<Location> locations = new ArrayList<>();
		try{
			Map<String, Object> queryParams =new HashMap<>();
			queryParams.put("locationName", locationName);
			List<Document> objects = mongoDBManager.getObjects(locationCollectionName, queryParams);
			if(objects!=null){
				for(Document doc:objects){
					Location loc = new Location();
					loc.fromJsonObject((JSONObject) JSONValue.parse(doc.toJson()));
					locations.add(loc);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return locations;
	}
	
	public void save(Movie movie){
		try{
			mongoDBManager.addObject(movieCollectionName, movie.toJsonObject().toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	
	public void save(Location loc){
		try{
			mongoDBManager.addObject(locationCollectionName, loc.toJsonObject().toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	public List<String> getTitleSuggestion(String s) {
		List<String> list = new ArrayList<>();
		try{
			Map<String, Object> queryParams =new HashMap<>();
			queryParams.put("title", Pattern.compile("^"+s+".*", Pattern.CASE_INSENSITIVE));
			List<String> fields = new ArrayList<>();
			fields.add("title");
			List<Document> objects = mongoDBManager.getObjects(movieCollectionName, 0, -1, queryParams, fields, null);
			if(objects!=null){
				for(Document doc:objects){
					String title = (String) doc.get("title");
					list.add(title);
				}
			}
			list = new ArrayList<>(new LinkedHashSet<>(list));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public List<String> getLocationSuggestion(String s) {
		List<String> list = new ArrayList<>();
		try{
			Map<String, Object> queryParams =new HashMap<>();
			Pattern pattern = Pattern.compile("^"+s+".*", Pattern.CASE_INSENSITIVE);
			queryParams.put("locations.locationName", pattern);
			List<String> fields = new ArrayList<>();
			fields.add("locations.locationName");
			List<Document> objects = mongoDBManager.getObjects(movieCollectionName, 0, -1, queryParams, fields, null);
			if(objects!=null){
				for(Document doc:objects){
					List<Document> locations = (List<Document>) doc.get("locations");
					for(Document d:locations){
						String name = (String) d.get("locationName");
						Matcher matcher = pattern.matcher(name);
						if(matcher.matches()){
							list.add(name);
						}
					}
				}
			}
			list = new ArrayList<>(new LinkedHashSet<>(list));
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return list;
	}

	public List<String> getActorSuggestion(String s) {
		List<String> list = new ArrayList<>();
		try{
			Map<String, Object> queryParams =new HashMap<>();
			Pattern pattern = Pattern.compile("^"+s+".*", Pattern.CASE_INSENSITIVE);
			queryParams.put("actors", pattern);
			List<String> fields = new ArrayList<>();
			fields.add("actors");
			List<Document> objects = mongoDBManager.getObjects(movieCollectionName, 0, -1, queryParams, fields, null);
			if(objects!=null){
				for(Document doc:objects){
					List<String> actors = (List<String>) doc.get("actors");
					for(String actor:actors){
						Matcher matcher = pattern.matcher(actor);
						if(matcher.matches()){
							list.add(actor);
						}
					}
				}
			}
			list = new ArrayList<>(new LinkedHashSet<>(list));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	public static void main(String[] args) {
		DaoService ds = new DaoService();
		ds.mongoDBManager = new MongoDBManager("localhost", "moviedb");
		List<String> titleSuggestion = ds.getLocationSuggestion("City");
		System.out.println(titleSuggestion);
	}
	
}
