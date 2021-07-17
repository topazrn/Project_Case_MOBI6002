package com.example.project_case_mobi6002.gameplay.heroes;

public class Hero {
    public static final int INFANTRY = 0;
    public static final int CAVALRY = 1;
    public static final int ARCHER = 2;
    public static final int CATAPULT = 3;

    public static final double[] ATTACK_BOOST = {0.4, 0.4, 0.4, 0.4};

    private int category;
    private int level;

    public Hero(int category, int level) {
        this.category = category;
        this.level = level;
    }

    public int getCategory() {
        return category;
    }

    public int getLevel() {
        return level;
    }
}
