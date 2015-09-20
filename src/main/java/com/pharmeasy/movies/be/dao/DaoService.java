package com.pharmeasy.movies.be.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
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
	
}
