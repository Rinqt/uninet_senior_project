package com.seniorproject.uninet.uninet.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seniorproject.uninet.uninet.R;

public class UniPostSettingsFragment extends Fragment {

    private static final String TAG = "UniPostSettingsFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View profileSettingsView = inflater.inflate(R.layout.fragment_uni_post_settings, container, false);










        return profileSettingsView;
    }
}
