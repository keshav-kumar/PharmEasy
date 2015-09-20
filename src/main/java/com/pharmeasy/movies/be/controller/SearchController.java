package com.pharmeasy.movies.be.controller;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pharmeasy.movies.be.service.SearchService;

@Controller
@RequestMapping("/v1/search")
public class SearchController {

 private static Logger      logger = LoggerFactory.getLogger(SearchController.class);
	 
	 @Autowired
	 private SearchService searchService;
	 
	 @RequestMapping(value="/suggest", method={RequestMethod.GET}, produces="application/json")
	 public @ResponseBody JSONArray getMovieByCity(@RequestParam String s){
		 try{
			 JSONArray jsonArray = searchService.getSuggestion(s);
			  return jsonArray;
		 }catch(Exception e){
			 logger.error(e.getMessage(), e);
		 }
		 return null;
	 }
	
}
