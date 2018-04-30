package com.seniorproject.uninet.uninet;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.PostListAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;

import java.util.ArrayList;
import java.util.List;


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

    String whoIsTheUser;

    SwipeRefreshLayout swipeRefreshLayout;

    // unipost_list
    public ListView unipost_list;
    PostListAdapter postListAdapter;
    ArrayList<UniPosts> uniPosts;

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
        profileButton = getActivity().findViewById(R.id.user_profile_button);
        userPhotosLabel = getActivity().findViewById(R.id.user_total_photos_label);
        userFriendsLabel = getActivity().findViewById(R.id.user_total_friends_label);
        userFollowsLabel = getActivity().findViewById(R.id.user_total_follows_label);

        swipeRefreshLayout = getActivity().findViewById(R.id.uni_post_swiper);
        unipost_list = getActivity().findViewById(R.id.uni_post_list_view);

        refreshInformation();
        addDataToList();

        postListAdapter = new PostListAdapter(getContext().getApplicationContext(), 0, R.layout.edit_uni_post_template, uniPosts);
        unipost_list.setAdapter(postListAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG", "onRefresh called from SwipeRefreshLayout");
                refreshPosts();
                refreshInformation();
            }
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());


        //Postlar için LongPress Alert Dialog
        unipost_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.i("Long click check", "Item Index " + i);

                alertDialog.setItems(R.array.uni_post_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichOption) {
                        UniPosts selectedPost;

                        switch (whichOption)
                        {
                            case 0:
                                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                selectedPost = postListAdapter.getItem(i);
                                ClipData clip = ClipData.newPlainText(getString(R.string.post_copied), selectedPost.getDescription());
                                assert clipboard != null;
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(getContext(), R.string.post_copied, Toast.LENGTH_LONG).show();
                            case 1:
                                selectedPost = postListAdapter.getItem(i);
                                //Log.i("getItem(i)", "Item Index " + selectedPost.getUniPostId());

                                // TODO: Add success controller. [for both places]
                                DatabaseMethods.RemovePost(selectedPost.getUniPostId());
                                Toast.makeText(getContext(), R.string.post_delete_successful, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alertDialog.show();
                return false;
            }
        });

            // uniPostların olduğu list view refreshToSwipe özelliği ile çakışıyordu.
            // View ilk elemana ulaştığı zaman swipe yapılabilir kontrolü eklendi.
        unipost_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profilePage = new Intent(getContext(), ProfileInfoActivity.class);
                startActivity(profilePage);

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        List<Post> uniPost = DatabaseMethods.GetPosts(whoIsTheUser);
        uniPosts = new ArrayList<>();


        for (int i = uniPost.size() - 1 ; i >= 0; i--)
        {
            uniPosts.add(new UniPosts(whoIsTheUser, uniPost.get(i).postId,
                    uniPost.get(i).name,
                    uniPost.get(i).postDate,
                    uniPost.get(i).postText,
                    uniPost.get(i).location,
                    uniPost.get(i).smallProfilePicture,
                    uniPost.get(i).smallProfilePicture));
        }
    }

    protected void refreshPosts()
    {
        postListAdapter.notifyDataSetChanged();

        addDataToList();

        postListAdapter = new PostListAdapter(getContext().getApplicationContext(), 0, R.layout.edit_uni_post_template, uniPosts);
        unipost_list.setAdapter(postListAdapter);

        Toast.makeText(getContext(), R.string.refresh_successful, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    protected void refreshInformation()
    {

        userInformation = new StoredUserInformation(getContext());

        String friendsLabel, photosLabel, followsLabel;

        friendsLabel = userInformation.getFriendsNumber();
        photosLabel = userInformation.getPhotosNumber();
        followsLabel = userInformation.getFollowsNumber();

        userPhotosLabel.setText(photosLabel);
        userFriendsLabel.setText(friendsLabel);
        userFollowsLabel.setText(followsLabel);
    }


    // To check if we are at top of the UniPost List.
    private boolean isListAtTop() {
        return unipost_list.getChildCount() == 0 || unipost_list.getChildAt(0).getTop() == 0;
    }

}
