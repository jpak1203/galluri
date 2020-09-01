package com.example.galluri

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SetUsername : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    lateinit var finishSignupButton: Button
    lateinit var usernameTextView: TextView
    lateinit var invalidUsernameTextView: TextView
    lateinit var invalidCharMinTextView: TextView
    lateinit var invalidCharMaxTextView: TextView
    lateinit var invalidCharRestrictionTextView: TextView
    lateinit var backToSignupLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_username_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        finishSignupButton = findViewById(R.id.complete_username_signup)
        usernameTextView = findViewById(R.id.set_username_input)
        invalidUsernameTextView = findViewById(R.id.invalid_username)
        invalidCharMinTextView = findViewById(R.id.character_min)
        invalidCharMaxTextView = findViewById(R.id.character_max)
        backToSignupLink = findViewById(R.id.back_to_signup)
        invalidCharRestrictionTextView = findViewById(R.id.character_restriction)
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        val email = user?.email.toString()

        usernameEnter(user, email)
        clickFinish(user, email)
        usernameCheck()
        backToSignupClick()
    }

    private fun usernameEnter(user: FirebaseUser?, email: String) {
        usernameTextView.setOnKeyListener (View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                finishUsernameSetup(user, email)
                return@OnKeyListener true
            }
            false
        })
    }

    private fun clickFinish(user: FirebaseUser?, email: String) {
        finishSignupButton.setOnClickListener {
            finishUsernameSetup(user, email)
        }
    }

    private fun backToSignupClick() {
        backToSignupLink.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@SetUsername, Signup::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun finishUsernameSetup(user: FirebaseUser?, email: String) {
        val usernameString = usernameTextView.text.toString()
        usernameTextView.setText("")

        writeNewUser(user!!.uid, usernameString, email)
        val intent = Intent(this, MainFeed::class.java)
        intent.putExtra("id", auth.currentUser?.email)
        startActivity(intent)
        finish()
    }

    private fun usernameCheck() {
        val usernameRef = db.reference.child("Usernames")

        usernameTextView.addTextChangedListener(object: TextWatcher {
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
                            usernameTextView.setTextColor(Color.parseColor("#BD3535"))
                            invalidUsernameTextView.visibility = View.VISIBLE
                            finishSignupButton.isClickable = false
                            Log.d("Authentication", "Username matches user in db")
                        } else {
                            usernameValid = true
                            invalidUsernameTextView.visibility = View.GONE
                        }

                        if (s.toString().length < 5) {
                            usernameTextView.setTextColor(Color.parseColor("#BD3535"))
                            invalidCharMinTextView.visibility = View.VISIBLE
                            finishSignupButton.isClickable = false
                            Log.d("Authentication", "Username is under 5 characters")
                        } else {
                            usernameMin = true
                            invalidCharMinTextView.visibility = View.GONE
                        }

                        if (s.toString().length > 15) {
                            usernameTextView.setTextColor(Color.parseColor("#BD3535"))
                            invalidCharMaxTextView.visibility = View.VISIBLE
                            finishSignupButton.isClickable = false
                            Log.d("Authentication", "Username is over 15 characters")
                        } else {
                            usernameMax = true
                            invalidCharMaxTextView.visibility = View.GONE
                        }

                        if (s.toString().contains("/[-!\$%^&*()+|~=`{}\\[\\]:\";'<>?,\\/]/\n")) {
                            usernameTextView.setTextColor(Color.parseColor("#BD3535"))
                            invalidCharRestrictionTextView.visibility = View.VISIBLE
                            finishSignupButton.isClickable = false
                            Log.d("Authentication", "Username contains invalid special characters")
                        } else {
                            usernameSpecial = true
                            invalidCharRestrictionTextView.visibility = View.GONE
                        }

                        if (usernameValid && usernameMin && usernameMax && usernameSpecial) {
                            usernameTextView.setTextColor(R.color.credential_input_focus)
                            finishSignupButton.isClickable = true
                            Log.d("Authentication", "Username is available to use")
                        }

                    }
                })

            }
        })

    }

    private fun writeNewUser(uid: String, username: String, email: String) {
        val user = Signup.User(username, username, email)
        db.getReference("Usernames").child(username).setValue(uid)
        db.getReference("Users").child(uid).setValue(user)
    }
}
