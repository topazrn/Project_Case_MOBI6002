package com.example.project_case_mobi6002.gameplay;

import android.app.Activity;
import android.widget.TextView;

import com.example.project_case_mobi6002.R;
import com.example.project_case_mobi6002.gameplay.armies.Army;
import com.example.project_case_mobi6002.gameplay.heroes.Hero;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class Player {
    private final String name;
    private final Vector<Hero> vHero;
    private final Vector<Army> vArmy;

    public Player(String name, Vector<Hero> vHero, Vector<Army> vArmy) {
        this.name = name;
        this.vHero = new Vector<>(vHero);
        this.vArmy = new Vector<>(vArmy);
    }

    public void render1(Activity activity) {
        // Render name
        TextView name = activity.findViewById(R.id.textViewPlayer1Name);
        name.setText(this.name);

        // Render Hero count per category
        Map<Army.Category, Integer> heroCountPerCategory = this.getHeroCountPerCategory();
        if (heroCountPerCategory.containsKey(Army.Category.Infantry)) {
            TextView infantryHeroCount = activity.findViewById(R.id.textViewPlayer1InfantryHeroCount);
            int value = coalesce(heroCountPerCategory.get(Army.Category.Infantry));
            infantryHeroCount.setText(String.valueOf(value));
        }
        if (heroCountPerCategory.containsKey(Army.Category.Cavalry)) {
            TextView cavalryHeroCount = activity.findViewById(R.id.textViewPlayer1CavalryHeroCount);
            int value = coalesce(heroCountPerCategory.get(Army.Category.Cavalry));
            cavalryHeroCount.setText(String.valueOf(value));
        }
        if (heroCountPerCategory.containsKey(Army.Category.Archer)) {
            TextView archerHeroCount = activity.findViewById(R.id.textViewPlayer1ArcherHeroCount);
            int value = coalesce(heroCountPerCategory.get(Army.Category.Archer));
            archerHeroCount.setText(String.valueOf(value));
        }
        if (heroCountPerCategory.containsKey(Army.Category.Catapult)) {
            TextView catapultHeroCount = activity.findViewById(R.id.textViewPlayer1CatapultHeroCount);
            int value = coalesce(heroCountPerCategory.get(Army.Category.Catapult));
            catapultHeroCount.setText(String.valueOf(value));
        }

        // Render Army count per category
        Map<Army.Category, Integer> armyCountPerCategory = this.getArmyCountPerCategory();
        if (armyCountPerCategory.containsKey(Army.Category.Infantry)) {
            TextView infantryArmyCount = activity.findViewById(R.id.textViewPlayer1InfantryArmyCount);
            int value = coalesce(armyCountPerCategory.get(Army.Category.Infantry));
            infantryArmyCount.setText(String.valueOf(value));
        }
        if (armyCountPerCategory.containsKey(Army.Category.Cavalry)) {
            TextView cavalryArmyCount = activity.findViewById(R.id.textViewPlayer1CavalryArmyCount);
            int value = coalesce(armyCountPerCategory.get(Army.Category.Cavalry));
            cavalryArmyCount.setText(String.valueOf(value));
        }
        if (armyCountPerCategory.containsKey(Army.Category.Archer)) {
            TextView archerArmyCount = activity.findViewById(R.id.textViewPlayer1ArcherArmyCount);
            int value = coalesce(armyCountPerCategory.get(Army.Category.Archer));
            archerArmyCount.setText(String.valueOf(value));
        }
        if (armyCountPerCategory.containsKey(Army.Category.Catapult)) {
            TextView catapultArmyCount = activity.findViewById(R.id.textViewPlayer1CatapultArmyCount);
            int value = coalesce(armyCountPerCategory.get(Army.Category.Catapult));
            catapultArmyCount.setText(String.valueOf(value));
        }
    }

    public String getName() {
        return this.name;
    }

    public Player battle(Player player2) {
        // Player 1 attacks Player 2

        // Get Player 1 stats as attacker
        Map<Army.Category, Integer> player1AttackerCount = this.getArmyCountPerCategory();
        Map<Hero.Category, Double> player1Boost = this.getArmyBoostPerCategory();

        // Get Player 2 stats as victim
        Map<Army.Category, Integer> player2Count = player2.getArmyCountPerCategory();
        Map<Army.Category, Integer> player2CountUnmodified = new HashMap<>(player2Count);

        // The attack begins
        _battle(player1AttackerCount, player1Boost, player2Count);

        // Player 2 attacks Player 1

        // Get Player 2 stats as attacker
        Map<Army.Category, Integer> player2AttackerCount = player2.getArmyCountPerCategory();
        Map<Hero.Category, Double> player2Boost = player2.getArmyBoostPerCategory();

        // Get Player 1 stats as victim
        Map<Army.Category, Integer> player1Count = this.getArmyCountPerCategory();
        Map<Army.Category, Integer> player1CountUnmodified = new HashMap<>(player1Count);

        // The attack begins
        _battle(player2AttackerCount, player2Boost, player1Count);

        // Update Player 1 vArmy
        for (Army.Category category : Army.Category.values()) {
            if (player1Count.containsKey(category) && player1CountUnmodified.containsKey(category)) {
                this.buryArmy(category, coalesce(player1CountUnmodified.get(category)) - coalesce(player1Count.get(category)));
            }
        }

        // Update Player 2 vArmy
        for (Army.Category category : Army.Category.values()) {
            if (player2Count.containsKey(category) && player2CountUnmodified.containsKey(category)) {
                this.buryArmy(category, coalesce(player2CountUnmodified.get(category)) - coalesce(player2Count.get(category)));
            }
        }

        return player2;
    }

    private Map<Army.Category, Integer> getArmyCountPerCategory() {
        Map<Army.Category, Integer> armyCountPerCategory = new HashMap<>();
        for (Army army : this.vArmy) {
            final int oldValue = coalesce(armyCountPerCategory.get(army.getCategory()));
            armyCountPerCategory.put(army.getCategory(), oldValue + 1);
        }
        return armyCountPerCategory;
    }

    private Map<Army.Category, Integer> getHeroCountPerCategory() {
        Map<Army.Category, Integer> heroCountPerCategory = new HashMap<>();
        for (Hero hero : this.vHero) {
            final int oldValue = coalesce(heroCountPerCategory.get(hero.getCategory()));
            heroCountPerCategory.put(hero.getCategory(), oldValue + 1);
        }
        return heroCountPerCategory;
    }

    private Map<Army.Category, Double> getArmyBoostPerCategory() {
        Map<Hero.Category, Double> armyBoostPerCategory = new HashMap<>();
        for (Hero hero : this.vHero) {
            final double oldValue = coalesce(armyBoostPerCategory.get(hero.getCategory()));
            final double attackBoost = coalesce(Hero.ATTACK_BOOST.get(hero.getCategory()));
            armyBoostPerCategory.put(hero.getCategory(), oldValue + attackBoost);
        }
        return armyBoostPerCategory;
    }

    private void _battle(Map<Army.Category, Integer> attackerCount, Map<Army.Category, Double> attackerBoost, Map<Army.Category, Integer> victimCount) {
        for (Army.Category victimCategory : Army.Category.values()) {
            if (victimCount.containsKey(victimCategory)) {
                final int currentVictimCategoryCount = coalesce(victimCount.get(victimCategory));
                if (currentVictimCategoryCount > 0) {
                    for (Army.Category attackerCategory : Army.Category.values()) {
                        if (attackerCount.containsKey(attackerCategory)) {
                            final int count = coalesce(attackerCount.get(attackerCategory));
                            if (count > 0) {
                                final double boost = coalesce(attackerBoost.get(attackerCategory));
                                final double compatibility = coalesce(Objects.requireNonNull(Army.KILL_COMPATIBILITY.get(attackerCategory)).get(victimCategory));
                                final double multiplier = (1 + boost) * compatibility;
                                final double currentDamage = count * multiplier;

                                if (currentDamage > currentVictimCategoryCount) {
                                    Integer attackerThatHasNotAttacked = (int) ((currentDamage - currentVictimCategoryCount) / multiplier);
                                    victimCount.put(victimCategory, 0);
                                    attackerCount.put(attackerCategory, attackerThatHasNotAttacked);
                                } else if (currentVictimCategoryCount > currentDamage) {
                                    Integer victimStillAlive = (int) (currentVictimCategoryCount - currentDamage);
                                    victimCount.put(victimCategory, victimStillAlive);
                                    attackerCount.put(attackerCategory, 0);
                                } else {
                                    victimCount.put(victimCategory, 0);
                                    attackerCount.put(attackerCategory, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void buryArmy(Army.Category category, int deathToll) {
        int length = this.vArmy.size();
        for (int i = 0; i < length; i++) {
            if (this.vArmy.get(i).getCategory().equals(category)) {
                this.vArmy.remove(this.vArmy.get(i));
                length--;
            }
            if (deathToll == 0) break;
        }
    }

    private int coalesce(Integer i) {
        return (i == null ? 0 : i);
    }

    private double coalesce(Double d) {
        return (d == null ? 0 : d);
    }
}
