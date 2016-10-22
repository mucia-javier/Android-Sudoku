package com.example.javcoz.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class playmenu extends AppCompatActivity {
    int mode = 0;  // 0=UNSET, 1=EASY, 2=MEDIUM, 3=DIFFICULT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmenu);




        Button easyButton = (Button) findViewById(R.id.PlaySudokuButton_Easy);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(playmenu.this, PickPuzzleNum.class);
                mode = 1;
                theIntent.putExtra("MODE", mode);
                startActivity(theIntent);
                finish();
            }
        });

        Button medButton = (Button) findViewById(R.id.PlaySudokuButton_Medium);
        medButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(playmenu.this, PickPuzzleNum.class);
                mode =2;
                theIntent.putExtra("MODE", mode);
                startActivity(theIntent);
                finish();
            }
        });

        Button difButton = (Button) findViewById(R.id.PlaySudokuButton_Difficult);
        difButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(playmenu.this, PickPuzzleNum.class);
                mode =3;
                theIntent.putExtra("MODE", mode);
                startActivity(theIntent);
                finish();
            }
        });

        Button goToMain = (Button) findViewById(R.id.GoToMainMenu);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(playmenu.this, MainActivity.class);
                startActivity(theIntent);
                finish();
            }
        });
        Button exitButton = (Button) findViewById(R.id.ExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                finish();
            }
        });

    }
}
