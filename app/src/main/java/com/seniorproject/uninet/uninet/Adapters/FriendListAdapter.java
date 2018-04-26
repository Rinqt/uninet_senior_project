package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Friends;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Friends> mList;
    private Context mContext;

    public FriendListAdapter(Context mContext, List<Friends> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
        Friends friendList = mList.get(position);

        if (friendList != null)
        {
            ((FriendList) holder).friendPicture.setImageResource(R.mipmap.ic_launcher_round);
            ((FriendList) holder).friendUserName.setText(friendList.getFriendName());
        }

    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public static class FriendList extends RecyclerView.ViewHolder
    {
        private ImageView friendPicture;
        private TextView friendUserName;

        FriendList(View itemView) {
            super(itemView);
            friendPicture = itemView.findViewById(R.id.friend_list_friend_profile_picture);
            friendUserName = itemView.findViewById(R.id.friend_name);
        }
    }
}
