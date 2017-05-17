package com.example.kilgore.chtrack;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBActivity extends AppCompatActivity {

    FoodListAdapter adapter;
    DBHandler dbHandler;
    ListView listview;

    EditText editTextSearch;

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
        listview.invalidateViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        listview = (ListView) findViewById(R.id.listview_food);
        editTextSearch = (EditText) findViewById(R.id.search);

        final List<Food> items = new ArrayList<>();
        items.clear();

        adapter = new FoodListAdapter(items);
        listview.setAdapter(adapter);

        dbHandler = new DBHandler(this);
        Cursor cursor = dbHandler.readFood();

        while ( !cursor.isAfterLast()){

            Long id = cursor.getLong(0);
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String carb = cursor.getString(cursor.getColumnIndex("carb"));

            items.add(new Food(id, name, Float.parseFloat(carb)));

            cursor.moveToNext();
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                Cursor cursor = dbHandler.filterFood(editTextSearch.getText().toString().toLowerCase(Locale.getDefault()));

                items.clear();

                while ( !cursor.isAfterLast()){

                    Long id = cursor.getLong(0);
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

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO do something
                    handled = true;
                }
                return handled;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Food selected = (Food) adapter.getItem(position);

                Intent intent = new Intent(DBActivity.this, DetailsActivity.class);

                intent.putExtra("selected", selected);
                startActivity(intent);
            };


        });
    }


}