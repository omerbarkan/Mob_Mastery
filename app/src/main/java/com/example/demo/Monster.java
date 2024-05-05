package com.example.demo;

public class Monster {
    private String type;
    private int maxHealth;
    private int currentHealth;


    public Monster(String type, int maxHealth, int currentHealth) {
        this.type = type;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public static int setMonsterHealth(int currentLevel)
    {
        double currentLevelD = currentLevel;
        return (int) ( 50 * Math.pow(currentLevelD*5, 1.25*(Math.sqrt(currentLevelD)/10)) ) ;
    }
}
