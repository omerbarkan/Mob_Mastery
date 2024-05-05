package com.example.demo;

import android.graphics.BitmapFactory;
import android.content.Context;


import java.util.ArrayList;

public class ArraysMethods {

    public static ArrayList<Equipment> setEquipment()
        {

            ArrayList<Equipment> equipmentList = new ArrayList<>();

            Equipment eq = new Equipment("Sword", 1, 1, 10);


            equipmentList.add(eq);



            return equipmentList;
        }

    public static ArrayList<Bonus> setBonuses()
    {

        ArrayList<Bonus> bonusList = new ArrayList<>();

        Bonus eq = new Bonus("Gold Boost", 1,10, 10);


        bonusList.add(eq);



        return bonusList;
    }

    public static ArrayList<Skill> setSkills()
    {
        ArrayList<Skill> skillsList = new ArrayList<>();

        Skill fireball = new Skill("Fireball", 5, "Does damage to enemy (10x than normal)", 0);
        Skill fastAttack = new Skill("FastAttack", 30, "Makes you attack x2 faster for 5 seconds", 5);

        skillsList.add(fireball);
        skillsList.add(fastAttack);

        return skillsList;
    }


}
