package com.example.galluri

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.EditText
import android.view.KeyEvent.KEYCODE_ENTER




class NewPostFragment : Fragment() {

    //todo: Database Implementation
    //todo: Upload picture and update placeholder
    //todo: Fix text-box appearance when creating title and description
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_new_post, container, false)

        val editText = rootView.findViewById<EditText>(R.id.description_text)
        editText.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {

                // if enter is pressed start calculating
                if (keyCode == KEYCODE_ENTER && event.getAction() === KeyEvent.ACTION_UP) {

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

                return false
            }
        })
        return rootView
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
