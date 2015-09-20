package com.pharmeasy.movies.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pharmeasy.movies.be.common.JsonAdaptable;

public class JSONUtils {

	public static JSONArray convertListToJSON(List list){
		JSONArray arr = new JSONArray();
		if(list==null){
			return arr;
		}
		for(Object o:list){
			if( o instanceof JsonAdaptable){
				JsonAdaptable jsonAdaptable = (JsonAdaptable) o;
				arr.add(jsonAdaptable.toJsonObject());
			}else{
				arr.add(o.toString());
			}
		}
		return arr;
	}
	
	public static List convertFromJson(JSONArray jsonArr,Class klass){
		List list =new ArrayList();
		if(jsonArr==null||jsonArr.size()==0){
			return list;
		}
		if(JsonAdaptable.class.isAssignableFrom(klass)){
			for(int i=0;i<jsonArr.size();i++){
				try {
					JSONObject jsonObject = (JSONObject) jsonArr.get(i);
					JsonAdaptable ja = (JsonAdaptable) klass.newInstance();
					ja.fromJsonObject(jsonObject);
					list.add(ja);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if(klass==String.class){
			list.addAll(jsonArr);
		}
		return list;
	}
	
	
}
