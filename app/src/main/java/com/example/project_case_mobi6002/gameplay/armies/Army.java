package com.example.project_case_mobi6002.gameplay.armies;

import java.util.Vector;

public abstract class Army {
    public static final int INFANTRY = 0;
    public static final int CAVALRY = 1;
    public static final int ARCHER = 2;
    public static final int CATAPULT = 3;

    // 1 Infantry will kill 0.1 Cavalry, 0.4 Archer, 0.3 Catapult
    // 1 Cavalry will kill 0.4 Infantry, 0.1 Archer, 0.2 Catapult
    // 1 Archer will kill 0.4 Cavalry, 0.1 Infantry, 0.1 Catapult
    // 1 Catapult will kill 10 Infantry, 3 Cavalry, 2 Archer
    // 1 Category will kill 1 of its own category
    public static final double[] INFANTRY_KILL_COMPATIBILITY = {1, 0.1, 0.4, 0.3};
    public static final double[] CAVALRY_KILL_COMPATIBILITY = {0.4, 1, 0.1, 0.2};
    public static final double[] ARCHER_KILL_COMPATIBILITY = {0.1, 0.4, 1, 0.1};
    public static final double[] CATAPULT_KILL_COMPATIBILITY = {10, 3, 2, 1};

    private int category;

    public Army(int category) {
        this.category = category;
    }

    public int getCategory() {
        return this.category;
    }

    public void dead(Vector<Army> vArmies) {
        vArmies.remove(this);
    }

}
