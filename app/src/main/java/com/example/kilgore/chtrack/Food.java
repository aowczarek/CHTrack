package com.example.kilgore.chtrack;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    private Long id;
    private String name;
    private Float carb;

    public Food(Long id, String name, Float carb) {
        this.id = id;
        this.name = name;
        this.carb = carb;
    }

    protected Food(Parcel in) {
        id = in.readLong();
        name = in.readString();
        carb = in.readFloat();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public Long getId() {
        return id;
    }

    public String getName() {

        return name;
    }

    public Float getCarb() {

        return carb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeFloat(carb);

    }
}