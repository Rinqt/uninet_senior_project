package com.seniorproject.uninet.uninet.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;
import com.seniorproject.uninet.uninet.MessagingScreenActivity;
import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.StoredUserInformation;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.seniorproject.uninet.uninet.ConstructorClasses.User.FRIEND;
import static com.seniorproject.uninet.uninet.ConstructorClasses.User.NOT_FRIEND;

public class UserSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mList;
    private Context mContext;

    private ArrayList<User> peopleArray;
    private ArrayList<UserListingInfo> userArray;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        StoredUserInformation storedUserInformation = new StoredUserInformation(mContext);
        final String whoIsTheUser = storedUserInformation.getUserId();

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

                    ((FriendResult) holder).blockFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            blockUser(whoIsTheUser, mList.get(position).getUserId());

                        }
                    });

                    ((FriendResult) holder).removeFriend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeUser(whoIsTheUser, mList.get(position).getUserId());
                        }
                    });

                    ((FriendResult) holder).messageFriend.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            String conversationId = findCommunication(whoIsTheUser, mList.get(position).getUserId());
                            goToMessageScreen(mList.get(position).getUserId(), conversationId, mList.get(position).getUserName());
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

        private Button  removeFriend;
        private Button  blockFriend;
        private Button  messageFriend;


        FriendResult(View userView)
        {
            super(userView);
            friendProfilePhoto = itemView.findViewById(R.id.already_friend_profile_photo);
            friendName = itemView.findViewById(R.id.already_friend_user_name);
            removeFriend = itemView.findViewById(R.id.already_friend_remove_friend_button);
            blockFriend = itemView.findViewById(R.id.already_friend_block_button);
            messageFriend = itemView.findViewById(R.id.already_friend_send_message);
        }
    }

    private class NotFriendResult extends RecyclerView.ViewHolder
    {
        private CircleImageView notFriendProfilePhoto;
        private TextView notFriendName;
        private TextView notFriendTitle;

        private Button  addNotFriend;
        private Button  followNotFriend;
        private Button  messageNotFriend;


        NotFriendResult(View userView)
        {
            super(userView);
            notFriendProfilePhoto = itemView.findViewById(R.id.not_friend_profile_photo);
            notFriendName = itemView.findViewById(R.id.not_friend_user_name);
            notFriendTitle = itemView.findViewById(R.id.not_friend_user_title);
            addNotFriend = itemView.findViewById(R.id.not_friend_student_add_friend);
            followNotFriend = itemView.findViewById(R.id.not_friend_student_follow);
            messageNotFriend = itemView.findViewById(R.id.not_friend_student_send_message);
        }
    }


    private void removeUser(final String whoIsTheUser, final String otherUserId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //builder.setTitle(getString(R.string.unsaved_changes_title));
        builder.setMessage(mContext.getString(R.string.description_friendship_removal));

        builder.setPositiveButton(mContext.getString(R.string.description_remove_friend), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Remove Friend
                DatabaseMethods.RemoveFriend(whoIsTheUser, otherUserId);
                dialog.dismiss();

            }
        });

        builder.setNegativeButton(mContext.getString(R.string.unsaved_changes_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Stay in the page
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void sendFriendRequest(final String whoIsTheUser, final String otherUserId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //builder.setTitle(getString(R.string.unsaved_changes_title));
        builder.setMessage(mContext.getString(R.string.description_friendship_request));

        builder.setPositiveButton(mContext.getString(R.string.description_add_friend), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Leave the page
                DatabaseMethods.InsertRelation(whoIsTheUser, otherUserId, "1", "1");
                dialog.dismiss();

            }
        });

        builder.setNegativeButton(mContext.getString(R.string.unsaved_changes_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Stay in the page
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void blockUser(final String whoIsTheUser, final String otherUserId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(mContext.getString(R.string.description_block));
        builder.setMessage(mContext.getString(R.string.description_block_request));

        builder.setPositiveButton(mContext.getString(R.string.unsaved_changes_yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                DatabaseMethods.InsertRelation(whoIsTheUser, otherUserId, "0", null);
                DatabaseMethods.RemoveFriendFollowUponBlock(whoIsTheUser, otherUserId);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(mContext.getString(R.string.unsaved_changes_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
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

    private String findCommunication(String whoIsTheUser, String friendId)
    {
        String comId = DatabaseMethods.CheckExistingConversation(whoIsTheUser, friendId);

        if (!comId.equals(""))
        {
            return comId;
        }

        return comId = null;
    }
}
