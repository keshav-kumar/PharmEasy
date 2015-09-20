package com.pharmeasy.movies.dto;

import org.json.simple.JSONObject;

import com.pharmeasy.movies.be.common.JsonAdaptable;

public abstract class BaseObject implements JsonAdaptable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2577691182804144126L;

    private String            id;
    private boolean           deleted;
    private String            createUser;
    private String            lastModifieduser;
    private long              creationTime;
    private long              lastUpdated;

    @Override
    public JSONObject toJsonObject() {
        JSONObject object = new JSONObject();
        object.put("_id", id);
        object.put("deleted", deleted);
        object.put("createUser", createUser);
        object.put("lastUser", lastModifieduser);
        object.put("creationTime", creationTime);
        object.put("lastUpdated", lastUpdated);
        toJsonInternal(object);
        return object;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {
    	Object idobj = jsonObject.get("_id");
        if(idobj instanceof String) {
            setId((String) idobj);
        }
        else if(idobj instanceof JSONObject) {
            setId((String) ((JSONObject) jsonObject.get("_id")).get("$oid"));
        }
        if (jsonObject.get("deleted") instanceof Boolean) {
            Boolean b = (Boolean) jsonObject.get("deleted");
            setDeleted(b);
        }
        setCreateUser((String) jsonObject.get("createUser"));
        setLastModifieduser((String) jsonObject.get("lastUser"));
        if (jsonObject.get("creationTime") instanceof Number) {
            Number number = (Number) jsonObject.get("creationTime");
            setCreationTime(number.longValue());
        }
        if (jsonObject.get("lastUpdated") instanceof Number) {
            Number number = (Number) jsonObject.get("lastUpdated");
            setLastUpdated(number.longValue());
        }
        fromJsonInternal(jsonObject);
    }

    public abstract void toJsonInternal(JSONObject jsonObject);

    public abstract void fromJsonInternal(JSONObject jsonObject);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastModifieduser() {
        return lastModifieduser;
    }

    public void setLastModifieduser(String lastModifieduser) {
        this.lastModifieduser = lastModifieduser;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
