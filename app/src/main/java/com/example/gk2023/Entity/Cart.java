package com.example.gk2023.Entity;

public class Cart {

    String name, img;
    int price, amount;

    public Cart(String name, String img, int price, int amount) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.amount = amount;
    }

    public Cart(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
