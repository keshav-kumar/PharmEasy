package com.pharmeasy.movies.be.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmeasy.movies.be.dao.DaoService;
import com.pharmeasy.movies.dto.Movie;

@Service
public class MovieService {

	private static Logger logger = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private DaoService daoService;
	
	public List<Movie> getMovies(String cityname) {
		try{
			List<Movie> allMoviesForCity = daoService.getAllMoviesForCity(cityname);
			return allMoviesForCity;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}

}
