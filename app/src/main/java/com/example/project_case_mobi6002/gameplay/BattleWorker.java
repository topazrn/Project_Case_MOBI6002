package com.example.project_case_mobi6002.gameplay;

import android.app.Activity;
import android.widget.TextView;

import com.example.project_case_mobi6002.R;

public class BattleWorker {
    private final Player player1;
    private Player player2;
    private final Activity activity;

    public BattleWorker(Activity activity, Player player1, Player player2) {
        this.activity = activity;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void run() {
        this.player2 = this.player1.battle(this.player2);
        TextView winner = activity.findViewById(R.id.textViewWinner);
        winner.append(this.player2.getName());
    }
}
