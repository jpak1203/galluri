package com.example.galluri

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class UserProfileFragment : Fragment() {

    lateinit var gridViewButton: ImageButton
    lateinit var zineViewButton: ImageButton
    lateinit var starViewButton: ImageButton
    lateinit var subsViewButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val recyclerView =  rootView.findViewById<RecyclerView>(R.id.grid_layout)


        //todo: Buttons for other views
        //todo: ability to create new zine
        gridViewButton = rootView.findViewById(R.id.gridView)
        zineViewButton = rootView.findViewById(R.id.zineView)
        starViewButton = rootView.findViewById(R.id.starView)
        subsViewButton = rootView.findViewById(R.id.subsView)

        val buttonWidth = resources.displayMetrics.widthPixels/4

        gridViewButton.layoutParams.width = buttonWidth
        zineViewButton.layoutParams.width = buttonWidth
        starViewButton.layoutParams.width = buttonWidth
        subsViewButton.layoutParams.width = buttonWidth

        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ProfileGridAdapter()
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                UserProfileFragment().apply {
                }
    }
}
