package com.example.cille_000.mingalgeleg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Galgelogik logik;
    private TextView ord;
    private TextView bogstaver;
    private EditText bogstav;
    private Button guess;
    private ImageView picture;
    private TextView vel;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private int total;
    private int wins;
    private String ordet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        ordet = intent.getStringExtra("Word");

        ord = (TextView) findViewById(R.id.textView2);
        bogstaver = (TextView) findViewById(R.id.textView8);
        bogstav = (EditText) findViewById(R.id.editText);
        guess = (Button) findViewById(R.id.button);
        picture = (ImageView) findViewById(R.id.imageView);
        vel = (TextView) findViewById(R.id.textView);
        logik = new Galgelogik();
        logik.setOrdet(ordet);

        ord.setText(logik.getSynligtOrd());

        sharedPref = getSharedPreferences(getString(R.string.Prefrence_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();





        bogstav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bogstav.setText("");
            }
        });




        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = bogstav.getText().toString();
                if(s.length()!=1) {
                    bogstav.setError("Du skal skrive 1 bogstav");
                }
                else {
                    logik.gætBogstav(s);
                    ord.setText(logik.getSynligtOrd());
                    bogstav.setText("");
                    ArrayList<String> letters = logik.getBrugteBogstaver();
                    String ss = "";
                    for (int i=0; i<letters.size();i++){
                        ss += letters.get(i) + ",";
                    }
                    bogstaver.setText(ss);
                    if(logik.getAntalForkerteBogstaver()==1){
                        picture.setImageResource(R.drawable.forkert1);
                    }
                    else if(logik.getAntalForkerteBogstaver()==2){
                        picture.setImageResource(R.drawable.forkert2);
                    }
                    else if(logik.getAntalForkerteBogstaver()==3){
                        picture.setImageResource(R.drawable.forkert3);
                    }
                    else if(logik.getAntalForkerteBogstaver()==4){
                        picture.setImageResource(R.drawable.forkert4);
                    }
                    else if(logik.getAntalForkerteBogstaver()==5){
                        picture.setImageResource(R.drawable.forkert5);
                    }
                    else if(logik.getAntalForkerteBogstaver()==6) {
                        picture.setImageResource(R.drawable.forkert6);
                        editor.putString("state", "Desværre du tabte spillet").commit();
                        int times = sharedPref.getInt("alt", 0);
                        total=times+1;
                        editor.putInt("alt", total).commit();
                        Intent intent = new Intent(MainActivity.this, Restart.class);
                        MainActivity.this.startActivity(intent);
                    }
                    if(logik.erSpilletVundet()){
                        editor.putString("state", "Tilykke du vandt spillet").commit();
                        int times = sharedPref.getInt("alt", 0);
                        total=times+1;
                        int win = sharedPref.getInt("vundet", 0);
                        wins=win+1;
                        editor.putInt("alt", total).commit();
                        editor.putInt("vundet", wins).commit();
                        Intent intent = new Intent(MainActivity.this, Restart.class);
                        MainActivity.this.startActivity(intent);

                    }
                }
            }
        });
    }
}
