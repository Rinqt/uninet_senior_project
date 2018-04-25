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
import com.seniorproject.uninet.uninet.OtherUserProfileActivity;
import com.seniorproject.uninet.uninet.R;

import java.util.List;


public class MessagesListAdapter extends ArrayAdapter<Messages> {

    private  Context mContext;
    private int mResource;

    static class ViewHolder {
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
        String name = getItem(position).getFriendUserName();
        final String conversationId = getItem(position).getConversationID();
        String message = getItem(position).getLastMessage();
        byte[] picture = getItem(position).getFriendSmallProfilePicture();

        if (convertView == null)
        {
            LayoutInflater messagesInflater = LayoutInflater.from(mContext);
            convertView = messagesInflater.inflate(mResource, parent, false);

            messagesViewHolder = new ViewHolder();

            messagesViewHolder.friendName = convertView.findViewById(R.id.friend_user_name);
            messagesViewHolder.friendMessage = convertView.findViewById(R.id.friend_last_message);
            messagesViewHolder.friendProfilePhoto = convertView.findViewById(R.id.friend_profile_picture);

            convertView.setTag(messagesInflater);
        }
        else
        {
            messagesViewHolder = (ViewHolder) convertView.getTag();
        }

        messagesViewHolder.friendName.setText(name);
        messagesViewHolder.friendMessage.setText(message);
        messagesViewHolder.friendProfilePhoto.setImageResource(R.mipmap.messages_icon);
        //messagesViewHolder.friendProfilePhoto.setImageResource(picture[position]);


        messagesViewHolder.friendProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageScreen = new Intent(mContext, OtherUserProfileActivity.class);
                messageScreen.putExtra("UserID", "1");
                messageScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(messageScreen);

            }
        });

        messagesViewHolder.friendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageScreen(conversationId);

            }
        });






        return convertView;



    }

    private void goToMessageScreen(String value)
    {
        Intent messageScreen = new Intent(mContext, MessagingScreenActivity.class);
        messageScreen.putExtra("conversationId", value);
        messageScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(messageScreen);
    }
}
