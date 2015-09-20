package com.pharmeasy.movies.db.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pharmeasy.movies.db.MongoDBManager;
import com.pharmeasy.movies.dto.Location;
import com.pharmeasy.movies.dto.Movie;

public class DBPopulatorScript {

	private static String dbhost = "localhost";
	private static String dbname = "moviedb";

	public static void main(String[] args) {
		loadLocation();
		loadMovies();
	}

	private static void loadMovies() {
		Map<String, Movie> movieMap = new HashMap<>();
		MongoDBManager mdm = new MongoDBManager(dbhost, dbname);
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document document = db.parse(new File("data/movies.xml"));
			NodeList nodeList = document.getElementsByTagName("row");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element elem = (Element) nodeList.item(i);
				if (elem.getAttribute("_id") == null) {
					continue;
				}
				String title = getValue(elem, "title");
				String releasYear = getValue(elem, "release_year");
				String locations = getValue(elem, "locations");
				String production_company = getValue(elem, "production_company");
				String director = getValue(elem, "director");
				String writer = getValue(elem, "writer");
				String actor_1 = getValue(elem, "actor_1");
				String actor_2 = getValue(elem, "actor_2");
				String actor_3 = getValue(elem, "actor_3");
				Movie m = new Movie();
				m.setTitle(title);
				m.setRealeseYear(Integer.parseInt(releasYear));
				List<Location> locs = new ArrayList<>();
				Location loc = getLocation(locations, mdm);
				locs.add(loc);
				m.setLocations(locs);
				m.setProductionComapny(production_company);
				List<String> directors = tokenize(director);
				m.setDirectors(directors);
				m.setWriters(tokenize(writer));
				List<String> actors = new ArrayList<>();
				actors.add(actor_1);
				if (actor_2 != null && !actor_2.trim().equals("")) {
					actors.add(actor_2);
				}
				if (actor_3 != null && !actor_3.trim().equals("")) {
					actors.add(actor_3);
				}
				m.setActors(actors);
				String key = m.getTitle() + "_" + m.getRealeseYear();
				Movie movie = movieMap.get(key);
				if(movie==null){
					movieMap.put(key, m);
				}else{
					movie.getLocations().addAll(m.getLocations());
				}
				System.out.println(m.toJsonObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(movieMap.size());
		for(Movie m:movieMap.values()){
			try{
				JSONObject jsonObject = m.toJsonObject();
				System.out.println("m.toJsonObject() = "+jsonObject);
				mdm.addObject("movieCol", jsonObject.toString());
				System.out.println("added "+m.toJsonObject());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private static List<String> tokenize(String str) {
		 List<String> list = new ArrayList<>();
		 if(str==null||str.trim().equals("")){
			 return list;
		 }
		 str = str.trim();
		String[] split = str.split(",");
		for(String s:split){
			s  =s.replace('&', ' ');
			list.add(s.trim());
		}
		return list;
	}

	private static Location getLocation(String locations, MongoDBManager mdm) {
		Location loc = new Location();
		loc.setLocationName(locations);
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("locationName", locations);
		org.bson.Document object = mdm.getObject("locationCol", queryParams );
		if(object!=null){
			loc.fromJsonObject((JSONObject) JSONValue.parse(object.toJson()));
		}
		System.out.println("got loc "+loc.toJsonObject());
		return loc;
	}

	public static void loadLocation() {
		try {
			MongoDBManager mdm = new MongoDBManager(dbhost, dbname);
			BufferedReader br = new BufferedReader(new FileReader(
					"data/Movie-Sites-SF.csv"));
			String line = null;
			br.readLine();
			Map<String, Location> locationMap = new HashMap<>();
			while ((line = br.readLine()) != null) {

				List<String> list = readCSVLine(line);
				String locationName = list.get(1);
				String state = list.get(2);
				String city = list.get(3);
				String latitude = list.get(4);
				String longitude = list.get(5);
				String street = list.get(9);
				String country = "USA";
				Location loc = new Location();
				loc.setLocationName(locationName);
				loc.setState(state);
				loc.setCity(city);
				loc.setLattitude(Double.parseDouble(latitude.trim()));
				loc.setLongitude(Double.parseDouble(longitude.trim()));
				loc.setStreet(street);
				loc.setCountry(country);
				System.out.println(loc.toJsonObject());
				locationMap.put(locationName, loc);
			}
			for (Location loc : locationMap.values()) {
				mdm.addObject("locationCol", loc.toJsonObject().toString());
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> readCSVLine(String line) {
		List<String> words = new ArrayList<String>();
		String currentToken = "";
		boolean quotesOpen = false;
		for (char c : line.toCharArray()) {
			switch (c) {
			case ',':
				if (!quotesOpen) {
					words.add(currentToken);
					currentToken = "";
				} else {
					currentToken += c;
				}
				break;
			case '"':
				quotesOpen = !quotesOpen;
				break;
			default:
				currentToken += c;
				break;
			}
		}
		words.add(currentToken);
		return words;
	}

	public static String getValue(Element parentElem, String nodeName) {
		NodeList nodeList = parentElem.getElementsByTagName(nodeName);
		if (nodeList != null && nodeList.getLength() > 0) {
			Node node = nodeList.item(0);
			return node.getTextContent();
		}
		return null;
	}

}
