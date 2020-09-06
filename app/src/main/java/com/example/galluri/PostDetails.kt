package com.example.galluri

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PostDetails : AppCompatActivity() {

    var x1: Float = 0.toFloat()
    var y1:Float = 0.toFloat()

    var x2: Float = 0.toFloat()
    var y2:Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        val username = intent.extras?.getString("username")
        val title = intent.extras?.getString("title")
        val date = intent.extras?.getString("date")
        val image = intent.extras?.getInt("image")
        val propic = intent.extras?.getInt("propic")

        findViewById<TextView>(R.id.username_header).setText(username)
        findViewById<TextView>(R.id.title_input).setText(title)
        findViewById<TextView>(R.id.post_date).setText(date)
        findViewById<TextView>(R.id.description_input).text = "test description"
        if (image != null) {
            findViewById<ImageView>(R.id.post_image).setImageResource(image)
        }
        if (propic != null) {
            findViewById<ImageView>(R.id.propic_header).setImageResource(propic)
        }

        val view = findViewById<ScrollView>(R.id.details_scroller)
        view.setOnTouchListener { _ , event -> onTouchEvent(event) }

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
                        val intent = Intent(this@PostDetails, MainFeed::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    } else {
                        // Swap without transition
                        val intent = Intent(this@PostDetails, MainFeed::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        return false
    }
}
