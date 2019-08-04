package com.example.galluri

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SetUsername : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_username_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        val user = auth.currentUser
        val email = user?.email.toString()
        val finishSignup = findViewById<Button>(R.id.complete_username_signup)
        val username = findViewById<TextView>(R.id.set_username_input)

        usernameCheck()

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

    }


    private fun usernameCheck() {

        val username = findViewById<TextView>(R.id.set_username_input)
        val finishButton = findViewById<Button>(R.id.complete_username_signup)

        val usernameRef = db.reference.child("Usernames")

        username.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                usernameRef.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Log.d("Authentication", p0.message)
                    }

                    @SuppressLint("ResourceAsColor")
                    override fun onDataChange(p0: DataSnapshot) {
                        Log.d("Authentication", "Authentication Data Change triggered")

                        var usernameValid = false
                        var usernameMin = false
                        var usernameMax = false
                        var usernameSpecial = false

                        Log.d("Authentication", p0.child(s.toString()).value.toString())

                        if ((p0.child(s.toString()).value != null)) {
                            username.setTextColor(Color.parseColor("#BD3535"))
                            findViewById<TextView>(R.id.invalid_username).visibility = View.VISIBLE
                            finishButton.isClickable = false
                            Log.d("Authentication", "Username matches user in db")
                        } else {
                            usernameValid = true
                            findViewById<TextView>(R.id.invalid_username).visibility = View.GONE
                        }

                        if (s.toString().length < 5) {
                            username.setTextColor(Color.parseColor("#BD3535"))
                            findViewById<TextView>(R.id.character_min).visibility = View.VISIBLE
                            finishButton.isClickable = false
                            Log.d("Authentication", "Username is under 5 characters")
                        } else {
                            usernameMin = true
                            findViewById<TextView>(R.id.character_min).visibility = View.GONE
                        }

                        if (s.toString().length > 15) {
                            username.setTextColor(Color.parseColor("#BD3535"))
                            findViewById<TextView>(R.id.character_max).visibility = View.VISIBLE
                            finishButton.isClickable = false
                            Log.d("Authentication", "Username is over 15 characters")
                        } else {
                            usernameMax = true
                            findViewById<TextView>(R.id.character_max).visibility = View.GONE
                        }

                        if (s.toString().contains("/[-!\$%^&*()+|~=`{}\\[\\]:\";'<>?,\\/]/\n")) {
                            username.setTextColor(Color.parseColor("#BD3535"))
                            findViewById<TextView>(R.id.character_restriction).visibility = View.VISIBLE
                            finishButton.isClickable = false
                            Log.d("Authentication", "Username contains invalid special characters")
                        } else {
                            usernameSpecial = true
                            findViewById<TextView>(R.id.character_restriction).visibility = View.GONE
                        }

                        if (usernameValid && usernameMin && usernameMax && usernameSpecial) {
                            username.setTextColor(R.color.credential_input_focus)
                            finishButton.isClickable = true
                            Log.d("Authentication", "Username is available to use")
                        }

                    }
                })

            }
        })

    }

    private fun finishButton(user: FirebaseUser?, email: String) {
        val username = findViewById<TextView>(R.id.set_username_input)
        val usernameString = username.text.toString()
        username.setText("")

        writeNewUser(user!!.uid, usernameString, email)
        var intent = Intent(this, MainFeed::class.java)
        intent.putExtra("id", auth.currentUser?.email)
        startActivity(intent)
        finish()
    }

    private fun writeNewUser(uid: String, username: String, email: String) {
        val user = Signup.User(username, username, email)
        db.getReference("Usernames").child(username).setValue(uid)
        db.getReference("Users").child(uid).setValue(user)
    }
}
