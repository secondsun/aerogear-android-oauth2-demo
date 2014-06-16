/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.android.oauth2.googledrivedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import net.saga.oauthtestsing.app.R;

import java.util.ArrayList;
import java.util.List;

public class DriveFragment extends Fragment {

    private ArrayList<Files> files = new ArrayList<Files>();
    private static final String FILES_KEY = "oauthtesting.FILES";

    public DriveFragment() {
        this.files = new ArrayList<Files>();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getArguments() != null && getArguments().getParcelableArrayList(FILES_KEY) != null) {
            if (files.isEmpty()) {
                files = getArguments().getParcelableArrayList(FILES_KEY);
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.drive_list, null);

        ListView driveItems = (ListView) view.findViewById(R.id.drive_items);

        driveItems.setAdapter(new ArrayAdapter<Files>(getActivity(), android.R.layout.simple_list_item_1, files) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Files files = DriveFragment.this.files.get(position);

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.drive_list_item, null);
                }

                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                Picasso.with(getActivity()).load(files.getIconLink()).into(imageView);

                TextView name = (TextView) convertView.findViewById(R.id.textView);
                name.setText(files.getTitle());

                return convertView;
            }
        });

        return view;
    }

    public static DriveFragment newInstance(List<Files> files) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(FILES_KEY, new ArrayList<>(files));
        DriveFragment fragment = new DriveFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

}
