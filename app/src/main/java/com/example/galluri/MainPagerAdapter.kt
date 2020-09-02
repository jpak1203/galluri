package com.example.galluri

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewPostFragment.newInstance()
            1 -> FeedFragment.newInstance()
            2 -> UserProfileFragment.newInstance()
            3 -> SettingsFragment.newInstance()
            else -> FeedFragment.newInstance()
        }
    }
}