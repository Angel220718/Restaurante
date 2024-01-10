package com.example.reservacionrestaurant;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public RestaurantPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RestaurantFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }


}

