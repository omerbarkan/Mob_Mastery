package com.example.demo;

import android.graphics.Bitmap;

public class Equipment {

    private String name;
    private int level;
    private int perLevelStrength;
    private int baseCost;

    public Equipment() {
    }

    public Equipment(String name, int level, int perLevelStrength, int baseCost) {
        this.name = name;
        this.level = level;
        this.perLevelStrength = perLevelStrength;
        this.baseCost = baseCost;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void setPerLevelStrength(int perLevelStrength) {
        this.perLevelStrength = perLevelStrength;
    }

    public void setBaseCost(int baseCost) {
        this.baseCost = baseCost;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }


    public int getPerLevelStrength() {
        return perLevelStrength;
    }

    public int getBaseCost() {
        return baseCost;
    }

}


