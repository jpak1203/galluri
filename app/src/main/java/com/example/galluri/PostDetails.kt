package com.example.galluri

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class PostDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        var username = getIntent().getExtras().getString("username")
        var title = getIntent().getExtras().getString("title")
        var date = getIntent().getExtras().getString("date")
        //TODO: Images are too big. find a way to size down
        var image = getIntent().getExtras().getInt("image")
        var propic = getIntent().getExtras().getInt("propic")

        findViewById<TextView>(R.id.username).setText(username)
        findViewById<TextView>(R.id.title).setText(title)
        findViewById<TextView>(R.id.date).setText(date)
        findViewById<ImageView>(R.id.post_img2).setImageResource(image)
        findViewById<ImageView>(R.id.profile_img).setImageResource(propic)

    }
}
