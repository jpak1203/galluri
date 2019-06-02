package com.example.galluri

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.grid_cell.view.*


class GridAdapter : RecyclerView.Adapter<GridViewHolder>() {

    val colde_imgs = listOf(R.drawable.colde_with_dog, R.drawable.offonoff, R.drawable.colde, R.drawable.df82927956eb01f77db99f578609e595, R.drawable.download,
            R.drawable.fb_img_1538999289690, R.drawable.images)

    override fun getItemCount(): Int {
        return colde_imgs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val postCell = inflater.inflate(R.layout.grid_cell, parent, false)

        val picWidth = parent.context.resources.displayMetrics.widthPixels/3

        postCell.layoutParams.width = picWidth
        postCell.layoutParams.height = picWidth

        return GridViewHolder(postCell)
    }

    override fun onBindViewHolder(holder: GridViewHolder, pos: Int) {
        val imgs = colde_imgs.get(pos)

        holder.v.post_pic?.setImageResource(imgs)

    }
}

class GridViewHolder(val v: View): RecyclerView.ViewHolder(v) {

}