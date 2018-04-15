package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Messages;
import com.seniorproject.uninet.uninet.R;

import java.util.List;


public class MessagesListAdapter extends ArrayAdapter<Messages> {

    private  Context mContext;
    private int mResource;

    public MessagesListAdapter(@NonNull Context context, int resource, @NonNull List<Messages> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String friendName = getItem(position).getFriendUserName();
        String friendMessage = getItem(position).getLastMessage();
        byte[] friendProfilePhoto = getItem(position).getFriendSmallProfilePicture();

        Messages messages = new Messages(friendName, friendMessage, friendProfilePhoto );

        LayoutInflater messagesInflater = LayoutInflater.from(mContext);
        convertView = messagesInflater.inflate(mResource, parent, false);

        TextView friendUserName = convertView.findViewById(R.id.friend_user_name);
        TextView friendLastMessage = convertView.findViewById(R.id.friend_last_message);
        ImageView friendPhoto = convertView.findViewById(R.id.friend_profile_picture);

        friendUserName.setText(friendName);
        friendLastMessage.setText(friendMessage);
        friendPhoto.setImageResource(R.mipmap.messages_icon);

        return convertView;



    }
}
