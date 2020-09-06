package com.example.galluri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class NewPostInfo : AppCompatActivity() {

    lateinit var addAllureLink: TextView
    lateinit var titleInput: EditText
    lateinit var descriptionInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_info)
    }
}