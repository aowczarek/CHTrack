package com.example.kilgore.chtrack;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBActivity extends AppCompatActivity {

    FoodListAdapter adapter;
    DBHandler dbHandler;

    EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        ListView listview = (ListView) findViewById(R.id.listview_food);
        editTextSearch = (EditText) findViewById(R.id.search);

        dbHandler = new DBHandler(this);
        Cursor cursor = dbHandler.readFood();

        final List<Food> items = new ArrayList<>();
        adapter = new FoodListAdapter(items);
        listview.setAdapter(adapter);
        items.clear();

        while ( !cursor.isAfterLast()){

            int id = cursor.getInt(0);
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String carb = cursor.getString(cursor.getColumnIndex("carb"));

            items.add(new Food(id, name, Float.parseFloat(carb)));

            cursor.moveToNext();
        }

        adapter.notifyDataSetChanged();

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                Cursor cursor = dbHandler.filterFood(editTextSearch.getText().toString().toLowerCase(Locale.getDefault()));

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

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }


}