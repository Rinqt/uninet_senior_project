package com.seniorproject.uninet.uninet.Adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.ConstructorClasses.UniPosts;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.OtherUserProfileActivity;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.seniorproject.uninet.uninet.ConstructorClasses.UniPosts.FRIEND_POST;
import static com.seniorproject.uninet.uninet.ConstructorClasses.UniPosts.USER_POST;

public class UniPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<UniPosts> mList;
    private Context mContext;
    private int mType;

    public UniPostAdapter(Context mContext, List<UniPosts> mList, int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.mType = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType)
    {
        final View uniPostView;

        switch (viewType)
        {
            // If post belongs to user.
            case FRIEND_POST:
                uniPostView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_uni_post_template, parent, false);
                return new FriendUniPost(uniPostView);

            // If logged in user sends a message to another user.
            case USER_POST:
                uniPostView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_uni_post_template, parent, false);
                return new UserUniPost(uniPostView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position)
    {
        final UniPosts uniPosts = mList.get(position);

        if (uniPosts != null)
        {
            switch (uniPosts.getType())
            {
                case FRIEND_POST:

                    // Set profile picture, if there is any
                    if (uniPosts.getProfilePicture() != null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(uniPosts.getProfilePicture(), 0, uniPosts.getProfilePicture().length);
                        ((FriendUniPost) holder).friendProfilePicture.setImageBitmap(bitmap);
                    }
                    else
                        ((FriendUniPost) holder).friendProfilePicture.setImageDrawable(null);

                    // Set post picture, if there is any.
                    if (uniPosts.getPostImage() != null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(uniPosts.getPostImage(), 0, uniPosts.getPostImage().length);
                        ((FriendUniPost) holder).friendUniPostImageView.setImageBitmap(bitmap);
                    }
                    else
                        ((FriendUniPost) holder).friendUniPostImageView.setImageDrawable(null);


                    ((FriendUniPost) holder).friendUserName.setText(uniPosts.getUserName());
                    ((FriendUniPost) holder).friendUniPostTime.setText(uniPosts.getTimeStamp());
                    ((FriendUniPost) holder).friendUniPostDescription.setText(uniPosts.getDescription());

                    ((FriendUniPost) holder).friendUniPostContainer.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        @Override
                        public boolean onLongClick(View v)
                        {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                            alertDialog.setItems(R.array.uni_post_settings_other_user, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int whichOption) {

                                    switch (whichOption)
                                    {
                                        case 0:
                                            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText(mContext.getString(R.string.post_copied), uniPosts.getDescription());
                                            assert clipboard != null;
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(mContext, R.string.uni_message_copied, Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            });
                            alertDialog.show();
                            return false;
                        }
                    });
                    if (mType != 1)
                    {
                        ((FriendUniPost) holder).friendUserName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToProfilePage(uniPosts.getUserID());
                            }
                        });

                        ((FriendUniPost) holder).friendProfilePicture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToProfilePage(uniPosts.getUserID());
                            }
                        });
                    }


                break;

                case USER_POST:

                    // Set profile picture
                    if (uniPosts.getProfilePicture() != null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(uniPosts.getProfilePicture(), 0, uniPosts.getProfilePicture().length);
                        ((UserUniPost) holder).userProfilePicture.setImageBitmap(bitmap);
                    }
                    else
                        ((UserUniPost) holder).userProfilePicture.setImageDrawable(null);

                    // Set post picture, if there is any.
                    if (uniPosts.getPostImage() != null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(uniPosts.getPostImage(), 0, uniPosts.getPostImage().length);
                        ((UserUniPost) holder).userUniPostImageView.setImageBitmap(bitmap);
                    }
                    else
                        ((UserUniPost) holder).userUniPostImageView.setImageDrawable(null);

                    ((UserUniPost) holder).userUserName.setText(uniPosts.getUserName());
                    ((UserUniPost) holder).userUniPostTime.setText(uniPosts.getTimeStamp());
                    ((UserUniPost) holder).userUniPostDescription.setText(uniPosts.getDescription());

                    ((UserUniPost) holder).userUniPostContainer.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        @Override
                        public boolean onLongClick(View v)
                        {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                            alertDialog.setItems(R.array.uni_post_settings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int whichOption) {

                                    switch (whichOption)
                                    {
                                        case 0:
                                            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText(mContext.getString(R.string.post_copied), uniPosts.getDescription());
                                            assert clipboard != null;
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(mContext, R.string.uni_message_copied, Toast.LENGTH_LONG).show();
                                            break;

                                        case 1:
                                            DatabaseMethods.RemovePost(uniPosts.getUniPostId());
                                            Toast.makeText(mContext, R.string.post_delete_successful, Toast.LENGTH_LONG).show();
                                            mList.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, mList.size());

                                            break;
                                    }
                                }
                            });
                            alertDialog.show();
                            return false;
                        }
                    });
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            UniPosts uniPost = mList.get(position);
            if (uniPost != null) {
                return uniPost.getType();
            }
        }
        return 0;
    }


    private void goToProfilePage(String friendID)
    {
        Intent profileScreen = new Intent(mContext, OtherUserProfileActivity.class);
        profileScreen.putExtra("UserID", friendID);
        mContext.startActivity(profileScreen);
    }

    public static class FriendUniPost extends RecyclerView.ViewHolder
    {
        CircleImageView friendProfilePicture;
        TextView friendUserName;
        TextView friendUniPostTime;
        TextView friendUniPostDescription;
        ImageView friendUniPostImageView;
        LinearLayout friendUniPostContainer;

        FriendUniPost(View itemView)
        {
            super(itemView);
            friendUserName = itemView.findViewById(R.id.user_name);
            friendUniPostDescription = itemView.findViewById(R.id.uni_post_description);
            friendUniPostTime = itemView.findViewById(R.id.time_stamp);
            friendProfilePicture = itemView.findViewById(R.id.profile_picture);
            friendUniPostImageView = itemView.findViewById(R.id.uni_post_image);
            friendUniPostContainer = itemView.findViewById(R.id.uni_post_container);
        }
    }



    public static class UserUniPost extends RecyclerView.ViewHolder
    {
        CircleImageView userProfilePicture;
        TextView userUserName;
        TextView userUniPostTime;
        TextView userUniPostDescription;
        ImageView userUniPostImageView;
        LinearLayout userUniPostContainer;

        UserUniPost(View itemView)
        {
            super(itemView);
            userUserName = itemView.findViewById(R.id.user_name);
            userUniPostDescription = itemView.findViewById(R.id.uni_post_description);
            userUniPostTime = itemView.findViewById(R.id.time_stamp);
            userProfilePicture = itemView.findViewById(R.id.profile_picture);
            userUniPostImageView = itemView.findViewById(R.id.uni_post_image);
            userUniPostContainer = itemView.findViewById(R.id.uni_post_container);
        }
    }
}
