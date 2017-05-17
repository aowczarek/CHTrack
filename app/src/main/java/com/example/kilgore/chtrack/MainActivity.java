package com.example.kilgore.chtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonAddMeal, buttonBrowseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        buttonAddMeal = (Button) findViewById(R.id.button_addMeal);
        buttonBrowseDB = (Button) findViewById(R.id.button_browseDB);

        BarGraph barGraph = (BarGraph) findViewById(R.id.barGraph);

        buttonBrowseDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                startActivity(intent);
            }
        });


    }
}
