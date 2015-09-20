package com.pharmeasy.movies.be.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmeasy.movies.be.dao.DaoService;

@Service
public class SearchService {

   private static Logger logger = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private DaoService daoService;
	
	public JSONArray getSuggestion(String s) {
		JSONArray suggestion = new JSONArray();
		
		List<String> titles = daoService.getTitleSuggestion(s);
		JSONObject titleObj =new JSONObject();
		titleObj.put("suggestion_type", "title");
		titleObj.put("suggestion", titles);
		suggestion.add(titleObj);
		
		List<String> locations = daoService.getLocationSuggestion(s);
		JSONObject locationObj =new JSONObject();
		locationObj.put("suggestion_type", "location");
		locationObj.put("suggestion", locations);
		suggestion.add(locationObj);
		
		List<String> actors = daoService.getActorSuggestion(s);
		JSONObject actorObj =new JSONObject();
		actorObj.put("suggestion_type", "actor");
		actorObj.put("suggestion", actors);
		suggestion.add(actorObj);
		
		return suggestion;
	}

}
