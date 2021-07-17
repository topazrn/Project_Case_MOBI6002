package com.example.project_case_mobi6002.gameplay.castles;

import com.example.project_case_mobi6002.gameplay.armies.Army;
import com.example.project_case_mobi6002.gameplay.heroes.Hero;

import java.util.Vector;

public abstract class Castle {
    public static final int STEEL = 0;
    public static final int HORSE = 1;
    public static final int WOOD = 2;
    public static final int STONE = 3;

    public static final double[] SKILL_BOOST = {0.2, 0.2, 0.2, 0.2};

    private int skin;
    private Vector<Hero> vHero = new Vector<>();
    private Vector<Army> vArmy = new Vector<>();

    public Castle(int skin) {
        this.skin = skin;
    }

    public int getSkin() {
        return this.skin;
    }
}
