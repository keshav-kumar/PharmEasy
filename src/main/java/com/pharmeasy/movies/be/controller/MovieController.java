package com.pharmeasy.movies.be.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pharmeasy.movies.be.service.MovieService;
import com.pharmeasy.movies.dto.Movie;
import com.pharmeasy.movies.utils.JSONUtils;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {

	 private static Logger      logger = LoggerFactory.getLogger(MovieController.class);
	 
	 @Autowired
	 private MovieService movieService;
	 
	 @RequestMapping(value="/city", method={RequestMethod.GET}, produces="application/json")
	 public @ResponseBody JSONArray getMovieByCity(@RequestParam String name){
		 try{
			  List<Movie> movies = movieService.getMovies(name);
			  JSONArray jsonArray = JSONUtils.convertListToJSON(movies);
			  return jsonArray;
		 }catch(Exception e){
			 logger.error(e.getMessage(), e);
		 }
		 return null;
	 }
	 
	 @RequestMapping(value="/title", method={RequestMethod.GET}, produces="application/json")
	 public @ResponseBody JSONArray getMovieByTitle(@RequestParam String name){
		 try{
			  List<Movie> movies = movieService.getMoviesForTitle(name);
			  JSONArray jsonArray = JSONUtils.convertListToJSON(movies);
			  return jsonArray;
		 }catch(Exception e){
			 logger.error(e.getMessage(), e);
		 }
		 return null;
	 }
	
}
