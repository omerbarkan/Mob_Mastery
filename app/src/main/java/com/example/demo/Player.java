package com.example.demo;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Player {
    protected String username;
    protected String email;

    protected int gold;

    protected int gems;

    protected int maxLevel;

    protected int currentLevel;

    protected ArrayList<Equipment> equipment;

    protected ArrayList<Bonus> bonuses;

    protected ArrayList<Skill> skills;

    protected ArrayList<Skill> equippedSkills;



    public Player(String username, String email) {
        this.username = username;
        this.email = email;
        this.gold = 50;
        this.gems = 500;
        this.maxLevel = 1;
        this.currentLevel = 1;
        this.skills = ArraysMethods.setSkills();
        this.equipment = ArraysMethods.setEquipment();
        this.bonuses = ArraysMethods.setBonuses();
        Skill skill = new Skill("NONE", 0,"NONE", 0);
        this.equippedSkills = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            this.equippedSkills.add(skill);
        }
    }

    public Player() {
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getGold() {
        return gold;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getGems() {
        return gems;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Bonus> getBonuses() {
        return bonuses;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public ArrayList<Skill> getEquippedSkills() {
        return equippedSkills;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

    public void setBonuses(ArrayList<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public void setEquippedSkills(ArrayList<Skill> equippedSkills) {
        this.equippedSkills = equippedSkills;
    }
}
