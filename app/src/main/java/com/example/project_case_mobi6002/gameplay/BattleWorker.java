package com.example.project_case_mobi6002.gameplay;

import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.project_case_mobi6002.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class BattleWorker implements Runnable {
    private final Player player1;
    private Player player2;
    private final Activity activity;

    public BattleWorker(Activity activity, Player player1, Player player2) {
        this.activity = activity;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        activity.runOnUiThread(() -> {
            SwitchMaterial switchMaterial = activity.findViewById(R.id.switchKasus);
            switchMaterial.setEnabled(false);

            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            Toast cliffhanger = new Toast(activity.getApplicationContext());
            cliffhanger.setText("The winner is...");
            cliffhanger.setDuration(Toast.LENGTH_SHORT);
            cliffhanger.show();

            fadeInWinnerImage();
        });
        this.player2 = this.player1.battle(this.player2);
        showWinner();
    }

    private void showWinner() {
        activity.runOnUiThread(() -> {
            SwitchMaterial switchMaterial = activity.findViewById(R.id.switchKasus);
            switchMaterial.setEnabled(true);

            ProgressBar progressBar = activity.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

            int armyPlayer1Count = player1.getArmyCount();
            int armyPlayer2Count = player2.getArmyCount();

            if (armyPlayer1Count > armyPlayer2Count) {
                // Player 1 wins
                Toast.makeText(activity.getApplicationContext(), "Player " + player1.getName(), Toast.LENGTH_SHORT).show();
                moveWinnerImageToTheWinner(1);
            } else if (armyPlayer2Count > armyPlayer1Count) {
                // Player 2 wins
                Toast.makeText(activity.getApplicationContext(), "Player " + player2.getName(), Toast.LENGTH_SHORT).show();
                moveWinnerImageToTheWinner(2);
            } else {
                // Draw
                Toast.makeText(activity.getApplicationContext(), "It's a draw!!", Toast.LENGTH_SHORT).show();
                fadeOutWinnerImage();
            }
        });
    }

    private void fadeInWinnerImage() {
        activity.runOnUiThread(() -> {
            ImageView imageViewWinner = activity.findViewById(R.id.imageViewWinner);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(1500);
            alphaAnimation.setRepeatCount(0);
            imageViewWinner.startAnimation(alphaAnimation);
            imageViewWinner.setVisibility(View.VISIBLE);
        });
    }

    private void fadeOutWinnerImage() {
        activity.runOnUiThread(() -> {
            ImageView imageViewWinner = activity.findViewById(R.id.imageViewWinner);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(1500);
            alphaAnimation.setRepeatCount(0);
            imageViewWinner.startAnimation(alphaAnimation);
            imageViewWinner.setVisibility(View.INVISIBLE);
        });
    }

    private void moveWinnerImageToTheWinner(int winner) {
        activity.runOnUiThread(() -> {
            ImageView imageViewWinner = activity.findViewById(R.id.imageViewWinner);
            Animation animation;
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, (winner == 1 ? -0.3f : 0.3f),
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f);
            animation.setDuration(1000);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.setFillAfter(true);
            imageViewWinner.startAnimation(animation);
        });
    }
}
