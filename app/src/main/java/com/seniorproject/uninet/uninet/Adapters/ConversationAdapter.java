package com.seniorproject.uninet.uninet.Adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.UserConversations;

import java.util.List;

import static com.seniorproject.uninet.uninet.UserConversations.RECEIVER;
import static com.seniorproject.uninet.uninet.UserConversations.SENDER;

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<UserConversations> mList;
    private Context mContext;



    public ConversationAdapter(Context context, List<UserConversations> list) {
        this.mContext = context;
        this.mList = list;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View messageView;

        switch (viewType)
        {
            // If user sends a message to logged in user.
            case RECEIVER:
                messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recieved_message_template, parent, false);
                return new OtherUserMessage(messageView);

            // If logged in user sends a message to another user.
            case SENDER:
                messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message_template, parent, false);
                return new CurrentUserMessage(messageView);
        }



        return null;
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        final UserConversations message = mList.get(position);

        if (message != null)
        {
            switch (message.getType())
            {
                case RECEIVER:
                    if (message.getFriendPicture()!= null)
                    {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(message.getFriendPicture(), 0, message.getFriendPicture().length);
                        ((OtherUserMessage) holder).friendProfilePicture.setImageBitmap(bitmap);
                    }

                    ((OtherUserMessage) holder).friendMessage.setText(message.getUserMessage());
                    ((OtherUserMessage) holder).friendMessageTime.setText(message.getMessageDate());

                    // Gelen mesaj için onClick Listner
                    ((OtherUserMessage) holder).friendMessageContainer.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                        @Override
                        public boolean onLongClick(View v) {
                            alertDialog.setItems((R.array.uni_message_settings_friend), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int whichOption) {

                                    switch (whichOption)
                                    {
                                        case 0:
                                            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText(mContext.getString(R.string.post_copied), message.getUserMessage() );
                                            assert clipboard != null;
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(mContext, R.string.uni_message_copied, Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            });
                            alertDialog.show();
                            return true;
                        }
                    });
                    break;

                case SENDER:

                    ((CurrentUserMessage) holder).currentUserMessage.setText(message.getUserMessage());
                    ((CurrentUserMessage) holder).currentUserMessageTime.setText(message.getMessageDate());

                    ((CurrentUserMessage) holder).sender.setOnLongClickListener(new View.OnLongClickListener()
                    {
                        // Giden Mesaj için OnClick Listener
                        @Override
                        public boolean onLongClick(View v)
                        {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                            alertDialog.setItems(R.array.uni_message_settings_user, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int whichOption) {

                                    switch (whichOption)
                                    {
                                        case 0:
                                            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText(mContext.getString(R.string.post_copied), message.getUserMessage() );
                                            assert clipboard != null;
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(mContext, R.string.uni_message_copied, Toast.LENGTH_LONG).show();
                                            break;

                                        case 1:
                                            Log.i("getItem(i)", "Item Index " + message.getMessageId());
                                            DatabaseMethods.EraseMessage(message.getUserId() ,message.getMessageId());
                                            mList.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, mList.size());

                                            // TODO: Add success controller. [for both places] Also check if post belongs to the user
                                            Toast.makeText(mContext, R.string.uni_message_delete_successful, Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            });
                            alertDialog.show();






                            return true;
                        }

                });
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
            UserConversations message = mList.get(position);
            if (message != null) {
                return message.getType();
            }
        }
        return 0;
    }



    // Holder Classes
    public static class OtherUserMessage extends RecyclerView.ViewHolder
    {
        private ImageView friendProfilePicture;
        private TextView friendMessage;
        private TextView friendMessageTime;

        private ConstraintLayout friendMessageContainer;

        OtherUserMessage(View itemView) {
            super(itemView);
            friendProfilePicture = itemView.findViewById(R.id.received_image_user_message_profile);
            friendMessage = itemView.findViewById(R.id.received_text_message_body);
            friendMessageTime = itemView.findViewById(R.id.received_text_message_time);
            friendMessageContainer = itemView.findViewById(R.id.receive_message_container);
        }
    }

    public static class CurrentUserMessage extends RecyclerView.ViewHolder{
        private ConstraintLayout sender;
        private TextView currentUserMessage;
        private TextView currentUserMessageTime;

        CurrentUserMessage(View itemView) {
            super(itemView);
            currentUserMessage = itemView.findViewById(R.id.sent_text_message_body);
            currentUserMessageTime = itemView.findViewById(R.id.sent_text_message_time);
            sender = itemView.findViewById(R.id.send_message_container);

        }
    }
}
