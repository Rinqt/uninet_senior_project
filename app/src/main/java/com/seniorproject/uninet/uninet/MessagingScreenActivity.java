package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.ConversationAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.Conversation;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaany on 27.02.2018.
 */

public class MessagingScreenActivity extends AppCompatActivity {

    private String whoIsTheUser;
    private String conversationID;
    private String userName;
    private String friendUserId;
    private String friendUserName;
    private String friendConversationId;
    private ArrayList<UserConversations> messages;
    private EditText messageBox;
    InputMethodManager keyboardHider;

    byte[] photo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);

        StoredUserInformation userInformation = new StoredUserInformation(this);

        // Data from MessagingFragment
        conversationID = getIntent().getStringExtra("conversationId");
        userName = getIntent().getStringExtra("UserName");
        photo = getIntent().getByteArrayExtra("photo");

        // Data from FriendListScreen
        friendUserId = getIntent().getStringExtra("FriendId");
        friendConversationId = getIntent().getStringExtra("FriendCommunicationId");
        friendUserName = getIntent().getStringExtra("FriendName");

        // Declarations
        messages = new ArrayList<>();
        whoIsTheUser = userInformation.getUserId();
        final RecyclerView chatScreen = findViewById(R.id.message_list_recycler_view);
        Button sendMessage = findViewById(R.id.button_message_send);
        messageBox = findViewById(R.id.message_box);

        clearMessages();
        loadMessages();

        final ConversationAdapter messageAdapter = new ConversationAdapter(this, messages);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        // Scroll view to the last message:
        linearLayoutManager.setStackFromEnd(true);
        clearMessages();
        loadMessages();
        chatScreen.setLayoutManager(linearLayoutManager);
        chatScreen.setItemAnimator(new DefaultItemAnimator());
        chatScreen.setAdapter(messageAdapter);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Check if we have a conversation with the friend
                if(conversationChecker())
                {
                    sendMessageToConversation(chatScreen);
                }
                else
                    startNewConversation();

            }
        });

        chatScreen.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, final int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                linearLayoutManager.setStackFromEnd(true);
                chatScreen.setLayoutManager(linearLayoutManager);
            }
        });



    }

    private boolean conversationChecker()
    {
        List<Conversation> conversations = DatabaseMethods.GetConversations(whoIsTheUser);

        for (int i = 0; i < conversations.size(); i++)
        {
            if(conversations.get(i).name.equals(friendUserName) || conversations.get(i).name.equals(userName))
            {
                Log.d("ConversationChecker", "true");
                return true;
            }
        }

        return false;
    }

    private void loadMessages()
    {
        if(friendConversationId != null && !friendConversationId.isEmpty())
        {
            conversationID = friendConversationId;
        }

        if (photo == null)
        {
            photo = DatabaseMethods.GetProfileInfoStudent(friendUserId).smallProfilePicture;
        }

        List<Message> message = DatabaseMethods.GetMessages(whoIsTheUser, conversationID);

        if (message.size() == 0)
        {
            Toast.makeText(this, "No message found. Start a conversation", Toast.LENGTH_LONG).show();
        }
        else
        {
            for (int k = 0; k < message.size(); k++)
            {
                byte[] smallProfilePicture =  photo;

                if(message.get(k).userId.equals(whoIsTheUser))
                {
                    messages.add(new UserConversations(whoIsTheUser, message.get(k).messageId, message.get(k).name, message.get(k).userMessage, message.get(k).messageDate, smallProfilePicture,1));
                }
                else
                    messages.add(new UserConversations(whoIsTheUser, message.get(k).messageId, message.get(k).name, message.get(k).userMessage, message.get(k).messageDate, smallProfilePicture, 0));
            }
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Set Activity Name as other user name
        if(friendUserName != null && !friendUserName.isEmpty())
        {
            userName = friendUserName;
            setTitle(userName);
        }
        else
            setTitle(userName);
    }

    private void clearMessages() {
        final int size = messages.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                messages.remove(0);
            }
        }
    }

    private void sendMessageToConversation(RecyclerView recyclerView)
    {
        String userMessage = messageBox.getText().toString();
        DatabaseMethods.SendMessageExistingConversation(conversationID, whoIsTheUser, userMessage);
        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

        Log.i("SEND", "Message send to the conversation");

        messageBox.getText().clear();

        // Scroll view to the last message:
        clearMessages();
        loadMessages();

        final ConversationAdapter messageAdapter = new ConversationAdapter(this, messages);
        final RecyclerView chatScreen = findViewById(R.id.message_list_recycler_view);

        chatScreen.setAdapter(messageAdapter);
    }

    private void startNewConversation()
    {
        String userMessage = messageBox.getText().toString();
        DatabaseMethods.SendMessage(friendUserId, whoIsTheUser, userMessage);
        Log.i("SEND", "New conversation created");

        messageBox.getText().clear();

        // Scroll view to the last message:
        clearMessages();
        loadMessages();

        final ConversationAdapter messageAdapter = new ConversationAdapter(this, messages);
        final RecyclerView chatScreen = findViewById(R.id.message_list_recycler_view);

        chatScreen.setAdapter(messageAdapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
