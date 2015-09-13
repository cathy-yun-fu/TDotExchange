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
    double latitude;
    double longitude;

    static double myLat = 0;
    static double myLon = 0;

    SaleItem(String courseCode, String title, double price, double lat, double lon){
        DecimalFormat df = new DecimalFormat("#.##");
        this.courseCode = courseCode;
        this.title = title;

        if(price < 0) price = 0;

        this.price = price;

        priceString = "$" + df.format(price);

        latitude = lat;
        longitude = lon;

        distance = distance(myLat, myLon, lat, lon, 'K');
        distanceString = df.format(distance) + " km";
    }

    public SaleItem(SaleItem another) {
        this.courseCode = another.courseCode;
        this.title = another.title;
        this.priceString = another.priceString;
        this.distanceString = another.distanceString;
        this.price = another.price;
        this.distance = another.distance;
        this.latitude = another.latitude;
        this.longitude = another.longitude;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        // Kilometers
        if (unit == 'K') {
            dist = dist * 1.609344;
        }
        // Nautical Mile
        else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
