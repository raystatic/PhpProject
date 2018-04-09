package com.example.rahul.phpproject.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rahul.phpproject.Fragments.AllUsersFragment;
import com.example.rahul.phpproject.Fragments.NotificationFragment;
import com.example.rahul.phpproject.Fragments.ProfileFragment;

/**
 * Created by rahul on 29/3/18.
 */

public class PagerViewAdapter  extends FragmentPagerAdapter{

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
            ProfileFragment profileFragment=new ProfileFragment();
            return profileFragment;
            case 1:
                return new AllUsersFragment();
//            case 2:
//                return new NotificationFragment();

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
