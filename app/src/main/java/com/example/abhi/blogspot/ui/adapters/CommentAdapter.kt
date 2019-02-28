package com.example.abhi.blogspot.ui.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.abhi.blogspot.R
import com.example.abhi.blogspot.model.Comment

class CommentAdapter(private val context: Context, private val commentList: MutableList<Comment?>)
    : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current: Comment? = commentList[position]

        holder.author.text = current!!.author!!.name
        holder.content.text = current.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_element, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val author = itemView.findViewById(R.id.comment_author) as TextView
        val content = itemView.findViewById(R.id.comment_content) as TextView
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun swap(commentList: MutableList<Comment?>) {
        this.commentList.clear()
        this.commentList.addAll(commentList)
        notifyDataSetChanged()
    }
}