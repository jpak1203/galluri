package com.example.galluri

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView


class PostDetails : AppCompatActivity() {

    var x1: Float = 0.toFloat()
    var y1:Float = 0.toFloat()

    var x2: Float = 0.toFloat()
    var y2:Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        var username = getIntent().getExtras().getString("username")
        var title = getIntent().getExtras().getString("title")
        var date = getIntent().getExtras().getString("date")
        var image = getIntent().getExtras().getInt("image")
        var propic = getIntent().getExtras().getInt("propic")

        findViewById<TextView>(R.id.username_details).setText(username)
        findViewById<TextView>(R.id.title_details).setText(title)
        findViewById<TextView>(R.id.date_details).setText(date)
        findViewById<TextView>(R.id.description_details).setText("test description")
        findViewById<ImageView>(R.id.post_img_details).setImageResource(image)
        findViewById<ImageView>(R.id.propic_details).setImageResource(propic)

        var view = findViewById<ScrollView>(R.id.details_scroller)
        view.setOnTouchListener { view, event -> onTouchEvent(event) }

    }


    override fun onTouchEvent(touchevent: MotionEvent): Boolean {
        when (touchevent.action) {
            // when user first touches the screen we get x and y coordinate
            MotionEvent.ACTION_DOWN -> {
                x1 = touchevent.x
            }
            MotionEvent.ACTION_UP -> {
                x2 = touchevent.x
                // if right to left sweep event on screen
                Log.d("swipe", "" + (x1-x2))
                if (x1 < x2 && (x2-x1) > 500) {
                    // Check if we're running on Android 5.0 or higher
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        var intent = Intent(this@PostDetails, MainFeed::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    } else {
                        // Swap without transition
                        var intent = Intent(this@PostDetails, MainFeed::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        return false
    }
}
