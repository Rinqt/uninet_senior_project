package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Lunch;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

public class DiningListAdapter extends ArrayAdapter<Lunch> {

    private Context mContext;
    private int mResource;

    public DiningListAdapter(@NonNull Context context, int resource, @NonNull List<Lunch> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String food = getItem(position).getLunchName();

        LayoutInflater lunchInflator = LayoutInflater.from(mContext);
        convertView = lunchInflator.inflate(mResource, parent, false);

        TextView foodName = convertView.findViewById(R.id.food_name);

        foodName.setText(food);

        return convertView;

    }
}
