package com.example.kilgore.chtrack;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextCarb;
    Button saveButton, loadButton;
    TextView resultTextView;

    DBHandler dbHandler;

    FoodListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);

        //editTextName = (EditText) findViewById(R.id.food_name);
        //editTextCarb = (EditText) findViewById(R.id.carb);
        //saveButton = (Button) findViewById(R.id.save);
        //loadButton = (Button) findViewById(R.id.load);
        //resultTextView = (TextView) findViewById(R.id.result);

        final List<Food> items = new ArrayList<>();

        adapter = new FoodListAdapter(items);

        ListView listview = (ListView) findViewById(R.id.listview_food);
        listview.setAdapter(adapter);

        Cursor cursor = dbHandler.readFood();

        items.clear();

        while ( !cursor.isAfterLast()){

            int id = cursor.getInt(0);
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String carb = cursor.getString(cursor.getColumnIndex("carb"));

            items.add(new Food(id, name, Float.parseFloat(carb)));

            cursor.moveToNext();
        }

        adapter.notifyDataSetChanged();
    }
}
