package com.example.galluri

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Page", "onCreate triggered: Signup")

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        //TODO: if user is deleted, remove cached user

        // Check if currentUser exists
        val currentUser = auth.currentUser
        if (currentUser != null) updateUI(currentUser)

        else {
            setContentView(R.layout.activity_signup)

            val emailTextView: TextView = findViewById(R.id.email_signup_input)
            val passwordTextView: TextView = findViewById(R.id.password_signup_input)
            val confirmTextView: TextView = findViewById(R.id.confirmpassword_signup_input)
            val emailSignUpButton: Button = findViewById(R.id.complete_signup)
            val loginLink: TextView = findViewById(R.id.login_link)
            val termsLink: TextView = findViewById(R.id.terms_link)

            emailSignUpButton.setTextColor(Color.parseColor("#BFB5B5B5"))
            emailSignUpButton.background.alpha = 150
            termsLink.paintFlags = termsLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            confirmPassword(emailTextView, passwordTextView, confirmTextView, emailSignUpButton)
            submitSignUp(emailTextView, passwordTextView, confirmTextView, emailSignUpButton)
            loginClick(loginLink)
        }
    }

    private fun updateUI(currentUser : FirebaseUser) {
        val uidQuery = db.reference.child("Users")
        uidQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("Authentication", currentUser.uid)
                if (p0.child(currentUser.uid).value != null) {
                    Log.d("Authentication", "User ${p0.child(currentUser.uid).child("username").value.toString()}: logging in")
                    val intent = Intent(this@Signup, MainFeed::class.java)
                    intent.putExtra("id", currentUser.email)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("Authentication", "User found: no username")
                    val intent = Intent(this@Signup, SetUsername::class.java)
                    intent.putExtra("id", currentUser.email)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }

    private fun submitSignUp(emailTextView: TextView, passwordTextView: TextView, confirmTextView: TextView, emailSignUpButton: Button) {
        emailSignUpButton.setOnClickListener {
            createAccount(emailTextView, passwordTextView, confirmTextView)
        }
    }

    private fun loginClick(loginLink: TextView) {
        loginLink.setOnClickListener {
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createAccount(emailTextView: TextView, passwordTextView: TextView, confirmTextView: TextView) {
        if (!validateForm(emailTextView, passwordTextView, confirmTextView)) return

        val email = emailTextView.text.toString()
        val password = passwordTextView.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("Authentication", "createUserWithEmail:success")
                val intent = Intent(this@Signup, SetUsername::class.java)
                startActivity(intent)
                finish()
            } else {
                // If sign in fails, display a message to the user.
                Log.w("Authentication", "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(emailTextView: TextView, passwordTextView: TextView, confirmTextView: TextView): Boolean {
        var valid = true

        if (TextUtils.isEmpty(emailTextView.text)) {
            emailTextView.setBackgroundResource(R.drawable.credential_input_fail)
            valid = false
        } else {
            emailTextView.error = null
        }

        if (TextUtils.isEmpty(passwordTextView.text)) {
            passwordTextView.setBackgroundResource(R.drawable.credential_input_fail)
            valid = false
        } else {
            passwordTextView.error = null
        }

        if (passwordTextView.text.toString() != confirmTextView.text.toString() || TextUtils.isEmpty(confirmTextView.text)) {
            confirmTextView.setBackgroundResource(R.drawable.credential_input_fail)
            valid = false
        } else {
            confirmTextView.error = null
        }


        return valid
    }


    private fun confirmPassword(emailTextView: TextView, passwordTextView: TextView, confirmTextView: TextView, emailSignUpButton: Button) {
        confirmTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != passwordTextView.text.toString()) {
                    confirmTextView.setTextColor(Color.parseColor("#da6161"))
                    emailSignUpButton.setTextColor(Color.parseColor("#BFB5B5B5"))
                    emailSignUpButton.background.alpha = 150
                    emailSignUpButton.isClickable = false
                } else {
                    confirmTextView.setTextColor(R.color.credential_input_focus)
                    emailSignUpButton.setTextColor(Color.parseColor("#E5E5E5"))
                    emailSignUpButton.background.alpha = 255
                    emailSignUpButton.isClickable = true
                }
            }
        })

        confirmTextView.setOnKeyListener (View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                createAccount(emailTextView, passwordTextView, confirmTextView)
                return@OnKeyListener true
            }
            false
        })
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
