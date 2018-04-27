package com.seniorproject.uninet.uninet.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFrangmentList = new ArrayList<>();

    // To get everything about fragments:
    private final HashMap<Fragment, Integer> mFragments = new HashMap<>();
    private final HashMap<String, Integer> mFragmentNumbers = new HashMap<>();
    private final HashMap<Integer, String> mFragmentNames = new HashMap<>();


    public SettingsFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        return mFrangmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFrangmentList.size();
    }

    public void addFragmentToList(Fragment fragment, String fragmentName){
        mFrangmentList.add(fragment);
        mFragments.put(fragment, mFrangmentList.size()-1);
        mFragmentNumbers.put(fragmentName, mFrangmentList.size()-1);
        mFragmentNames.put(mFrangmentList.size()-1, fragmentName);
    }

    // Getters:

    /**
     * Returns the fragment named @param
     * @param fragmentNumber
     * @return Integer
     */
    public Integer getFragmentNumber(String fragmentNumber)
    {
        if (mFragmentNumbers.containsKey(fragmentNumber))
        {
            return  mFragmentNumbers.get(fragmentNumber);
        }
        else
            return null;
    }

    /**
     * Returns the fragment named @param
     * @param fragment
     * @return Integer
     */
    public Integer getFragmentNumber(Fragment fragment)
    {
        if (mFragmentNumbers.containsKey(fragment))
        {
            return  mFragmentNumbers.get(fragment);
        }
        else
            return null;
    }

    /**
     * Returns the fragment named @param
     * @param fragmentNumber
     * @return String
     */
    public String getFragmentName(Integer fragmentNumber)
    {
        if (mFragmentNames.containsKey(fragmentNumber))
        {
            return mFragmentNames.get(fragmentNumber);
        }
        else
            return null;
    }







}
