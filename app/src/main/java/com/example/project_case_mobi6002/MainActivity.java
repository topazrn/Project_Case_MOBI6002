package com.example.project_case_mobi6002;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.project_case_mobi6002.gameplay.BattleWorker;
import com.example.project_case_mobi6002.gameplay.Player;
import com.example.project_case_mobi6002.gameplay.armies.ArcherArmy;
import com.example.project_case_mobi6002.gameplay.armies.Army;
import com.example.project_case_mobi6002.gameplay.armies.CavalryArmy;
import com.example.project_case_mobi6002.gameplay.heroes.ArcherHero;
import com.example.project_case_mobi6002.gameplay.heroes.CavalryHero;
import com.example.project_case_mobi6002.gameplay.heroes.Hero;

import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Activity activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int MAX_HERO = 5;
        int MIN_HERO = 1;
        int MAX_ARMY = 100000;
        int MIN_ARMY = 5000;

        // #region init

        // NIM: 2201789870 - TopazRN
        // Case 1: Cavalry vs Archer

        // Player 1 init
        Vector<Hero> player1Heroes = new Vector<>();
        int player1HeroesCount = random.nextInt(MAX_HERO - MIN_HERO) + MIN_HERO;
        for (int i = 0; i < player1HeroesCount; i++) {
            player1Heroes.add(new CavalryHero(random.nextInt(50) + 1));
        }

        Vector<Army> player1Armies = new Vector<>();
        int player1ArmiesCount = random.nextInt(MAX_ARMY - MIN_ARMY) + MIN_ARMY;
        for (int i = 0; i < player1ArmiesCount; i++) {
            player1Armies.add(new CavalryArmy());
        }

        Player player1 = new Player("Radiant", player1Heroes, player1Armies);
        player1.renderLeft(activity);

        // Player 2 init
        Vector<Hero> player2Heroes = new Vector<>();
        int player2HeroesCount = random.nextInt(MAX_HERO - MIN_HERO) + MIN_HERO;
        for (int i = 0; i < player2HeroesCount; i++) {
            player2Heroes.add(new ArcherHero(random.nextInt(50) + 1));
        }

        Vector<Army> player2Armies = new Vector<>();
        int player2ArmiesCount = random.nextInt(MAX_ARMY - MIN_ARMY) + MIN_ARMY;
        for (int i = 0; i < player2ArmiesCount; i++) {
            player2Armies.add(new ArcherArmy());
        }

        Player player2 = new Player("Dire", player2Heroes, player2Armies);
        player2.renderRight(activity);

        // #endregion

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            button.setEnabled(false);
            BattleWorker battleWorker = new BattleWorker(activity, player1, player2);
            battleWorker.run();
        });

    }
}