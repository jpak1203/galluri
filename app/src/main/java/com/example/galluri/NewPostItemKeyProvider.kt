package com.example.galluri

import androidx.recyclerview.selection.ItemKeyProvider

class NewPostItemKeyProvider(private val newPostAdapter: NewPostImageAdapter): ItemKeyProvider<String>(SCOPE_CACHED) {
    override fun getKey(position: Int): String = newPostAdapter.getItem(position)
    override fun getPosition(key: String): Int = newPostAdapter.getPosition(key)
}