package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.seniorproject.uninet.uninet.Adapters.ConversationAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.Conversation;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by kaany on 27.02.2018.
 */

public class MessagingScreenActivity extends AppCompatActivity {

    private String whoIsTheUser;
    private String conversationID;
    private ArrayList<UserConversations> messages;
    private Button sendMessage;
    private EditText messageBox;
    private RecyclerView chatScreen;

    InputMethodManager keyboardHider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);

        whoIsTheUser = LoggedInUser.UserId;
        conversationID = getIntent().getStringExtra("conversationId");
        messages = new ArrayList<>();

        // Declarations
        chatScreen = findViewById(R.id.message_list_recycler_view);
        sendMessage = findViewById(R.id.button_message_send);
        messageBox = findViewById(R.id.message_box);

        clearMessages();
        loadMessages();

        ConversationAdapter messageAdapter = new ConversationAdapter(this, messages);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        chatScreen.setLayoutManager(linearLayoutManager);
        chatScreen.setItemAnimator(new DefaultItemAnimator());
        //chatScreen.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        chatScreen.setAdapter(messageAdapter);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = messageBox.getText().toString();
                DatabaseMethods.SendMessageExistingConversation(conversationID, whoIsTheUser, userMessage);

                messageBox.getText().clear();
                clearMessages();
                loadMessages();

            }
        });

        findViewById(R.id.message_screen_main_constraint_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                keyboardHider.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                return true;
            }
        });

    }

    private void loadMessages()
    {
        List<Conversation> currentUserMessages = DatabaseMethods.GetConversations(whoIsTheUser);
        List<Message> message = DatabaseMethods.GetMessages(whoIsTheUser, conversationID);

        for (int k = 0; k < currentUserMessages.size(); k++)
        {
            for (int i = 0; i < message.size(); i++)
            {
                if(message.get(i).userId.equals(whoIsTheUser))
                {
                    messages.add(new UserConversations(whoIsTheUser, message.get(i).messageId, message.get(i).name, message.get(i).userMessage, message.get(i).messageDate, 1));
                }
                else
                    messages.add(new UserConversations(whoIsTheUser, message.get(i).messageId, message.get(i).name, message.get(i).userMessage, message.get(i).messageDate, 0));
            }

        }


    }

    public void clearMessages() {
        final int size = messages.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                messages.remove(0);
            }
        }
    }
}
