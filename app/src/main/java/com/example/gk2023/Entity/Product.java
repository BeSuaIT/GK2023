package com.example.gk2023.Entity;

public class Product {
    String name, description, img;
    int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Product(String name, String description, String img, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
    }
    public Product(){

    }
}
