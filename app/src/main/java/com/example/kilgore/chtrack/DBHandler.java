package com.example.kilgore.chtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import static com.example.kilgore.chtrack.DBHandler.DBHelper.TABLE_NAME_FOOD;
import static com.example.kilgore.chtrack.DBHandler.DBHelper.TABLE_NAME_MEAL;

public class DBHandler {

    protected DBHelper dbHelper;

    public DBHandler(Context context){
        dbHelper = new DBHelper(context);
    }

    public void createFood(String name, Float carb){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("carb", carb);

        db.insert(TABLE_NAME_FOOD, null, values);
        db.close();
    }

    public Cursor readFood(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_FOOD, null, null, null, null, null, null);
        //db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public void updateFood(Long id, String name, Float carb){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("carb", carb);

        db.update(TABLE_NAME_FOOD, values, "_id=?", new String[]{id.toString()});
        db.close();
    }

    public Cursor filterFood(String filterString){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FOOD +
                " WHERE NAME LIKE '%" + filterString.toLowerCase(Locale.getDefault()) + "%'", null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public void createMeal(Meal meal){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("food_id", meal.getFood().getId());
        values.put("quantity", meal.getQuantity());

        db.insert(TABLE_NAME_MEAL, null, values);
        db.close();
    }

    public Cursor readMeal(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT strftime('%H', datetime(timestamp, 'localtime')) as hour, quantity / 100 * carb as consumed " +
                                    " FROM " + TABLE_NAME_MEAL + " LEFT JOIN " + TABLE_NAME_FOOD + " ON FOOD._id = MEAL.food_id " +
                                    " WHERE datetime(timestamp, 'localtime') > datetime('now', 'localtime', 'start of day')", null);

        cursor.moveToFirst();

        db.close();
        return cursor;
    }

    public Float getTotalCHToday(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT sum(quantity / 100 * carb) as total " +
                                    " FROM " + TABLE_NAME_MEAL + " LEFT JOIN " + TABLE_NAME_FOOD + " ON FOOD._id = MEAL.food_id " +
                                    " WHERE datetime(timestamp, 'localtime') > datetime('now', 'localtime', 'start of day')", null);
        cursor.moveToFirst();

        return cursor.getFloat(0);
    }

    public class DBHelper extends SQLiteOpenHelper {

        public final static String DB_NAME = "MY_DB";
        public final static int DB_VERSION = 1;
        public final static String TABLE_NAME_FOOD = "food";
        public final static String TABLE_NAME_MEAL = "meal";

        private final Context context;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME_FOOD + "(" +
                    "_id    INTEGER PRIMARY KEY, " +
                    "name   TEXT NOT NULL," +
                    "carb   REAL NOT NULL" +
                    ")");

            db.execSQL("CREATE TABLE " + TABLE_NAME_MEAL + "(" +
                            "_id        INTEGER PRIMARY KEY, " +
                            "timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                            "food_id    INTEGER NOT NULL, " +
                            "quantity   REAL NOT NULL, " +
                            "FOREIGN KEY(food_id) REFERENCES food(_id)" +
                    ")");

            InputStream inputStream =  context.getResources().openRawResource(R.raw.db);

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            try {
                while ((line = r.readLine()) != null) {
                    db.execSQL(line);
                }
            } catch (IOException e) {

            }



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEAL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FOOD);
            onCreate(db);
        }
    }
}
