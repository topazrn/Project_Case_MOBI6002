package com.example.project_case_mobi6002.gameplay;

import com.example.project_case_mobi6002.gameplay.armies.Army;
import com.example.project_case_mobi6002.gameplay.castles.Castle;
import com.example.project_case_mobi6002.gameplay.heroes.Hero;

import java.util.Vector;

public class Player {
    private final String name;
    private Vector<Hero> vHero = new Vector<>();
    private Vector<Army> vArmy = new Vector<>();

    public Player(String name) {
        this.name = name;
    }

    public Player battle(Player castle2) {
        return this;
    }

    public String getName() {
        return this.name;
    }
}
