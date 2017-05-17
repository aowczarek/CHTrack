package com.example.kilgore.chtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    BarGraph barGraph;
    Button buttonBrowseDB;
    TextView textViewTotalCH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        barGraph = (BarGraph) findViewById(R.id.barGraph);
        buttonBrowseDB = (Button) findViewById(R.id.button_browseDB);
        textViewTotalCH = (TextView) findViewById(R.id.totalCH);

        buttonBrowseDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHandler db = new DBHandler(this);

        textViewTotalCH.setText(String.format(Locale.getDefault(), "%.2f", db.getTotalCHToday()));

    }
}
