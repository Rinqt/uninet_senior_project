package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.MessagesListAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.Messages;
import com.seniorproject.uninet.uninet.DatabaseClasses.Conversation;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {

    Button newMessageButton;

    SwipeRefreshLayout swipeRefreshLayout;
    String whoIsTheUser;
    StoredUserInformation userInformation;

    // messages_list
    private ListView messagesList;
    private MessagesListAdapter messagesListAdapter;
    private ArrayList<Messages> messages;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Declaration
        userInformation = new StoredUserInformation(getActivity());
        whoIsTheUser = userInformation.getUserId();

        swipeRefreshLayout = getActivity().findViewById(R.id.messages_swiper);
        messagesList = getActivity().findViewById(R.id.messages_list);
        newMessageButton = getActivity().findViewById(R.id.new_message_button);


        addDataToList();


        messagesListAdapter = new MessagesListAdapter(getContext().getApplicationContext(), R.layout.messages_list_template, messages);
        messagesList.setAdapter(messagesListAdapter);


        // uniPostların olduğu list view refreshToSwipe özelliği ile çakışıyordu.
        // View ilk elemana ulaştığı zaman swipe yapılabilir kontrolü eklendi.
        messagesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0 && isListAtTop()){
                    swipeRefreshLayout.setEnabled(true);
                }else{
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        newMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent friendListScreen = new Intent(getContext(), FriendsListActivity.class);
                startActivity(friendListScreen);
            }
        });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG", "[MessagesFragment]onRefresh called from SwipeRefreshLayout");
                refreshPosts();
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPosts();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void addDataToList()
    {
        List<Conversation> conversations = DatabaseMethods.GetConversations(whoIsTheUser);
        messages = new ArrayList<>();



        for (int i = 0; i < conversations.size(); i++)
        {
            messages.add(new Messages(conversations.get(i).name, conversations.get(i).userMessage, conversations.get(i).conversationId, conversations.get(i).smallProfilePicture));
        }
    }

    private void refreshPosts()
    {
        messagesListAdapter.notifyDataSetChanged();

        addDataToList();

        messagesListAdapter = new MessagesListAdapter(getContext().getApplicationContext(), R.layout.messages_list_template, messages);
        messagesList.setAdapter(messagesListAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    // To check if we are at top of the UniPost List.
    private boolean isListAtTop() {
        return messagesList.getChildCount() == 0 || messagesList.getChildAt(0).getTop() == 0;
    }


}
