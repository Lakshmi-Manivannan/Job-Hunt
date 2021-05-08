package com.example.jobhuntapp.HelperClasses.HomeAdapter;

public class MostRequestedClass {
    String image;
    String full_name;
    String category;
    String location,description;


    public String getImage() {
        return image;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public MostRequestedClass(String image, String full_name, String category, String location, String description) {
        this.image = image;
        this.full_name = full_name;
        this.category = category;
        this.location = location;
        this.description = description;
    }
}
