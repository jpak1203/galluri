package com.example.galluri

import android.content.Intent
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Underline Terms & Privacy Policy
        val terms = findViewById<TextView>(R.id.terms_link)
        terms.paintFlags = terms.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        signupClick()
        loginClick()
    }

    private fun signupClick() {
        val signupLink = findViewById<TextView>(R.id.signup_link)
        signupLink.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginClick() {
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val intent = Intent(this, MainFeed::class.java)
            startActivity(intent)
            finish()
        }
    }
}
