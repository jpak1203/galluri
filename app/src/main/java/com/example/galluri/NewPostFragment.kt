package com.example.galluri

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*


class NewPostFragment : Fragment() {

    //global variables
    private val PICK_IMAGE = 1
    private var filePath: Uri? = null
    private var addImage: ImageButton? = null

    private var uploadButton: ImageButton? = null
    private var cancelButton: ImageButton? = null

    //Firebase
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    //todo: Database Implementation
    //todo: Upload picture and update placeholder
    //todo: Fix text-box appearance when creating title and description
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_new_post, container, false)

        // Text input for new post
        val editText = rootView.findViewById<EditText>(R.id.description_text)
        editText.setOnKeyListener { v, keyCode, event ->
            // if enter is pressed start calculating
            if (keyCode == KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

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

        // Image input for new post
        storage = FirebaseStorage.getInstance()
        storageReference = storage?.reference

        addImage = rootView.findViewById<ImageButton>(R.id.add_image)
        uploadButton = rootView.findViewById<ImageButton>(R.id.upload_button)
        cancelButton = rootView.findViewById<ImageButton>(R.id.cancel_button)

        addImage?.setOnClickListener {
            chooseImage()
        }

        uploadButton?.setOnClickListener {
            uploadImage()
        }

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            filePath = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                addImage?.background = null
                addImage?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }


            uploadButton?.visibility = ImageButton.VISIBLE
            cancelButton?.visibility = ImageButton.VISIBLE
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    private fun uploadImage() {

        if (filePath != null) {
            val ref = storageReference?.child("images/" + UUID.randomUUID().toString())

            //todo: add notification on upload progress
            ref?.putFile(filePath!!)
                    ?.addOnSuccessListener {
                    }
                    ?.addOnFailureListener { e ->
                    }
                    ?.addOnProgressListener { taskSnapshot ->
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
