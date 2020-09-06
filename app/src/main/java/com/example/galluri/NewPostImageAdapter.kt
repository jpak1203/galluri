package com.example.galluri

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_cell.view.*

class NewPostImageAdapter(private val images: ArrayList<String>?) : RecyclerView.Adapter<NewPostImageAdapter.NewPostGridHolder>() {

    var tracker: SelectionTracker<String>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewPostGridHolder {
        val inflater = LayoutInflater.from(parent.context)
        val postCell = inflater.inflate(R.layout.grid_cell, parent, false)

        val picWidth = parent.context.resources.displayMetrics.widthPixels/4

        postCell.layoutParams.width = picWidth
        postCell.layoutParams.height = picWidth

        return NewPostGridHolder(postCell)
    }

    override fun onBindViewHolder(holder: NewPostGridHolder, position: Int) {
        val imgs = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, images!![position])
        Log.d("IMAGE", "image uri: " + imgs)

        tracker?.let {
            holder.bind(it.isSelected(images[position]))
        }

        Picasso.get().load(imgs).resize(384, 300).centerCrop().into(holder.v.post_grid_cell)

    }

    override fun getItemCount(): Int {
        Log.d("IMAGE", "image size: " + images!!.size)
        return images.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItem(position: Int) = images!![position]

    fun getPosition(key: String) = images!!.indexOfFirst { it == key }

    inner class NewPostGridHolder(val v: View): RecyclerView.ViewHolder(v) {
        fun bind(isActivated: Boolean = false) {
            itemView.isActivated = isActivated
        }

        fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<String>() {
                    override fun getPosition(): Int = bindingAdapterPosition
                    override fun getSelectionKey(): String? = images!![bindingAdapterPosition]
                }
    }

}