package com.example.galluri

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.graphics.Paint

class MainActivity : AppCompatActivity() {

    private val email: String? = null
    private val password: String? = null
    private val confirmPassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val terms = findViewById<TextView>(R.id.terms_link)
        terms.paintFlags = terms.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}
