package com.example.demo;

import android.os.CountDownTimer;

public class GameManager {
    private Monster monster;
    private double AttackSpeed;


    public GameManager(Monster monster, double attackSpeed) {
        this.monster = monster;
        AttackSpeed = attackSpeed;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public double getAttackSpeed() {
        return AttackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        AttackSpeed = attackSpeed;
    }

}
