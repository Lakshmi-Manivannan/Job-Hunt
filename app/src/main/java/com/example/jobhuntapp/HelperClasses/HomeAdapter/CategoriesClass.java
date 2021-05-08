package com.example.jobhuntapp.HelperClasses.HomeAdapter;


public class CategoriesClass {
    int image;
    String title;
    int background;

    public CategoriesClass(int image, String title, int background) {
        this.image = image;
        this.title = title;
        this.background = background;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public int getBackground() {
        return background;
    }
}
