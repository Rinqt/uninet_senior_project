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

    static class ViewHolder {
        TextView food;
    }

    public DiningListAdapter(@NonNull Context context, int resource, @NonNull List<Lunch> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder diningViewHolder;
        String food = getItem(position).getLunchName();

        if (convertView ==  null)
        {
            LayoutInflater lunchInflater = LayoutInflater.from(mContext);
            convertView = lunchInflater.inflate(mResource, parent, false);

            diningViewHolder = new ViewHolder();
            diningViewHolder.food = convertView.findViewById(R.id.food_name);

            convertView.setTag(diningViewHolder);
        }
        else
            diningViewHolder = (ViewHolder) convertView.getTag();

        diningViewHolder.food.setText(food);

        return convertView;

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
