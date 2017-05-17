package com.example.kilgore.chtrack;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    EditText editTextName, editTextCH, editTextQuantity;
    Button buttonAdd;

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

                final String name =  editTextName.getText().toString().trim();
                final String carb =  editTextCH.getText().toString().trim();
                final String quantity = editTextQuantity.getText().toString().trim();

                if (name.isEmpty() || carb.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(DetailsActivity.this, "No empty values allowed", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (!name.equalsIgnoreCase(selectedFood.getName()) || Float.parseFloat(carb) != selectedFood.getCarb()) {

                        AlertDialog.Builder ab = new AlertDialog.Builder(DetailsActivity.this);

                        ab.setTitle("Food info changed");
                        ab.setMessage("Do you really want to change name and/or CH values for this food?");

                        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.updateFood(selectedFood.getId(), name, Float.parseFloat(carb));
                                Toast.makeText(DetailsActivity.this, "Food saved", Toast.LENGTH_SHORT).show();
                            }
                        });

                        ab.setNegativeButton("Cancel", null);
                        ab.show();

                    }
                    else {

                        db.createMeal(new Meal(name, selectedFood, Float.parseFloat(quantity)));
                        Toast.makeText(DetailsActivity.this, "Meal saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}