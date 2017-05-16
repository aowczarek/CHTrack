package com.example.kilgore.chtrack;

public class Food {

    private int id;
    private String name;
    private Float carb;

    public Food(int id, String name, Float carb) {
        this.id = id;
        this.name = name;
        this.carb = carb;
    }

    public int getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public Float getCarb() {

        return carb;
    }
}