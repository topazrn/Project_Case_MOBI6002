package com.example.project_case_mobi6002.gameplay;

import android.app.Activity;
import android.widget.Toast;

import com.example.project_case_mobi6002.gameplay.castles.Castle;

public class BattleWorker implements Runnable {
    private final Player player1, player2;
    private Player winner;
    private final Activity activity;

    public BattleWorker(Activity activity, Player player1, Player player2) {
        this.activity = activity;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        winner = player1.battle(player2);
        Toast.makeText(activity.getApplicationContext(), "The Winner is... " + winner.getName() + "!!", Toast.LENGTH_LONG).show();
    }
}
