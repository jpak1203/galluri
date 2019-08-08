package com.example.galluri

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.EditText
import android.view.KeyEvent.KEYCODE_ENTER
import android.content.Intent
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.widget.Button
import android.widget.ImageButton
import android.app.Activity
import android.graphics.Bitmap
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent.getIntent
import android.net.Uri
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.widget.ImageView


class NewPostFragment : Fragment() {

    val PICK_IMAGE = 1
    //todo: Database Implementation
    //todo: Upload picture and update placeholder
    //todo: Fix text-box appearance when creating title and description
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_new_post, container, false)

        val editText = rootView.findViewById<EditText>(R.id.description_text)
        editText.setOnKeyListener { v, keyCode, event ->
            // if enter is pressed start calculating
            if (keyCode == KEYCODE_ENTER && event.action === KeyEvent.ACTION_UP) {

                // get EditText text
                val text = (v as EditText).text.toString()

                // find how many rows it contains
                val editTextRowCount = text.split("\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size

                Log.d("Row", editTextRowCount.toString())
                // user has input more than limited - lets do something
                // about that
                if (editTextRowCount >= 25) {

                    // find the last break
                    val lastBreakIndex = text.lastIndexOf("\n")

                    // compose new text
                    val newText = text.substring(0, lastBreakIndex)

                    // add new text - delete old one and append new one
                    // (append because I want the cursor to be at the end)
                    v.setText("")
                    v.append(newText)

                }
            }

            false
        }

        val addImage = rootView.findViewById<ImageButton>(R.id.add_image)

        addImage.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                val uri = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.context?.contentResolver, uri)
                val ob = BitmapDrawable(this.resources, bitmap)

                val addImage = view?.findViewById<ImageButton>(R.id.add_image)
                addImage?.background = ob
                addImage?.adjustViewBounds = true
                addImage?.scaleType = ImageView.ScaleType.CENTER_CROP
                addImage?.setImageResource(android.R.color.transparent)

                Log.d("Result", addImage.toString())
                Log.d("Result", uri.toString())
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewPostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                NewPostFragment().apply {
                }
    }
}
