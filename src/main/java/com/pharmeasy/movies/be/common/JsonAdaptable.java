package com.pharmeasy.movies.be.common;

import java.io.Serializable;

import org.json.simple.JSONObject;

public interface JsonAdaptable extends Serializable {

    public JSONObject toJsonObject();

    public void fromJsonObject(JSONObject jsonObject);

}
