package com.example.project_case_mobi6002.gameplay.castles;

public abstract class Castle {
    public static final int STEEL = 0;
    public static final int HORSE = 1;
    public static final int WOOD = 2;
    public static final int STONE = 3;

    public static final double[] SKILL_BOOST = {0.2, 0.2, 0.2, 0.2};

    private int skin;

    public Castle(int skin) {
        this.skin = skin;
    }

    public int getSkin() {
        return this.skin;
    }
}
