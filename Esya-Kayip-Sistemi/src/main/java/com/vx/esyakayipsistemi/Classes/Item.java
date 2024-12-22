package com.vx.esyakayipsistemi.Classes;

public class Item {
    private String name;
    private String category;
    private String description;
    private String date;
    private String imagePath;

    //constructor
    public Item(String name, String category,String description, String date, String imagePath) {
        this.name = name;
        this.category = category;
        this.description=description;
        this.date = date;
        this.imagePath = imagePath;
    }

    //setter ve getter
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getDescription(){return description;}
}
