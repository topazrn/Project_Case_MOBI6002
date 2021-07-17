package com.example.project_case_mobi6002.gameplay.castles;

import com.example.project_case_mobi6002.gameplay.armies.Army;
import com.example.project_case_mobi6002.gameplay.heroes.Hero;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public abstract class Castle {
    public enum Skin {
        Steel,
        Horse,
        Wood,
        Stone,
    }

    public static Map<Skin, Double> SKILL_BOOST;
    static {
        Map<Skin, Double> temp = new HashMap<>();
        temp.put(Skin.Steel, 0.2);
        temp.put(Skin.Horse, 0.2);
        temp.put(Skin.Wood, 0.2);
        temp.put(Skin.Stone, 0.2);
        SKILL_BOOST = Collections.unmodifiableMap(temp);
    }

    private Skin skin;
    private Vector<Hero> vHero = new Vector<>();
    private Vector<Army> vArmy = new Vector<>();

    public Castle(Skin skin) {
        this.skin = skin;
    }

    public Skin getSkin() {
        return this.skin;
    }
}
