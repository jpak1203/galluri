package com.example.galluri

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth


    //todo: Database Implementation
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        auth = FirebaseAuth.getInstance()

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_setting, container, false)

        val logOut = rootView.findViewById<Button>(R.id.logout_button)
        logOut.setOnClickListener {
            signOut()
        }

        return rootView
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()

        var intent = Intent(context, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        this.activity!!.finish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                SettingsFragment().apply {
                }
    }
}
