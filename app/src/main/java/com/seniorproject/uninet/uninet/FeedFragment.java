package com.seniorproject.uninet.uninet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // unipost_list_view
    private ListView unipost_feed;
    private ListViewAdapter listViewAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    final CharSequence[] unipostOptions = {"Copy UniPost Text"};



    // Declaration of the layout
    ImageView profilePhoto;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private OnFragmentInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
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

        swipeRefreshLayout = getActivity().findViewById(R.id.feed_list_swiper);


        unipost_feed = getActivity().findViewById(R.id.uni_post_feed_list);
        listViewAdapter = new ListViewAdapter(getActivity().getApplicationContext(), 1, LoggedInUser.UserId);
        unipost_feed.setAdapter(listViewAdapter);


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        //Postlar için LongPress Alert Dialog
        unipost_feed.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Long click check", "Item Index " + i);

                alertDialog.setItems(unipostOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichOption) {

                        switch (whichOption)
                        {
                            case 0:
                                Toast.makeText(getContext(), "Deneme", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alertDialog.show();
                return false;
            }
        });

        // uniPostların olduğu list view refreshToSwipe özelliği ile çakışıyordu.
        // View ilk elemana ulaştığı zaman swipe yapılabilir kontrolü eklendi.
        unipost_feed.setOnScrollListener(new AbsListView.OnScrollListener() {
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


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG", "onRefresh called from SwipeRefreshLayout");
                refreshPosts();
            }
        });





    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
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

    private void refreshPosts()
    {
        listViewAdapter.notifyDataSetChanged();
        unipost_feed.setAdapter(new ListViewAdapter(getActivity().getApplicationContext(), 1, LoggedInUser.UserId));

        Toast.makeText(getContext(), R.string.refresh_successful, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    // To check if we are at top of the UniPost List.
    private boolean isListAtTop()
    {
        if(unipost_feed.getChildCount() == 0) return true;
        return unipost_feed.getChildAt(0).getTop() == 0;
    }
    //-----------------------------

}
