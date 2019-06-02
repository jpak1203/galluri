package com.example.galluri

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Underline Terms & Privacy Policy
        val terms = findViewById<TextView>(R.id.terms_link)
        terms.paintFlags = terms.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        // Press Enter to Login
        val password = findViewById<TextView>(R.id.password_input)
        password.setOnKeyListener (View.OnKeyListener {_, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                loginClick()
                return@OnKeyListener true
            }
            false
        })

        // Press Login Button to Login
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            loginClick()
        }

        // Move to SignUp Screen
        signupClick()
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

        val email = findViewById<TextView>(R.id.username_input)
        val password = findViewById<TextView>(R.id.password_input)
        val invalid = findViewById<TextView>(R.id.invalidLogin)

        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                var intent = Intent(this, MainFeed::class.java)
                intent.putExtra("id", auth.currentUser?.email)
                startActivity(intent)
                finish()
            } else {
                Log.d("ERROR", "Login fail")
                invalid.visibility = View.VISIBLE
                email.setBackgroundResource(R.drawable.credential_input_fail)
                password.setBackgroundResource(R.drawable.credential_input_fail)

            }
        }
    }
}
