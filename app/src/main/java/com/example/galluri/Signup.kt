package com.example.galluri

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

class Signup : AppCompatActivity() {

    private val email: String? = null
    private val password: String? = null
    private val confirmPassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Underline Terms & Privacy Policy
        val terms = findViewById<TextView>(R.id.terms_link)
        terms.paintFlags = terms.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        loginClick()
    }

    private fun loginClick() {
        val loginLink = findViewById<TextView>(R.id.login_link)
        loginLink.setOnClickListener {
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

}
