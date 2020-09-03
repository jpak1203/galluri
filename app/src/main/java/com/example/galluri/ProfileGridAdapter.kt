package com.example.galluri

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_cell.view.*


class ProfileGridAdapter() : RecyclerView.Adapter<ProfileGridViewHolder>() {

    //todo: Database Implementation
    val colde_imgs = listOf(R.drawable.colde_with_dog, R.drawable.offonoff, R.drawable.colde, R.drawable.df82927956eb01f77db99f578609e595, R.drawable.download,
            R.drawable.fb_img_1538999289690, R.drawable.images)

    override fun getItemCount(): Int {
        return colde_imgs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileGridViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val postCell = inflater.inflate(R.layout.grid_cell, parent, false)

        val picWidth = parent.context.resources.displayMetrics.widthPixels/3

        postCell.layoutParams.width = picWidth
        postCell.layoutParams.height = picWidth

        return ProfileGridViewHolder(postCell)
    }

    override fun onBindViewHolder(holder: ProfileGridViewHolder, pos: Int) {
        val imgs = colde_imgs[pos]

        Picasso.get().load(imgs).resize(512, 400).centerCrop().into(holder.v.post_grid_cell)
    }
}

class ProfileGridViewHolder(val v: View): RecyclerView.ViewHolder(v) {

}