package com.example.lifecounter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private int valueDepart = 20;
    private int i1 = valueDepart;
    private int i2 = valueDepart;
    private Random rng = new Random();
    private TextView textWinP1;
    private TextView textWinP2;
    private TextView lifePointP1;
    private TextView lifePointP2;
    public ImageView terrainP1;
    public ImageView terrainP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Components du Player 1
        FloatingActionButton addLifeP1 = (FloatingActionButton) findViewById(R.id.btnAddLifePlayer1);
        FloatingActionButton minusLifeP1 = (FloatingActionButton) findViewById(R.id.btnMinusLifePlayer1);
        lifePointP1 = findViewById(R.id.lifePointPlayer1);
        ImageView manaP1 = (ImageView) findViewById(R.id.manaPlayer1);
        terrainP1 = findViewById(R.id.terrainPlayer1);
        textWinP1 = findViewById(R.id.winTextP1);

        // Components du Player 2
        FloatingActionButton addLifeP2 = (FloatingActionButton) findViewById(R.id.btnAddLifePlayer2);
        FloatingActionButton minusLifeP2 = (FloatingActionButton) findViewById(R.id.btnMinusLifePlayer2);
        lifePointP2 = findViewById(R.id.lifePointPlayer2);
        ImageView manaP2 = (ImageView) findViewById(R.id.manaPlayer2);
        terrainP2 = findViewById(R.id.terrainPlayer2);
        textWinP2 = findViewById(R.id.winTextP2);

        // Components de la NavBar
        ImageView newGame = (ImageView) findViewById(R.id.newGameBtn);
        ImageView setLifePoints = (ImageView) findViewById(R.id.setLifePointBtn);
        ImageView battle = (ImageView) findViewById(R.id.battleBtn);

// --------------------------------- NavBar --------------------------------------------------------

        // New Game - Remettre les points de vie des 2 joueurs aux points de vie de départ
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1=valueDepart;
                i2=valueDepart;
                String n = Integer.toString(valueDepart);
                lifePointP1.setText(n);
                lifePointP2.setText(n);
            }

            private void setContentView(int activity_main){
            }});


        // Set Life Points - Définir le nombre de points de vie de départ des 2 joueurs
        setLifePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { chooseLifePoints(); }

            private void setContentView(int activity_main){
            }});


        // Fight - Définir le joueur qui commence la partie
        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { fight(); }
        });


// ---------------------------------- Player 1 -----------------------------------------------------

        // Player 1 - Définir action -1 au click du bouton "btnMinusLifePlayer1"
        minusLifeP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1--;
                String j1 = Integer.toString(i1);
                lifePointP1.setText(j1);
            }

            private void setContentView(int activity_main) {
            }});

        // Player 1 - Définir action +1 au click du bouton "btnAddLifePlayer1"
        addLifeP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1++;
                String j1 = Integer.toString(i1);
                lifePointP1.setText(j1);
            }

            private void setContentView(int activity_main) {
            }});

        // Player 1 - Choisir son mana
        manaP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { chooseManaP1(); }
        });


// -------------------------------- Player 2 -------------------------------------------------------

        // Player 2 - Définir action -1 au click du bouton "btnMinusLifePlayer2"
        minusLifeP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2--;
                String j2 = Integer.toString(i2);
                lifePointP2.setText(j2);
            }

            private void setContentView(int activity_main) {
            }});

        // Player 2 - Définir action +1 au click du bouton "btnAddLifePlayer2"
        addLifeP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2++;
                String j2 = Integer.toString(i2);
                lifePointP2.setText(j2);
            }

            private void setContentView(int activity_main) {
            }});

        // Player 2 - Choisir son mana
        manaP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { chooseManaP2(); }
        });

    }


// -------------------------------------Méthodes----------------------------------------------------

    // Hidding the Status Bar :
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }



    // Méthode mana Player 1 :
    public void chooseManaP1() {
        AlertDialog.Builder builderManaP1 = new AlertDialog.Builder(this);
        builderManaP1.setTitle("Mana");

        // Add the choices
        String[] mana = {"black", "blue", "white", "red", "green"};
        builderManaP1.setItems(mana, new DialogInterface.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: terrainP1.setImageResource(R.drawable.blackmanacropped);
                    break;
                    case 1: terrainP1.setImageResource(R.drawable.bluemanacropped);
                    break;
                    case 2: terrainP1.setImageResource(R.drawable.whitemanacropped);
                    break;
                    case 3: terrainP1.setImageResource(R.drawable.redmanacropped);
                    break;
                    case 4: terrainP1.setImageResource(R.drawable.greenmanacropped);
                    break;
                }
            }
        });

        // Afficher la boite de Dialog
        AlertDialog dialog = builderManaP1.create();
        builderManaP1.show();
    }



    // Méthode mana Player 2 :
    public void chooseManaP2() {
        AlertDialog.Builder builderManaP2 = new AlertDialog.Builder(this);
        builderManaP2.setTitle("Mana");

        // Add the choices
        String[] mana = {"black", "blue", "white", "red", "green"};
        builderManaP2.setItems(mana, new DialogInterface.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: terrainP2.setImageResource(R.drawable.blackmanacropped);
                    break;
                    case 1: terrainP2.setImageResource(R.drawable.bluemanacropped);
                    break;
                    case 2: terrainP2.setImageResource(R.drawable.whitemanacropped);
                    break;
                    case 3: terrainP2.setImageResource(R.drawable.redmanacropped);
                    break;
                    case 4: terrainP2.setImageResource(R.drawable.greenmanacropped);
                    break;
                }
            }
        });

        // Afficher la boite de Dialog
        AlertDialog dialog = builderManaP2.create();
        builderManaP2.show();
    }



    // Méthode NavBar - SetLifePoints :
    public void chooseLifePoints() {
        AlertDialog.Builder builderLifePoints = new AlertDialog.Builder(this);
        builderLifePoints.setMessage(R.string.dialog_message);

        // Add the buttons
        builderLifePoints.setPositiveButton("40", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 40 button
                valueDepart = 40;
                i1=valueDepart;
                i2=valueDepart;
                String n = Integer.toString(valueDepart);
                lifePointP1.setText(n);
                lifePointP2.setText(n);

            }
        });
        builderLifePoints.setNeutralButton("20", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 20 button
                valueDepart = 20;
                i1=valueDepart;
                i2=valueDepart;
                String n = Integer.toString(valueDepart);
                lifePointP1.setText(n);
                lifePointP2.setText(n);
            }
        });
        builderLifePoints.setNegativeButton("30", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked 30 button
                valueDepart = 30;
                i1=valueDepart;
                i2=valueDepart;
                String n = Integer.toString(valueDepart);
                lifePointP1.setText(n);
                lifePointP2.setText(n);
            }
        });

        // Afficher la boite de Dialog
        builderLifePoints.show();
    }



        // Méthode NavBar - Fight :
    public void fight() {
        int randomWinner = rng.nextInt(2)+1;
        final int[] time = {2};

        new CountDownTimer(2000, 100) {
            public void onTick(long millisUntilFinished){
                switch (randomWinner) {
                    case 1:
                        textWinP1.setText("WINNER");
                        textWinP2.setText(" ");
                        break;
                    case 2:
                        textWinP1.setText(" ");
                        textWinP2.setText("WINNER");
                        time[0]++;
                }
            }
            public void onFinish(){
                textWinP1.setText(" ");
                textWinP2.setText(" ");
            }
        }.start();




        

    }

}