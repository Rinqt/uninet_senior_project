package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.UniPostAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.UniPosts;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;
import com.seniorproject.uninet.uninet.DatabaseClasses.PostPicture;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    Button profileButton;

    TextView userPhotosLabel;
    TextView userFriendsLabel;
    TextView userFollowsLabel;
    ImageView profilePhoto;

    private String whoIsTheUser;
    public RecyclerView userUniPostFeed;
    SwipeRefreshLayout swipeRefreshLayout;
    UniPostAdapter uniPostAdapter;
    ArrayList<UniPosts> userUniPosts;

    StoredUserInformation userInformation;



    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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


        // Declaration
        whoIsTheUser = userInformation.getUserId();
        profilePhoto = getActivity().findViewById(R.id.profile_fragment_profile_picture);
        profileButton = getActivity().findViewById(R.id.user_profile_button);
        userPhotosLabel = getActivity().findViewById(R.id.user_total_photos_label);
        userFriendsLabel = getActivity().findViewById(R.id.user_total_friends_label);
        userFollowsLabel = getActivity().findViewById(R.id.user_total_follows_label);

        swipeRefreshLayout = getActivity().findViewById(R.id.uni_post_swiper);
        userUniPostFeed = getActivity().findViewById(R.id.uni_post_list_view);


        UserListingInfo user = DatabaseMethods.GetUserNamePic(whoIsTheUser);

        if (user.smallProfilePicture != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.smallProfilePicture, 0, user.smallProfilePicture.length);
            profilePhoto.setImageBitmap(bitmap);
        }


        refreshInformation();
        addDataToList();

        uniPostAdapter = new UniPostAdapter(getContext(),  userUniPosts, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);

        userUniPostFeed.setLayoutManager(linearLayoutManager);
        userUniPostFeed.setItemAnimator(new DefaultItemAnimator());
        userUniPostFeed.setAdapter(uniPostAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable()
                {
                    @Override public void run()
                    {
                        refreshPosts();
                        refreshInformation();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });




        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilePage = new Intent(getContext(), ProfileInfoActivity.class);
                startActivity(profilePage);

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

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
        List<Post> posts = DatabaseMethods.GetPosts(whoIsTheUser);
        userUniPosts = new ArrayList<>();

        for (int i = posts.size() - 1 ; i >= 0; i--)
        {
            if (posts.get(i).userId.equals(whoIsTheUser))
            {
                List<PostPicture> picturesOfPost = DatabaseMethods.GetPostPictures(posts.get(i).postId);
                byte[] picturePost;
                if (!picturesOfPost.isEmpty())
                    picturePost = picturesOfPost.get(0).picture;
                else
                    picturePost = null;
                // TODO Post Image?
                userUniPosts.add(new UniPosts
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
        }
    }

    protected void refreshPosts()
    {
        swipeRefreshLayout.setRefreshing(true);

        addDataToList();

        uniPostAdapter = new UniPostAdapter(getContext(), userUniPosts,1 );
        userUniPostFeed.setAdapter(uniPostAdapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    protected void refreshInformation()
    {

        userInformation = new StoredUserInformation(Objects.requireNonNull(getContext()));

        String friendsLabel, photosLabel, followsLabel;

        friendsLabel = userInformation.getFriendsNumber();
        photosLabel = userInformation.getPhotosNumber();
        followsLabel = userInformation.getFollowsNumber();

        userPhotosLabel.setText(photosLabel);
        userFriendsLabel.setText(friendsLabel);
        userFollowsLabel.setText(followsLabel);
    }
}
