package com.example.galluri

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.grid_cell.view.*


class GridAdapter : RecyclerView.Adapter<GridViewHolder>() {


    override fun getItemCount(): Int {
        return 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val postCell = inflator.inflate(com.example.galluri.R.layout.grid_cell, parent, false)
        return GridViewHolder(postCell)
    }

    override fun onBindViewHolder(p0: GridViewHolder, p1: Int) {
        p0.v.info_text?.text = "asdf"
    }
}

class GridViewHolder(val v: View): RecyclerView.ViewHolder(v) {

}