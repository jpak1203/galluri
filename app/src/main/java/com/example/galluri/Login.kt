package com.example.galluri

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    lateinit var emailTextView: TextView
    lateinit var passwordTextView: TextView
    lateinit var invalidTextView: TextView
    lateinit var loginButton: Button
    lateinit var termsLink: TextView
    lateinit var signUpLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Page", "onCreate triggered: Login")
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()

        passwordTextView = findViewById(R.id.password_login_input)
        loginButton = findViewById(R.id.complete_login)
        termsLink = findViewById(R.id.terms_link)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Page", "onStart triggered: Login")

        termsLink.paintFlags = termsLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        passwordSubmit()
        loginClick()
        signUpclick()
    }

    private fun loginClick() {
        loginButton.setOnClickListener {
            val authTask = auth.signInWithEmailAndPassword(emailTextView.text.toString(), passwordTextView.text.toString())
            authTask.addOnCompleteListener(this) { task ->
                Log.d("Authentication", this.toString())
                if (task.isSuccessful) {
                    Log.d("Authentication", "Login success")

                    //TODO: continue making fail-safe: username does not exist in db due to backend deletion but has savedState in application
                    checkUsername(auth.currentUser!!.uid)

                } else {
                    Log.d("Authentication", "Login fail")
                    invalidTextView.visibility = View.VISIBLE
                    emailTextView.setBackgroundResource(R.drawable.credential_input_fail)
                    passwordTextView.setBackgroundResource(R.drawable.credential_input_fail)
                }
            }
        }
    }

    private fun passwordSubmit() {
        passwordTextView.setOnKeyListener (View.OnKeyListener {_, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                loginClick()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun signUpclick() {
        signUpLink.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
            finish()
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
                    val intent = Intent(this@Login, SetUsername::class.java)
                    intent.putExtra("id", auth.currentUser?.email)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("Authentication", "Username " + p0.child("username").value.toString() + " found")
                    val intent = Intent(this@Login, MainFeed::class.java)
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
