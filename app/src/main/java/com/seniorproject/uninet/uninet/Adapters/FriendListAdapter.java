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
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.MessagingScreenActivity;
import com.seniorproject.uninet.uninet.OtherUserProfileActivity;
import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.StoredUserInformation;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Friends> mList;
    private Context mContext;
    private int type;

    private StoredUserInformation userInformation;

    public FriendListAdapter(Context mContext, List<Friends> mList, int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.type = type;

        userInformation = new StoredUserInformation(mContext);
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
                String loggedInUserID = userInformation.getUserId();
                final String friendId = friendList.getFriendId();
                final String friendName = friendList.getFriendName();
                final String communicationId = findCommunication(loggedInUserID, friendId);

                ((FriendList) holder).newMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("friendAdapter", "buttonClicked");
                        goToMessageScreen(friendId, communicationId, friendName);

                    }
                });

                ((FriendList) holder).friendPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToProfile(friendId);

                    }
                });

                ((FriendList) holder).friendUserName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToProfile(friendId);
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


    private void goToProfile(String friendID)
    {
        Intent profileScreen = new Intent(mContext, OtherUserProfileActivity.class);
        profileScreen.putExtra("UserID", friendID);
        mContext.startActivity(profileScreen);

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

    private String findCommunication(String whoIsTheUser, String friendUserName)
    {
        String comId = DatabaseMethods.CheckExistingConversation(whoIsTheUser, friendUserName);

        if (!comId.equals(""))
        {
            return comId;
        }

        return comId = null;
    }


}
