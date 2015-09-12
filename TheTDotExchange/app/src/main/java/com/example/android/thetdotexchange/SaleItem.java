package com.example.android.thetdotexchange;

import android.graphics.Bitmap;

import java.text.DecimalFormat;

/**
 * Created by Bocaj on 12/09/2015.
 */
public class SaleItem {
    String courseCode;
    String title;
    String priceString;
    String distanceString;
    double price;
    double distance;

    SaleItem(String courseCode, String title, double price, double distance){
        DecimalFormat df = new DecimalFormat("#.##");
        this.courseCode = courseCode;
        this.title = title;

        if(price < 0) price = 0;

        this.price = price;
        this.distance = distance;

        priceString = "$" + df.format(price);
        distanceString = df.format(distance) + " km";
    }
}
