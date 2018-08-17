package com.example.akshay.dustaway;

/**
 * Created by rahul on 17/8/18.
 */

public class cleaner {
    private String name;
    private int points;

    public cleaner()
    {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = this.points + points;
    }
}
