package com.example.project_case_mobi6002;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_case_mobi6002.gameplay.BattleWorker;
import com.example.project_case_mobi6002.gameplay.Player;
import com.example.project_case_mobi6002.gameplay.armies.ArcherArmy;
import com.example.project_case_mobi6002.gameplay.armies.Army;
import com.example.project_case_mobi6002.gameplay.armies.CatapultArmy;
import com.example.project_case_mobi6002.gameplay.armies.CavalryArmy;
import com.example.project_case_mobi6002.gameplay.armies.InfantryArmy;
import com.example.project_case_mobi6002.gameplay.castles.Castle;
import com.example.project_case_mobi6002.gameplay.castles.HorseCastle;
import com.example.project_case_mobi6002.gameplay.castles.SteelCastle;
import com.example.project_case_mobi6002.gameplay.castles.StoneCastle;
import com.example.project_case_mobi6002.gameplay.castles.WoodCastle;
import com.example.project_case_mobi6002.gameplay.heroes.ArcherHero;
import com.example.project_case_mobi6002.gameplay.heroes.CatapultHero;
import com.example.project_case_mobi6002.gameplay.heroes.CavalryHero;
import com.example.project_case_mobi6002.gameplay.heroes.Hero;
import com.example.project_case_mobi6002.gameplay.heroes.InfantryHero;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();
    private Player player1, player2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchMaterial switchMaterial = findViewById(R.id.switchMaterial);
        button = findViewById(R.id.button);
        Button buttonPlayer1Castle = findViewById(R.id.buttonPlayer1Castle);
        Button buttonPlayer2Castle = findViewById(R.id.buttonPlayer2Castle);

        init(switchMaterial.isChecked());

        switchMaterial.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            refreshState();
            init(isChecked);
        });

        buttonPlayer1Castle.setOnClickListener(view -> {
            player1.changeCastle();
            player1.renderLeft(this);
        });

        buttonPlayer2Castle.setOnClickListener(view -> {
            player2.changeCastle();
            player2.renderRight(this);
        });

        button.setOnClickListener(view -> {
            button.setEnabled(false);
            BattleWorker battleWorker = new BattleWorker(this, this.player1, this.player2);
            new Thread(battleWorker).start();
        });
    }

    private void refreshState() {
        button.setEnabled(true);
        moveWinnerImageToCenterAndHide();
    }

    private void moveWinnerImageToCenterAndHide() {
        ImageView imageViewWinner = findViewById(R.id.imageViewWinner);
        if (imageViewWinner.getVisibility() == View.VISIBLE) {
            imageViewWinner.setVisibility(View.INVISIBLE);
            Animation animation;
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f);
            animation.setDuration(0);
            imageViewWinner.startAnimation(animation);
        }
    }

    private void init(boolean isCase2) {
        int MAX_HERO = 5;
        int MIN_HERO = 1;
        int MAX_ARMY = 100000;
        int MIN_ARMY = 500;

        TextView textViewCurrentCase = findViewById(R.id.textViewCurrentCase);

        // NIM: 2201789870 - TopazRN
        if (!isCase2) {
            // Case 1: Cavalry vs Archer
            textViewCurrentCase.setText(R.string.case_1);

            // Player 1 init
            Castle player1Castle = new HorseCastle();

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

            this.player1 = new Player("Cavalry", player1Castle, player1Heroes, player1Armies);

            // Player 2 init
            Castle player2Castle = new WoodCastle();

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

            this.player2 = new Player("Archer", player2Castle, player2Heroes, player2Armies);
        } else {
            // Case 2: Mixed Armies vs Infantry
            textViewCurrentCase.setText(R.string.case_2);

            // Player 1 init
            Castle player1Castle;
            Castle.Skin[] skins = Castle.Skin.values();
            Castle.Skin randomSkin = skins[random.nextInt(skins.length)];
            switch (randomSkin) {
                case Steel:
                    player1Castle = new SteelCastle();
                    break;
                case Horse:
                    player1Castle = new HorseCastle();
                    break;
                case Wood:
                    player1Castle = new WoodCastle();
                    break;
                default:
                    player1Castle = new StoneCastle();
                    break;
            }

            Vector<Hero> player1Heroes = new Vector<>();
            int player1HeroesCount = random.nextInt(MAX_HERO - MIN_HERO) + MIN_HERO;
            for (int i = 0; i < player1HeroesCount; i++) {
                Army.Category[] categories = Army.Category.values();
                Army.Category randomCategory = categories[random.nextInt(categories.length)];
                switch (randomCategory) {
                    case Infantry:
                        player1Heroes.add(new InfantryHero(random.nextInt(50) + 1));
                        break;
                    case Cavalry:
                        player1Heroes.add(new CavalryHero(random.nextInt(50) + 1));
                        break;
                    case Archer:
                        player1Heroes.add(new ArcherHero(random.nextInt(50) + 1));
                        break;
                    case Catapult:
                        player1Heroes.add(new CatapultHero(random.nextInt(50) + 1));
                        break;
                }
            }

            Vector<Army> player1Armies = new Vector<>();
            int player1ArmiesCount = random.nextInt(MAX_ARMY - MIN_ARMY) + MIN_ARMY;
            for (int i = 0; i < player1ArmiesCount; i++) {
                Army.Category[] categories = Army.Category.values();
                Army.Category randomCategory = categories[random.nextInt(categories.length)];
                switch (randomCategory) {
                    case Infantry:
                        player1Armies.add(new InfantryArmy());
                        break;
                    case Cavalry:
                        player1Armies.add(new CavalryArmy());
                        break;
                    case Archer:
                        player1Armies.add(new ArcherArmy());
                        break;
                    case Catapult:
                        player1Armies.add(new CatapultArmy());
                        break;
                }
            }

            this.player1 = new Player("Mixed Armies", player1Castle, player1Heroes, player1Armies);

            // Player 2 init
            Castle player2Castle = new SteelCastle();

            Vector<Hero> player2Heroes = new Vector<>();
            int player2HeroesCount = random.nextInt(MAX_HERO - MIN_HERO) + MIN_HERO;
            for (int i = 0; i < player2HeroesCount; i++) {
                player2Heroes.add(new InfantryHero(random.nextInt(50) + 1));
            }

            Vector<Army> player2Armies = new Vector<>();
            int player2ArmiesCount = random.nextInt(MAX_ARMY - MIN_ARMY) + MIN_ARMY;
            for (int i = 0; i < player2ArmiesCount; i++) {
                player2Armies.add(new InfantryArmy());
            }

            this.player2 = new Player("Infantry", player2Castle, player2Heroes, player2Armies);
        }
        this.player1.renderLeft(this);
        this.player2.renderRight(this);
    }
}