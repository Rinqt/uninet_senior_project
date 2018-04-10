package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kaany on 28.02.2018.
 */
class UniPost
{
    String userName = "";
    String date = "";
    String description = "";
    int userPhoto = 0;
    int menuButton = 0;

    public UniPost(String userName, String date, String description, int userPhoto) {
        this.userName = userName;
        this.date = date;
        this.description = description;
        this.userPhoto = userPhoto;
    }
}
public class ListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<UniPost> list; // Currently empty.

    ListViewAdapter(Context c) {
        context = c;
        list = new ArrayList<UniPost>();

        Resources res = c.getResources();
        String name = "Kaan YÖŞ";
        String[] date = res.getStringArray(R.array.date);
        String[] descriptions = res.getStringArray(R.array.post);
        int userPhoto = R.mipmap.awesome_kaan;

        for (int i = 0; i < 5; i++)
        {
            list.add(new UniPost(name, date[i], descriptions[i], userPhoto));
        }
    }

    @Override
    public int getCount() { // Need to return number of elements
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 1. Get the root view
        // 2. Use the root view to find other views
        // 3. Set the values

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View uniPost = layoutInflater.inflate(R.layout.post_template, viewGroup, false);

        TextView userName = uniPost.findViewById(R.id.user_name);
        TextView date = uniPost.findViewById(R.id.unipost_date);
        TextView description = uniPost.findViewById(R.id.unipost_text);
        ImageView userPhoto = uniPost.findViewById(R.id.profile_picture);


        UniPost temp = list.get(i);

        userName.setText(temp.userName);
        date.setText(temp.date);
        description.setText(temp.description);
        userPhoto.setImageResource(temp.userPhoto);

        return uniPost;

    }
}
