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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d("Authentication", "Login page")

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        // Underline Terms & Privacy Policy
        val terms = findViewById<TextView>(R.id.terms_link)
        terms.paintFlags = terms.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        // Press Enter to Login
        val password = findViewById<TextView>(R.id.password_login_input)
        password.setOnKeyListener (View.OnKeyListener {_, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                loginClick()
                return@OnKeyListener true
            }
            false
        })

        // Press Login Button to Login
        val loginButton = findViewById<Button>(R.id.complete_login)
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

        val email = findViewById<TextView>(R.id.username_login_input)
        val emailText = email.text.toString()
        val password = findViewById<TextView>(R.id.password_login_input)
        val invalid = findViewById<TextView>(R.id.invalidLogin)

        auth.signInWithEmailAndPassword(emailText, password.text.toString()).addOnCompleteListener(this) { task ->
            Log.d("Authentication", this.toString())
            if (task.isSuccessful) {
                Log.d("Authentication", "Login success")

                //TODO: continue making fail-safe: username does not exist in db due to backend deletion but has savedState in application
                checkUsername(auth.currentUser!!.uid)

            } else {
                Log.d("Authentication", "Login fail")
                invalid.visibility = View.VISIBLE
                email.setBackgroundResource(R.drawable.credential_input_fail)
                password.setBackgroundResource(R.drawable.credential_input_fail)

            }
        }
    }

    private fun checkUsername(uid: String) {
        Log.d("Authentication", uid)

        val usernameCheck = db.getReference("Users").child(uid)

        usernameCheck.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("Authentication", p0.child("username").value.toString())
                if (p0.child("username").value == null) {
                    Log.d("Authentication", "Username missing")
                    var intent = Intent(this@Login, SetUsername::class.java)
                    intent.putExtra("id", auth.currentUser?.email)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("Authentication", "Username " + p0.child("username").value.toString() + " found")
                    var intent = Intent(this@Login, MainFeed::class.java)
                    intent.putExtra("id", auth.currentUser?.email)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("Authentication", p0.message)
            }
        })

    }


/*    private fun updateNewUser(uid: String, username: String, email: String)  {
        db.getReference("Usernames").setValue(username)
        db.getReference("Users").child(uid).child(username).setValue(username)
    }
*/
}
