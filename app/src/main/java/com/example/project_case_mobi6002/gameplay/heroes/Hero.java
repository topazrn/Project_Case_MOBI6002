package com.example.project_case_mobi6002.gameplay.heroes;

import com.example.project_case_mobi6002.gameplay.armies.Army;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Hero {
    public enum Category {
        Infantry,
        Cavalry,
        Archer,
        Catapult,
    }

    public static Map<Category, Double> ATTACK_BOOST;
    static {
        Map<Category, Double> temp = new HashMap<>();
        temp.put(Category.Infantry, 0.4);
        temp.put(Category.Cavalry, 0.4);
        temp.put(Category.Archer, 0.4);
        temp.put(Category.Catapult, 0.4);
        ATTACK_BOOST = Collections.unmodifiableMap(temp);
    }

    private Category category;
    private int level;

    public Hero(Category category, int level) {
        this.category = category;
        this.level = level;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getLevel() {
        return this.level;
    }
}
