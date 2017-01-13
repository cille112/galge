package com.example.cille_000.mingalgeleg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;


public class ShowWords extends AppCompatActivity {

    private Galgelogik galgelogik;
    private ListView listView;
    private ArrayAdapter adapter;
    private List<String> ord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        listView = (ListView) findViewById(R.id.list);
        galgelogik = new Galgelogik();


        new async().execute();



    }

    private void showGame(String word){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Word", word);
        this.startActivity(intent);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private class async extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0) {
            try {
                galgelogik.hentOrdFraDr();
                return "Ordene blev korrekt hentet fra DR's server";
            } catch (Exception e) {
                e.printStackTrace();
                return "Ordene blev ikke hentet korrekt: " + e;
            }
        }

        @Override
        protected void onPostExecute(Object resultat) {
            ord = galgelogik.getList();
            adapter = new StableArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ord);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    System.out.println(item);
                    showGame(item);
                }

            });
        }
    }

}
