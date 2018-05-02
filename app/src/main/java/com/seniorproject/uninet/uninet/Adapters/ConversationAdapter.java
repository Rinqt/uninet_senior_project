package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        final UserConversations message = mList.get(position);

        if (message != null)
        {
            switch (message.getType())
            {
                case RECEIVER:
                    ((OtherUserMessage) holder).senderProfilePicture.setImageResource(R.mipmap.ic_launcher_round);
                    ((OtherUserMessage) holder).senderMessage.setText(message.getUserMessage());
                    ((OtherUserMessage) holder).senderMessageTime.setText(message.getMessageDate());
                    ((OtherUserMessage) holder).receiver.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            return true;
                        }
                    });
                    break;

                case SENDER:
                    ((CurrentUserMessage) holder).currentUserMessage.setText(message.getUserMessage());
                    ((CurrentUserMessage) holder).currentUserMessageTime.setText(message.getMessageDate());
                    ((CurrentUserMessage) holder).sender.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //TODO aletbox ile kopyalama ve silme yaptır
                        DatabaseMethods.EraseMessage(message.getUserId() ,message.getMessageId());
                        Log.i("userid", ": " + message.getUserId());
                        Log.i("message", ": " + message.getMessageId());
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
        private ImageView senderProfilePicture;
        private TextView senderMessage;
        private TextView senderMessageTime;

        private ConstraintLayout receiver;

        OtherUserMessage(View itemView) {
            super(itemView);
            senderProfilePicture = itemView.findViewById(R.id.received_image_user_message_profile);
            senderMessage = itemView.findViewById(R.id.received_text_message_body);
            senderMessageTime = itemView.findViewById(R.id.received_text_message_time);
            receiver = itemView.findViewById(R.id.receive_message_container);
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
