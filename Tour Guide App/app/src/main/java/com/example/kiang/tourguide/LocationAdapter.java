package com.example.kiang.tourguide;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kiang on 11/22/2016.
 */
public class LocationAdapter extends ArrayAdapter<Location> {

    public LocationAdapter(Activity activity, ArrayList<Location> locations) {
        super(activity, 0, locations);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View locationItemView = convertView;

        if (locationItemView == null) {
            locationItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.place_template, parent, false);
        }

        Location location = getItem(position);

        TextView nameTextView = (TextView) locationItemView.findViewById(R.id.location_name);
        nameTextView.setText(location.getLocationName());

        TextView locationTextView = (TextView) locationItemView.findViewById(R.id.location_location);
        locationTextView.setText(location.getLocation());

        ImageView photo = (ImageView) locationItemView.findViewById(R.id.location_photo);
        if (location.hasImage()) {
            photo.setImageResource(location.getImageResourceId());
        } else {
            photo.setVisibility(View.GONE);
        }

        return locationItemView;
    }
}
