package org.proven.game2d24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameView gameView = (GameView) findViewById(R.id.gameview);
        ThreadGame thread = new ThreadGame(gameView);
        thread.start();
    }
}