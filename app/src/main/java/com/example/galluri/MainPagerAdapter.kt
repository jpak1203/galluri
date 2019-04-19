package com.example.galluri

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        if (position == 1) return FeedFragment.newInstance()
        return FeedFragment.newInstance()
    }

    override fun getCount(): Int {
        return 3
    }
}