package com.example.galluri

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainFeed : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_feed)

        viewPager = findViewById(R.id.pager)

        pagerAdapter = MainPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.setCurrentItem(1)
    }

}
