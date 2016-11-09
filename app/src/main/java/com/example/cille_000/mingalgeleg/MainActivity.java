package com.example.cille_000.mingalgeleg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Galgelogik logik = new Galgelogik();
    private TextView ord;
    private TextView bogstaver;
    private EditText bogstav;
    private Button guess;
    private ImageView picture;
    private TextView vel;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private int i;
    private int o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ord = (TextView) findViewById(R.id.textView2);
        bogstaver = (TextView) findViewById(R.id.textView8);
        bogstav = (EditText) findViewById(R.id.editText);
        guess = (Button) findViewById(R.id.button);
        picture = (ImageView) findViewById(R.id.imageView);
        vel = (TextView) findViewById(R.id.textView);

        sharedPref = getSharedPreferences(getString(R.string.Prefrence_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        ord.setText(logik.getSynligtOrd());
        i = sharedPref.getInt("alt", 0);
        o = sharedPref.getInt("vundet", 0);

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
                        i=i+1;
                        editor.putInt("alt", i).commit();
                        Intent intent = new Intent(MainActivity.this, Restart.class);
                        MainActivity.this.startActivity(intent);
                    }
                    if(logik.erSpilletVundet()){
                        editor.putString("state", "Tilykke du vandt spillet").commit();
                        i=i+1;
                        o=o+1;
                        editor.putInt("alt", i).commit();
                        editor.putInt("vundet", o).commit();
                        Intent intent = new Intent(MainActivity.this, Restart.class);
                        MainActivity.this.startActivity(intent);

                    }
                }
            }
        });
    }
}
