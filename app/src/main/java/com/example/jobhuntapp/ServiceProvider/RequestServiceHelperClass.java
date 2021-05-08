package com.example.jobhuntapp.ServiceProvider;

import java.sql.Timestamp;
import java.util.Map;

public class RequestServiceHelperClass {
    String uname,location,category,image;
    String timestamp;

    public RequestServiceHelperClass(String uname, String location, String category, String image, String timestamp) {
        this.uname = uname;
        this.location = location;
        this.category = category;
        this.image = image;
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
