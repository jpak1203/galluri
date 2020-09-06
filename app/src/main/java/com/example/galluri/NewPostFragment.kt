package com.example.galluri

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewPostFragment : Fragment() {

    var tracker: SelectionTracker<String>? = null
    lateinit var adapter: NewPostImageAdapter

    lateinit var shareLink: TextView
    lateinit var newPostImage: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_new_post, container, false)
        val gridView = rootView.findViewById<RecyclerView>(R.id.photo_grid)

        shareLink = rootView.findViewById(R.id.share_link)
        newPostImage = rootView.findViewById(R.id.new_post_image)
        adapter = NewPostImageAdapter(fetchGalleryImages(activity))

        val layoutManager = GridLayoutManager(activity, 4)
        gridView.layoutManager = layoutManager
        gridView.adapter = adapter
        setupTracker(gridView)

        return rootView
    }

    override fun onStart() {
        super.onStart()

        clickShareLink()
    }

    private fun clickShareLink() {
        shareLink.setOnClickListener {
            val intent = Intent(context, NewPostInfo::class.java)
            startActivity(intent)
            this.activity!!.finish()
        }
    }

    private fun setupTracker(gridView: RecyclerView) {
        tracker = SelectionTracker.Builder<String>(
                "mySelection",
                gridView,
                NewPostItemKeyProvider(adapter),
                NewPostItemDetailsLookup(gridView),
                StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
                SelectionPredicates.createSelectSingleAnything()
        ).build()

        tracker?.addObserver(
                object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        Log.d("IMAGE", "image tracker " + tracker?.selection!!.first())
                        val image = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, tracker?.selection!!.first())
                        Picasso.get().load(image).into(newPostImage)
                    }
                })

        adapter.tracker = tracker
    }

    private fun fetchGalleryImages(context: FragmentActivity?): ArrayList<String>? {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val cursor = context?.contentResolver?.query(uri, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC")

        val ids = arrayListOf<String>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                ids.add(cursor.getString(columnIndex))
            }
        }
        cursor?.close()

        return ids
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
