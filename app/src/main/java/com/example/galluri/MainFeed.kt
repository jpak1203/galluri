package com.example.galluri

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class MainFeed : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Page", "onCreate triggered: MainFeed")
        setContentView(R.layout.activity_main_feed)

        viewPager = findViewById(R.id.main_feed_pager)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Page", "onStart triggered: MainFeed")

        pagerAdapter = MainPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 1
    }

}
