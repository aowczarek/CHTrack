package com.example.kilgore.chtrack;

public class Meal {

    private String name;
    private Food food;
    private Float quantity;

    public Meal(String name, Food food, Float quantity) {
        this.name = name;
        this.food = food;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public Food getFood() {
        return food;
    }

    public Float getQuantity(){
        return quantity;
    }
}
