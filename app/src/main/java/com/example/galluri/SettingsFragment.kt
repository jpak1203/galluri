package com.example.galluri

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var logOutButton: Button

    //todo: Database Implementation
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_setting, container, false)
        logOutButton = rootView.findViewById(R.id.logout_button)

        return rootView
    }

    override fun onStart() {
        super.onStart()

        signOut()
    }

    private fun signOut() {
        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            var intent = Intent(context, Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            this.activity!!.finish()
        }
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
