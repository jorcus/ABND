/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.kiang.tourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HotelFragment extends Fragment {
    public HotelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_list, container, false);

        final ArrayList<Location> location_Card = new ArrayList<Location>();
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.enakrestaurant));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.lafite_at_shangri_la));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.marble8));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.marinion57));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.nobu));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.prego));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.thirty8));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.troikaskydining));
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.viewrooftopbar));

        LocationAdapter adapter = new LocationAdapter(getActivity(), location_Card);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Location location = location_Card.get(position);
            }
        });

        return rootView;
    }
}
