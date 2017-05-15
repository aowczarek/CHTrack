package com.example.kilgore.chtrack;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FoodListAdapter extends BaseAdapter {

    private List<Food> items;

    public FoodListAdapter(List<Food> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.foodlist_item, null);
        }

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.foodlist_name);
        TextView carbTextView = (TextView) listItemView.findViewById(R.id.foodlist_carb);

        Food food = items.get(position);
        nameTextView.setText(food.getName());
        carbTextView.setText("(" + food.getCarb().toString() + "g/100g)");

        return listItemView;
    }
}
