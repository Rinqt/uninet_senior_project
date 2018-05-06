package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.UniPostAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.UniPosts;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;
import com.seniorproject.uninet.uninet.DatabaseClasses.PostPicture;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String whoIsTheUser;
    StoredUserInformation userInformation;
    private SwipeRefreshLayout swipeRefreshLayout;

    public RecyclerView uniPostFeed;
    UniPostAdapter uniPostAdapter;
    ArrayList<UniPosts> uniPosts;

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
        userInformation = new StoredUserInformation(getActivity());

        whoIsTheUser = userInformation.getUserId();

        // Declaration
        swipeRefreshLayout = getActivity().findViewById(R.id.feed_list_swiper);
        uniPostFeed = getActivity().findViewById(R.id.uni_post_feed_list);

        addDataToList();

        UniPostAdapter uniPostAdapter = new UniPostAdapter(getContext(), uniPosts, 0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);

        uniPostFeed.setLayoutManager(linearLayoutManager);
        uniPostFeed.setItemAnimator(new DefaultItemAnimator());
        uniPostFeed.setAdapter(uniPostAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override public void run() {

                refreshPosts();
                swipeRefreshLayout.setRefreshing(false);

            }
        }, 1500);

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
        List<Post> posts = DatabaseMethods.GetNewsFeed(whoIsTheUser);
        uniPosts = new ArrayList<>();

        if (posts.size() == 0)
        {
            Toast.makeText(getContext(), "No post found", Toast.LENGTH_LONG).show();
        }
        else
        {
            for (int i = posts.size() - 1; i >= 0;  i--)
            {
                if (posts.get(i).userId.equals(whoIsTheUser))
                {
                    // Means post belogns to logged in user. That's why TYPE = 1
                    List<PostPicture> picturesOfPost = DatabaseMethods.GetPostPictures(posts.get(i).postId);
                    byte[] picturePost = null;

                    if(!picturesOfPost.isEmpty())
                        picturePost = picturesOfPost.get(0).picture;
                    else
                        picturePost = null;

                    uniPosts.add(new UniPosts
                            (whoIsTheUser,
                            posts.get(i).postId,
                            posts.get(i).name,
                            posts.get(i).postDate,
                            posts.get(i).postText,
                            posts.get(i).location,
                            posts.get(i).smallProfilePicture,
                            picturePost,
                            1)
                    );
                }
                else
                {
                    // Means post belongs to friend of the logged in user. That's why TYPE = 0
                    List<PostPicture> picturesOfPost = DatabaseMethods.GetPostPictures(posts.get(i).postId);
                    byte[] picturePost = null;

                    if(!picturesOfPost.isEmpty())
                        picturePost = picturesOfPost.get(0).picture;
                    else
                        picturePost = null;

                    uniPosts.add(new UniPosts
                            (posts.get(i).userId,
                            posts.get(i).postId,
                            posts.get(i).name,
                            posts.get(i).postDate,
                            posts.get(i).postText,
                            posts.get(i).location,
                            posts.get(i).smallProfilePicture,
                            picturePost,
                            0)
                    );
                }
            }
        }
    }

    protected void refreshPosts()
    {
        swipeRefreshLayout.setRefreshing(true);
        uniPostFeed.removeAllViews();

        addDataToList();

        uniPostAdapter = new UniPostAdapter(getContext(), uniPosts, 0);
        uniPostFeed.setAdapter(uniPostAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }
}
