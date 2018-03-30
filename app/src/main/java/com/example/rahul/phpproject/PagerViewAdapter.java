package com.example.rahul.phpproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rahul on 29/3/18.
 */

class PagerViewAdapter  extends FragmentPagerAdapter{

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
            case 2:
                return new NotificationFragment();

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}