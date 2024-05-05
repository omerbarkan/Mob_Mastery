package com.example.demo;

import android.graphics.Bitmap;

public class Bonus {

    private String name;

    private int level;

    private int baseCost;

    private int perLevelStrength;

    public Bonus(String name, int level,  int baseCost, int perLevelStrength) {
        this.name = name;
        this.level = level;
        this.baseCost = baseCost;
        this.perLevelStrength = perLevelStrength;
    }

    public Bonus() {
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void setBaseCost(int baseCost) {
        this.baseCost = baseCost;
    }

    public void setPerLevelStrength(int perLevelStrength) {
        this.perLevelStrength = perLevelStrength;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }


    public int getBaseCost() {
        return baseCost;
    }

    public int getPerLevelStrength() {
        return perLevelStrength;
    }
}
