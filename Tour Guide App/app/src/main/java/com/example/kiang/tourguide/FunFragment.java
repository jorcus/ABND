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


public class FunFragment extends Fragment {
    public FunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_list, container, false);

        final ArrayList<Location> location_Card = new ArrayList<Location>();
        location_Card.add(new Location(getString(R.string.sunway_pyramid_name), getString(R.string.sunway_pyramid_location), R.drawable.sunwaypyramid));
        location_Card.add(new Location(getString(R.string.setia_city_mall_name), getString(R.string.setia_city_mall_location), R.drawable.setiacitymall));
        location_Card.add(new Location(getString(R.string.royal_museum_name), getString(R.string.royal_museum_location), R.drawable.royalmuseum));
        location_Card.add(new Location(getString(R.string.pavilion_name), getString(R.string.pavilion_location), R.drawable.pavilion));
        location_Card.add(new Location(getString(R.string.mid_valley_name), getString(R.string.mid_valley_location), R.drawable.midvalley));
        location_Card.add(new Location(getString(R.string.ioi_city_mall_name), getString(R.string.ioi_city_mall_location), R.drawable.ioishoppingmall));
        location_Card.add(new Location(getString(R.string.icity_name), getString(R.string.icity_location), R.drawable.icity));
        location_Card.add(new Location(getString(R.string.berjaya_times_square_name), getString(R.string.berjaya_times_square_location), R.drawable.berjayatimesquare));

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
