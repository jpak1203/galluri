package com.example.galluri

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        if (position == 0) return NewPostFragment.newInstance()
        else if (position == 1) return FeedFragment.newInstance()
        else if (position == 2) return UserProfileFragment.newInstance()
        else if (position == 3) return SettingsFragment.newInstance()
        else return FeedFragment.newInstance()
    }

    override fun getCount(): Int {
        return 4
    }
}