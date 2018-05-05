package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.ConstructorClasses.User;
import com.seniorproject.uninet.uninet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.seniorproject.uninet.uninet.ConstructorClasses.User.FRIEND;
import static com.seniorproject.uninet.uninet.ConstructorClasses.User.NOT_FRIEND;

public class UserSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mList;
    private Context mContext;
    ArrayList<User> peopleArray;

    public UserSearchAdapter(List<User> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View userView;

        switch (viewType)
        {
            case FRIEND:
                userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.already_friend_search_template, parent, false);
                return new FriendResult(userView);

            case NOT_FRIEND:
                userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.not_friend_search_template, parent, false);
                return new NotFriendResult(userView);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final User user = mList.get(position);

        if (user != null)
        {
            switch (user.getType())
            {
                case FRIEND:
                    if (user.getUserPhoto()!= null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getUserPhoto(), 0, user.getUserPhoto().length);
                        ((FriendResult) holder).friendProfilePhoto.setImageBitmap(bitmap);
                    }

                    ((FriendResult) holder).friendName.setText(user.getUserName());

                    //TODO ONCLİCK LISTENERLAR

                    ((FriendResult) holder).addFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    ((FriendResult) holder).followFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    ((FriendResult) holder).messageFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });


                    break;

                case NOT_FRIEND:

                    ((NotFriendResult) holder).notFriendName.setText(user.getUserName());


                    //TODO ONCLİCK LISTENERLAR
                    break;


            }
        }

    }

    public void myFilter(String name){

        name = name.toLowerCase(Locale.getDefault());
        mList.clear();
        if (name.length() == 0){
            mList.addAll(peopleArray);
        } else {
            for (User PL : peopleArray){
                if (PL.getUserName().toLowerCase(Locale.getDefault()).contains(name)){
                    peopleArray.add(PL);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            User user = mList.get(position);
            if (user != null) {
                return user.getType();
            }
        }
        return 0;
    }

    private class FriendResult extends RecyclerView.ViewHolder
    {
        private CircleImageView friendProfilePhoto;
        private TextView friendName;

        private Button  addFriend;
        private Button  followFriend;
        private Button  messageFriend;


        public FriendResult(View userView)
        {
            super(userView);
            friendProfilePhoto = itemView.findViewById(R.id.lecture_profile_photo);
            friendName = itemView.findViewById(R.id.search_activity_user_name);
            addFriend = itemView.findViewById(R.id.search_activity_lecture_add_friend);
            followFriend = itemView.findViewById(R.id.search_activity_lecture_follow);
            messageFriend = itemView.findViewById(R.id.search_activity_lecture_send_message);
        }
    }

    private class NotFriendResult extends RecyclerView.ViewHolder
    {
        private CircleImageView notFriendProfilePhoto;
        private TextView notFriendName;

        private Button  addNotFriend;
        private Button  followNotFriend;
        private Button  messageNotFriend;


        public NotFriendResult(View userView)
        {
            super(userView);
            notFriendProfilePhoto = itemView.findViewById(R.id.student_profile_photo);
            notFriendName = itemView.findViewById(R.id.search_activity_student_name);
            addNotFriend = itemView.findViewById(R.id.search_activity_student_add_friend);
            followNotFriend = itemView.findViewById(R.id.search_activity_student_follow);
            messageNotFriend = itemView.findViewById(R.id.search_activity_student_send_message);
        }
    }
}
