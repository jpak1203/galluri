package com.example.galluri

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        if (position == 0) return NewPostFragment.newInstance()
        else if (position == 1) return FeedFragment.newInstance()
        else if (position == 2) return UserProfileFragment.newInstance()
        else if (position == 3) return SettingFragment.newInstance()
        else return FeedFragment.newInstance()
    }

    override fun getCount(): Int {
        return 4
    }
}