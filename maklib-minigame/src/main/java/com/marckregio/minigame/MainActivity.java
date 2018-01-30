package com.marckregio.minigame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.button_play) ImageButton buttonPlay;
    @BindView(R.id.button_score) ImageButton score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ButterKnife.bind(this);
        buttonPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonPlay){
            Intent game = new Intent(this, GameActivity.class);
            startActivity(game);
        }
    }
}
