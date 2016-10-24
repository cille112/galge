package com.example.cille_000.mingalgeleg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.cille_000.galge.R.id.editText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Galgelogik logik = new Galgelogik();
        final TextView ord = (TextView) findViewById(R.id.textView2);
        final TextView bogstaver = (TextView) findViewById(R.id.textView8);
        final EditText bogstav = (EditText) findViewById(R.id.editText);
        final Button guess = (Button) findViewById(R.id.button);
        final ImageView picture = (ImageView) findViewById(R.id.imageView);
        final TextView vel = (TextView) findViewById(R.id.textView);

        ord.setText(logik.getSynligtOrd());

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
                        vel.setText("Spillet er slut og du har tabt");
                    }
                    if(logik.erSpilletVundet()){
                        vel.setText("Tillykke du gættede ordet!");
                    }
                }
            }
        });





    }
}
