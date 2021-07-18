package com.example.project_case_mobi6002.gameplay;

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

    public String getName() {
        return this.name;
    }

    public Player battle(Player player2) {
        // Player 1 attacks Player 2

        // Get Player 1 stats as attacker
        Map<Army.Category, Integer> player1AttackerCount = new HashMap<>();
        for (Army army : this.vArmy) {
            final int oldValue = coalesce(player1AttackerCount.get(army.getCategory()));
            player1AttackerCount.put(army.getCategory(), oldValue + 1);
        }

        Map<Hero.Category, Double> player1Boost = new HashMap<>();
        for (Hero hero : this.vHero) {
            final double oldValue = coalesce(player1Boost.get(hero.getCategory()));
            final double attackBoost = coalesce(Hero.ATTACK_BOOST.get(hero.getCategory()));
            player1Boost.put(hero.getCategory(), oldValue + attackBoost);
        }

        // Get Player 2 stats as victim
        Map<Army.Category, Integer> player2Count = new HashMap<>();
        for (Army army : player2.vArmy) {
            final int oldValue = coalesce(player2Count.get(army.getCategory()));
            player2Count.put(army.getCategory(), oldValue + 1);
        }
        final Map<Army.Category, Integer> player2CountBeforeBattle = new HashMap<>(player2Count);

        // The attack begins
        for (Army.Category victimCategory : Army.Category.values()) {
            if (player2Count.containsKey(victimCategory)){
                final int currentVictimCategoryCount = coalesce(player2Count.get(victimCategory));
                if (currentVictimCategoryCount > 0) {
                    for (Army.Category attackerCategory : Army.Category.values()) {
                        if (player1AttackerCount.containsKey(attackerCategory)) {
                            final int count = coalesce(player1AttackerCount.get(attackerCategory));
                            if (count > 0) {
                                final double boost = coalesce(player1Boost.get(attackerCategory));
                                final double compatibility = coalesce(Objects.requireNonNull(Army.KILL_COMPATIBILITY.get(attackerCategory)).get(victimCategory));
                                final double multiplier = (1 + boost) * compatibility;
                                final double currentDamage = count * multiplier;

                                if (currentDamage > currentVictimCategoryCount) {
                                    Integer attackerThatHasNotAttacked = (int) ((currentDamage - currentVictimCategoryCount) / multiplier);
                                    player2Count.put(victimCategory, 0);
                                    player1AttackerCount.put(attackerCategory, attackerThatHasNotAttacked);
                                } else if (currentVictimCategoryCount > currentDamage) {
                                    Integer victimStillAlive = (int) (currentVictimCategoryCount - currentDamage);
                                    player2Count.put(victimCategory, victimStillAlive);
                                    player1AttackerCount.put(attackerCategory, 0);
                                } else {
                                    player2Count.put(victimCategory, 0);
                                    player1AttackerCount.put(attackerCategory, 0);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Player 2 attacks Player 1

        // Get Player 2 stats as attacker
        Map<Army.Category, Integer> player2AttackerCount = new HashMap<>();
        for (Army army : player2.vArmy) {
            int oldValue = coalesce(player2AttackerCount.get(army.getCategory()));
            player2AttackerCount.put(army.getCategory(), oldValue + 1);
        }

        Map<Hero.Category, Double> player2Boost = new HashMap<>();
        for (Hero hero : player2.vHero) {
            double oldValue = coalesce(player2Boost.get(hero.getCategory()));
            double attackBoost = coalesce(Hero.ATTACK_BOOST.get(hero.getCategory()));
            player2Boost.put(hero.getCategory(), oldValue + attackBoost);
        }

        // Get Player 1 stats as victim
        Map<Army.Category, Integer> player1Count = new HashMap<>();
        for (Army army : this.vArmy) {
            Integer oldValue = player1Count.get(army.getCategory());
            player1Count.put(army.getCategory(), (oldValue == null ? 0 : oldValue) + 1);
        }
        final Map<Army.Category, Integer> player1CountBeforeBattle = new HashMap<>(player1Count);

        // The attack begins
        for (Army.Category victimCategory : Army.Category.values()) {
            if (player1Count.containsKey(victimCategory)){
                int currentVictimCategoryCount = coalesce(player1Count.get(victimCategory));
                if (currentVictimCategoryCount > 0) {
                    for (Army.Category attackerCategory : Army.Category.values()) {
                        if (player2AttackerCount.containsKey(attackerCategory)) {
                            int count = coalesce(player2AttackerCount.get(attackerCategory));
                            if (count > 0) {
                                double boost = coalesce(player2Boost.get(attackerCategory));
                                double compatibility = coalesce(Objects.requireNonNull(Army.KILL_COMPATIBILITY.get(attackerCategory)).get(victimCategory));
                                double multiplier = (1 + boost) * compatibility;
                                double currentDamage = count * multiplier;

                                if (currentDamage > currentVictimCategoryCount) {
                                    Integer attackerThatHasNotAttacked = (int) ((currentDamage - currentVictimCategoryCount) / multiplier);
                                    player1Count.put(victimCategory, 0);
                                    player2AttackerCount.put(attackerCategory, attackerThatHasNotAttacked);
                                } else if (currentVictimCategoryCount > currentDamage) {
                                    Integer victimStillAlive = (int) (currentVictimCategoryCount - currentDamage);
                                    player1Count.put(victimCategory, victimStillAlive);
                                    player2AttackerCount.put(attackerCategory, 0);
                                } else {
                                    player1Count.put(victimCategory, 0);
                                    player2AttackerCount.put(attackerCategory, 0);
                                }
                            }
                        }
                    }
                }
            }
        }

        // check stats
        player1CountBeforeBattle.values();
        player1Count.values();
        player2CountBeforeBattle.values();
        player2Count.values();

        return this;
    }

    private int coalesce(Integer i) {
        return (i == null ? 0 : i);
    }

    private double coalesce(Double d) {
        return (d == null ? 0 : d);
    }
}
