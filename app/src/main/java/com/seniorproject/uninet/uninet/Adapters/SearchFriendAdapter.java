package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.ConstructorClasses.Friends;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

public class SearchFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Friends> mList;
    private Context mContext;

    public SearchFriendAdapter(Context mContext, List<Friends> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View friendListView;

        friendListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_search_template, parent, false);
        return new FriendSearch(friendListView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Friends friendList = mList.get(position);


        if (friendList != null) {
            ((FriendSearch) holder).friendPicture.setImageResource(R.mipmap.ic_launcher_round);
            ((FriendSearch) holder).friendUserName.setText(friendList.getFriendName());
            ((FriendSearch) holder).newMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("friendAdapter", "buttonClicked");

                    String friendId = friendList.getFriendId();
                    String friendName = friendList.getFriendName();


                }
            });

            ((FriendSearch) holder).addFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Arkadaş Ekle
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public class FriendSearch extends RecyclerView.ViewHolder {
        private ImageView friendPicture;
        private TextView friendUserName;
        private Button newMessage;
        private Button addFriend;

        FriendSearch(View itemView) {

            super(itemView);
            friendPicture = itemView.findViewById(R.id.friend_search_profile_picture);
            friendUserName = itemView.findViewById(R.id.friend_search_friend_name);
            newMessage = itemView.findViewById(R.id.friend_search_message_friend);
            addFriend = itemView.findViewById(R.id.friend_search_add_friend);
        }

    }
}
