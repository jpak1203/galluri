package com.example.galluri

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.feed_post.view.*


class FeedAdapter : RecyclerView.Adapter<PostViewHolder>() {

    //todo: Database Implementation
    val colde_imgs = listOf(R.drawable.colde_with_dog, R.drawable.offonoff, R.drawable.colde, R.drawable.df82927956eb01f77db99f578609e595, R.drawable.download,
            R.drawable.fb_img_1538999289690, R.drawable.images)
    val colde_description = listOf("samna <3", "w/ 0channel", "a merry wavy new years~", "omw", "my new wave album dropped", "happy birthday", "your dog loves you ft. crush")
    val colde_dates = listOf("02.19.19", "02.01.19", "01.01.19", "11.25.18", "09.13.18", "05.10.18", "04.27.18")


    override fun getItemCount(): Int {
        return colde_imgs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val postRow = inflator.inflate(R.layout.feed_post, parent, false)
        return PostViewHolder(postRow)
    }

    override fun onBindViewHolder(holder: PostViewHolder, pos: Int) {

        val imgs = colde_imgs.get(pos)
        val descs = colde_description.get(pos)
        val dates = colde_dates.get(pos)

        holder.v.username_header.text = "wavycolde"
        holder.v.post_username.text = "wavycolde"
        holder.v.title_input.text = descs
        holder.v.post_date.text = dates
        holder.v.post_image.setImageResource(imgs)
        holder.v.propic_header.setImageResource(R.drawable.dda77bdc532ea07cfb4bb1698446e6a8653df0d5)
        holder.v.propic_description.setImageResource(R.drawable.dda77bdc532ea07cfb4bb1698446e6a8653df0d5)

        holder.v.title_input?.setOnClickListener {
            val intent = Intent(holder.itemView.context, PostDetails::class.java)
            intent.putExtra("username", "wavycolde")
            intent.putExtra("title", descs)
            intent.putExtra("date", dates)

            intent.putExtra("image", imgs)
            intent.putExtra("propic", R.drawable.dda77bdc532ea07cfb4bb1698446e6a8653df0d5)
            holder.itemView.context.startActivity(intent)
            Log.d("POST", holder.v.title_input.text.toString())
        }
    }
}

class PostViewHolder(val v: View): RecyclerView.ViewHolder(v) {

}