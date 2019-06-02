package com.example.galluri

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView

class UserProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val recyclerView =  rootView.findViewById<RecyclerView>(R.id.grid_layout)


        val gridView = rootView.findViewById<ImageButton>(R.id.gridView)
        val zineView = rootView.findViewById<ImageButton>(R.id.zineView)
        val starView = rootView.findViewById<ImageButton>(R.id.starView)
        val subsView = rootView.findViewById<ImageButton>(R.id.subsView)

        val buttonWidth = resources.displayMetrics.widthPixels/4

        gridView.layoutParams.width = buttonWidth
        zineView.layoutParams.width = buttonWidth
        starView.layoutParams.width = buttonWidth
        subsView.layoutParams.width = buttonWidth

        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = GridAdapter()
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
