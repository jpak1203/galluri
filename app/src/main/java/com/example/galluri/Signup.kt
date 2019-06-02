package com.example.galluri

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        confirmPassword()

        val emailSignUpButton = findViewById<Button>(R.id.complete_signup)
        emailSignUpButton.setOnClickListener {
            createAccount()
        }

        // Underline Terms & Privacy Policy
        val terms = findViewById<TextView>(R.id.terms_link)
        terms.paintFlags = terms.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        loginClick()
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            var intent = Intent(this, MainFeed::class.java)
            intent.putExtra("id", currentUser.email)
            startActivity(intent)
            finish()
        }
    }

    private fun createAccount() {

        if (!validateForm()) {
            return
        }

        val email = findViewById<TextView>(R.id.email_input).text.toString()
        val password = findViewById<TextView>(R.id.password_input).text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("EmailPassword", "createUserWithEmail:success")
                val user = auth.currentUser
                setContentView(R.layout.set_username_signup)
                val finishSignup = findViewById<Button>(R.id.complete_signup)

                val username = findViewById<TextView>(R.id.username_input)
                username.setOnKeyListener (View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        finishButton(user, email)
                        return@OnKeyListener true
                    }
                    false
                })

                finishSignup.setOnClickListener {
                    finishButton(user, email)
                }

            } else {
                // If sign in fails, display a message to the user.
                Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        val email = findViewById<TextView>(R.id.email_input)
        val password = findViewById<TextView>(R.id.password_input)
        val confirm = findViewById<TextView>(R.id.confirmpassword_input)

        var valid = true

        if (TextUtils.isEmpty(email.text)) {
            email.setBackgroundResource(R.drawable.credential_input_fail)
            valid = false
        } else {
            email.error = null
        }

        if (TextUtils.isEmpty(password.text)) {
            password.setBackgroundResource(R.drawable.credential_input_fail)
            valid = false
        } else {
            password.error = null
        }

        if (password.text.toString() != confirm.text.toString()) {
            confirm.setBackgroundResource(R.drawable.credential_input_fail)
            valid = false
        } else {
            confirm.error = null
        }


        return valid
    }

    private fun confirmPassword() {
        val password = findViewById<TextView>(R.id.password_input)
        val confirm = findViewById<TextView>(R.id.confirmpassword_input)
        confirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != password.text.toString()) {
                    confirm.setTextColor(Color.parseColor("#da6161"))
                } else {
                    confirm.setTextColor(R.color.credential_input_focus)
                }
            }
        })

        confirm.setOnKeyListener (View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                createAccount()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun finishButton(user: FirebaseUser?, email: String) {
        val username = findViewById<TextView>(R.id.username_input).text.toString()
        writeNewUser(user!!.uid, username, email)
        var intent = Intent(this, MainFeed::class.java)
        intent.putExtra("id", auth.currentUser?.email)
        startActivity(intent)
        finish()
    }

    private fun loginClick() {
        val loginLink = findViewById<TextView>(R.id.login_link)
        loginLink.setOnClickListener {
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun writeNewUser(uid: String, username: String, email: String) {
        val user = User(username, username, email)
        db.getReference("Users").child(uid).setValue(user)
    }

    data class User (
            var username: String? = "",
            var displayname: String? = "",
            var email: String? = "",
            var description: String? = "",
            var propic: String? = "",
            var photos: List<String>? = listOf(),
            var feed: List<String>? = listOf(),
            var allures: List<String>? = listOf(),
            var dark: Boolean? = false
    )
}
