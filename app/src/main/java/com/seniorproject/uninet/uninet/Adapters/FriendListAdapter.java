package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.ConstructorClasses.Friends;
import com.seniorproject.uninet.uninet.DatabaseClasses.Conversation;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.LoggedInUser;
import com.seniorproject.uninet.uninet.MessagingScreenActivity;
import com.seniorproject.uninet.uninet.OtherUserProfileActivity;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Friends> mList;
    private Context mContext;
    private int type;

    public FriendListAdapter(Context mContext, List<Friends> mList, int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View friendsView;

        friendsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_template, parent, false);
        return new FriendList(friendsView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final Friends friendList = mList.get(position);

        if (friendList != null)
        {
            if (friendList.getFriendPicture() != null)
            {
                Bitmap bitmap = BitmapFactory.decodeByteArray(friendList.getFriendPicture(), 0, friendList.getFriendPicture().length);
                ((FriendList) holder).friendPicture.setImageBitmap(bitmap);
            }

            ((FriendList) holder).friendUserName.setText(friendList.getFriendName());


            if (type != 0)
            {
                ((FriendList) holder).newMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("friendAdapter", "buttonClicked");

                        String friendId = friendList.getFriendId();
                        String friendName = friendList.getFriendName();

                        String communicationId = findCommunication(LoggedInUser.UserId, friendName);
                        goToMessageScreen(friendId, communicationId, friendName);

                    }
                });

                ((FriendList) holder).friendPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent friendScreen = new Intent(mContext, OtherUserProfileActivity.class);
                        mContext.startActivity(friendScreen);

                    }
                });

                ((FriendList) holder).friendUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent friendScreen = new Intent(mContext, OtherUserProfileActivity.class);
                        mContext.startActivity(friendScreen);

                    }
                });
            }
            else
                {

                    ((FriendList) holder).newMessage.setVisibility(View.GONE);
                    ((FriendList) holder).friendUserName.setClickable(false);
                    ((FriendList) holder).friendPicture.setClickable(false);
                }



        }

    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public class FriendList extends RecyclerView.ViewHolder
    {
        private CircleImageView friendPicture;
        private TextView friendUserName;
        private Button newMessage;

        FriendList(View itemView) {
            super(itemView);
            friendPicture = itemView.findViewById(R.id.friend_list_profile_picture);
            friendUserName = itemView.findViewById(R.id.friend_list_friend_name);
            newMessage = itemView.findViewById(R.id.friend_list_message_friend);
        }


    }

    private  void goToMessageScreen(String friendID, String communicationID, String friendName)
    {
        Intent messageScreen = new Intent(mContext, MessagingScreenActivity.class);
        messageScreen.putExtra("FriendId", friendID);
        messageScreen.putExtra("FriendName", friendName);
        messageScreen.putExtra("FriendCommunicationId", communicationID);
        messageScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(messageScreen);
    }

    private String findCommunication(String whoIsTheUser, String userName)
    {
        String comId;

        List<Conversation> conversations = DatabaseMethods.GetConversations(whoIsTheUser);

        for (int i = 0; i < conversations.size(); i++)
        {
            if(conversations.get(i).name.equals(userName))
            {
                comId = conversations.get(i).conversationId;
                return comId;
            }
        }

        return comId = null;
    }


}
