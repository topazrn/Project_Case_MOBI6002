package com.example.project_case_mobi6002.gameplay.armies;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Army {
    public enum Category {
        Infantry,
        Cavalry,
        Archer,
        Catapult,
    }

    // 1 Infantry will kill 0.1 Cavalry, 0.4 Archer, 0.3 Catapult
    // 1 Cavalry will kill 0.4 Infantry, 0.1 Archer, 0.2 Catapult
    // 1 Archer will kill 0.4 Cavalry, 0.1 Infantry, 0.1 Catapult
    // 1 Catapult will kill 10 Infantry, 3 Cavalry, 2 Archer
    // 1 Category will kill 1 of its own category
    public static Map<Category, Map<Category, Double>> KILL_COMPATIBILITY;

    static {
        Map<Category, Map<Category, Double>> all = new HashMap<>();
        Map<Category, Double> infantry = new HashMap<>();
        infantry.put(Category.Infantry, 1d);
        infantry.put(Category.Cavalry, 0.1);
        infantry.put(Category.Archer, 0.4);
        infantry.put(Category.Catapult, 0.3);
        Map<Category, Double> cavalry = new HashMap<>();
        cavalry.put(Category.Infantry, 0.4);
        cavalry.put(Category.Cavalry, 1d);
        cavalry.put(Category.Archer, 0.1);
        cavalry.put(Category.Catapult, 0.2);
        Map<Category, Double> archer = new HashMap<>();
        archer.put(Category.Infantry, 0.1);
        archer.put(Category.Cavalry, 0.4);
        archer.put(Category.Archer, 1d);
        archer.put(Category.Catapult, 0.1);
        Map<Category, Double> catapult = new HashMap<>();
        catapult.put(Category.Infantry, 10d);
        catapult.put(Category.Cavalry, 3d);
        catapult.put(Category.Archer, 2d);
        catapult.put(Category.Catapult, 1d);
        all.put(Category.Infantry, infantry);
        all.put(Category.Cavalry, cavalry);
        all.put(Category.Archer, archer);
        all.put(Category.Catapult, catapult);
        KILL_COMPATIBILITY = Collections.unmodifiableMap(all);
    }

    private final Category category;

    public Army(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

}
