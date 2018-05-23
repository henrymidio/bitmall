package com.mdio.br.altshop.models;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String description;
    private double price;
    private int photo;

    public Product(double price, String name, String description, int photo) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPhoto() {
        return photo;
    }

    public double getPrice() {
        return price;
    }
}
