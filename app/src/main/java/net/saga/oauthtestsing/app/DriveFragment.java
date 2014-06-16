package net.saga.oauthtestsing.app;

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

import java.util.ArrayList;
import java.util.List;

public class DriveFragment extends Fragment {

    private ArrayList<Files> fileses = new ArrayList<Files>();
    private static final String FILES_KEY = "oauthtesting.FILES";

    public DriveFragment() {
        this.fileses = new ArrayList<Files>();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getArguments() != null && getArguments().getParcelableArrayList(FILES_KEY) != null) {
            if (fileses.isEmpty()) {
                fileses = getArguments().getParcelableArrayList(FILES_KEY);
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.drive_list, null);

        ListView driveItems = (ListView) view.findViewById(R.id.drive_items);

        driveItems.setAdapter(new ArrayAdapter<Files>(getActivity(), android.R.layout.simple_list_item_1, fileses) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Files files = fileses.get(position);

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
