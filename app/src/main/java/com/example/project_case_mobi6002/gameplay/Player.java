package com.example.project_case_mobi6002.gameplay;

import com.example.project_case_mobi6002.gameplay.armies.Army;
import com.example.project_case_mobi6002.gameplay.castles.Castle;
import com.example.project_case_mobi6002.gameplay.heroes.Hero;

import java.util.Vector;

public class Player {
    private final String name;
    private final Vector<Hero> vHero;
    private final Vector<Army> vArmy;

    public Player(String name, Vector<Hero> vHero, Vector<Army> vArmy) {
        this.name = name;
        this.vHero = vHero;
        this.vArmy = vArmy;
    }

    public Player battle(Player castle2) {
        return this;
    }

    public String getName() {
        return this.name;
    }
}
