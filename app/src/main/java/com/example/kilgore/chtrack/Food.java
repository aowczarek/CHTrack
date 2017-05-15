package com.example.kilgore.chtrack;

public class Food {

    private String name;
    private Float carb;

    public Food(String name, Float carb) {
        this.name = name;
        this.carb = carb;
    }

    public String getName() {
        return name;
    }

    public Float getCarb() {
        return carb;
    }
}