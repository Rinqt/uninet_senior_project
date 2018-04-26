package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Messages;
import com.seniorproject.uninet.uninet.MessagingScreenActivity;
import com.seniorproject.uninet.uninet.R;

import java.util.List;
import java.util.Objects;


public class MessagesListAdapter extends ArrayAdapter<Messages> {

    private  Context mContext;
    private int mResource;

    static class ViewHolder  {
        TextView friendName;
        TextView friendMessage;
        ImageView friendProfilePhoto;
    }

    public MessagesListAdapter(@NonNull Context context, int resource, @NonNull List<Messages> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder messagesViewHolder;
        final String name = Objects.requireNonNull(getItem(position)).getFriendUserName();
        final String conversationId = Objects.requireNonNull(getItem(position)).getConversationID();
        String message = Objects.requireNonNull(getItem(position)).getLastMessage();
        byte[] picture = Objects.requireNonNull(getItem(position)).getFriendSmallProfilePicture();

        if (convertView == null)
        {
            LayoutInflater messagesInflater = LayoutInflater.from(mContext);
            convertView = messagesInflater.inflate(mResource, parent, false);

            messagesViewHolder = new ViewHolder();

            messagesViewHolder.friendName = convertView.findViewById(R.id.friend_user_name);
            messagesViewHolder.friendMessage = convertView.findViewById(R.id.friend_last_message);
            messagesViewHolder.friendProfilePhoto = convertView.findViewById(R.id.friend_list_friend_profile_picture);

            convertView.setTag(messagesInflater);
        }
        else
        {
            messagesViewHolder = (ViewHolder) convertView.getTag();
        }

        messagesViewHolder.friendName.setText(name);
        messagesViewHolder.friendMessage.setText(message);
        messagesViewHolder.friendProfilePhoto.setImageResource(R.mipmap.ic_launcher);
        //messagesViewHolder.friendProfilePhoto.setImageResource(picture[position]);


        messagesViewHolder.friendProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageScreen(conversationId, name);
            }
        });

        messagesViewHolder.friendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageScreen(conversationId, name);
            }
        });






        return convertView;
    }

    private void goToMessageScreen(String value, String name)
    {
        Intent messageScreen = new Intent(mContext, MessagingScreenActivity.class);
        messageScreen.putExtra("conversationId", value);
        messageScreen.putExtra("UserName", name);
        messageScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(messageScreen);
    }

}
