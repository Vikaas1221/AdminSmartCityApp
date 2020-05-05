package com.example.administrationkarnal;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class adapterViewPager extends FragmentPagerAdapter
{

    public adapterViewPager(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment=null;
        switch (position)
        {
            case 0:
                fragment=new fragment_one();
                break;
            case 1:
                fragment=new fragment_two();
                break;

        }
        return fragment;


    }

    @Override
    public int getCount()
    {
        return 2; // number of fragments
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        if (position==0)
        {
            return "NEW COMPLAINTS";
        }
        else
        {
            return "RESOLVED COMLAINTS";
        }
    }
}

