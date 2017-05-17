package com.example.kilgore.chtrack;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    EditText editTextName, editTextCH, editTextQuantity;
    Button buttonAdd, buttonEdit;

    Food selectedFood;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = new DBHandler(this);

        editTextName = (EditText) findViewById(R.id.nameValue);
        editTextCH = (EditText) findViewById(R.id.chValue);
        editTextQuantity = (EditText) findViewById(R.id.quantityValue);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);

        editTextQuantity.requestFocus();

        Bundle bundle  = getIntent().getExtras();

        if (bundle.containsKey("selected"))
        {
            selectedFood = bundle.getParcelable("selected");

            if (selectedFood != null)
            {
                editTextName.setText(selectedFood.getName());
                editTextCH.setText(String.format(Locale.getDefault(), "%.2f", selectedFood.getCarb()));
            }
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name =  editTextName.getText().toString().trim();
                String carb =  editTextCH.getText().toString().trim();
                String quantity = editTextQuantity.getText().toString().trim();

                if (name.isEmpty() || carb.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(DetailsActivity.this, "No empty values allowed", Toast.LENGTH_SHORT).show();
                }

//                if (!name.equalsIgnoreCase(selectedFood.getName())){
//
//                }
//
//                if(!carb.equalsIgnoreCase(selectedFood.getCarb().toString())){
//
//                }

                else{
                    db.createMeal(new Meal(name, selectedFood, Float.parseFloat(quantity)));
                }
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String name =  editTextName.getText().toString().trim();
                String carb =  editTextCH.getText().toString().trim();

                if (name.isEmpty() || carb.isEmpty()) {
                    Toast.makeText(DetailsActivity.this, "No empty values allowed", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.updateFood(selectedFood.getId(), name, Float.parseFloat(carb));
                    Toast.makeText(DetailsActivity.this, "Food info updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}