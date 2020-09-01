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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Authentication", "OnCreate triggered")

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        val currentUser = auth.currentUser

        //TODO: if user is deleted, remove cached user
        
        // Check if currentUser exists
        if (currentUser != null) {

            val uidQuery = db.reference.child("Users")

            uidQuery.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    Log.d("Authentication", currentUser.uid)
                    if (p0.child(currentUser.uid).value != null) {
                        Log.d("Authentication", "User ${p0.child(currentUser.uid).child("username").value.toString()}: logging in")
                        var intent = Intent(this@Signup, MainFeed::class.java)
                        intent.putExtra("id", currentUser.email)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("Authentication", "User found: no username")
                        var intent = Intent(this@Signup, SetUsername::class.java)
                        intent.putExtra("id", currentUser.email)
                        startActivity(intent)
                        finish()
                    }
                }

            })
        } else {
            setContentView(R.layout.activity_signup)

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
    }

    private fun loginClick() {
        val loginLink = findViewById<TextView>(R.id.login_link)
        loginLink.setOnClickListener {
            val intent = Intent(this@Signup, Login::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun createAccount() {

        if (!validateForm()) {
            return
        }

        val email = findViewById<TextView>(R.id.email_signup_input).text.toString()
        val password = findViewById<TextView>(R.id.password_signup_input).text.toString()

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


    private fun validateForm(): Boolean {
        val email = findViewById<TextView>(R.id.email_signup_input)
        val password = findViewById<TextView>(R.id.password_signup_input)
        val confirm = findViewById<TextView>(R.id.confirmpassword_signup_input)

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

        if (password.text.toString() != confirm.text.toString() || TextUtils.isEmpty(confirm.text)) {
            confirm.setBackgroundResource(R.drawable.credential_input_fail)
            valid = false
        } else {
            confirm.error = null
        }


        return valid
    }


    private fun confirmPassword() {
        val password = findViewById<TextView>(R.id.password_signup_input)
        val confirm = findViewById<TextView>(R.id.confirmpassword_signup_input)

        val signupButton = findViewById<Button>(R.id.complete_signup)

        confirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @SuppressLint("ResourceAsColor")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != password.text.toString()) {
                    confirm.setTextColor(Color.parseColor("#da6161"))
                    signupButton.isClickable = false
                } else {
                    confirm.setTextColor(R.color.credential_input_focus)
                    signupButton.isClickable = true
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
