package com.cs349.john.a5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Drawing drawing = (Drawing) findViewById(R.id.draw);

        Button resetButton = (Button) findViewById(R.id.reset);
        Button aboutButton = (Button) findViewById(R.id.about);


        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawing.reset();
            }
        });


        aboutButton.setOnClickListener(new View.OnClickListener() {

            // Help from here: https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
            @Override
            public void onClick(View v) {
                AlertDialog ad = new AlertDialog.Builder(v.getContext())
                    .setTitle("A4 Ragdoll Application")
                    .setMessage("John (Jialong) Ma\n jl2ma")
                    .setNegativeButton(android.R.string.no, null)
                    .show();
            }
        });
    }
}