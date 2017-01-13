package com.example.cille_000.mingalgeleg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Restart extends AppCompatActivity {

    private TextView titel;
    private SharedPreferences sharedPref;
    private Button knap;
    private TextView stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);

        titel = (TextView) findViewById(R.id.textView5);
        sharedPref = getSharedPreferences(getString(R.string.Prefrence_file_key), Context.MODE_PRIVATE);
        knap = (Button) findViewById(R.id.button3);
        stat = (TextView) findViewById(R.id.textView6);

        titel.setText(sharedPref.getString("state","lehgska"));

        stat.setText("Spil statistik: \n " + sharedPref.getInt("vundet",0) + "/" + sharedPref.getInt("alt",0));

        knap.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Restart.this, ShowWords.class);
                Restart.this.startActivity(intent);
            }
        }));

    }
}
