package com.example.project_case_mobi6002.gameplay.heroes;

import com.example.project_case_mobi6002.gameplay.armies.Army;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Hero extends Army {

    public static Map<Category, Double> ATTACK_BOOST;
    static {
        Map<Category, Double> temp = new HashMap<>();
        temp.put(Category.Infantry, 0.4);
        temp.put(Category.Cavalry, 0.4);
        temp.put(Category.Archer, 0.4);
        temp.put(Category.Catapult, 0.4);
        ATTACK_BOOST = Collections.unmodifiableMap(temp);
    }

    private int level;

    public Hero(Category category, int level) {
        super(category);
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}
