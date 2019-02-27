package com.example.abhi.blogspot.ui.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.abhi.blogspot.R
import com.example.abhi.blogspot.model.Article
import com.squareup.picasso.Picasso

class ArticleAdapter(private val context: Context, private val articleList: MutableList<Article?>,
                     private val callback: CustomCallback)
    : RecyclerView.Adapter<ArticleAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.article_list_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current: Article? = articleList[position]

        holder.title.text = current?.title
        holder.author.text = current?.author?.name
        setImage(holder, current?.picture)

        //on article click
        holder.container.setOnClickListener {
            callback.onArticleClicked(current)
        }
    }

    private fun setImage(holder: ViewHolder, url: String?) {
        if (url == "") {
            holder.pic.setImageResource(R.drawable.default_profile_picture)
        } else {
            Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
                .into(holder.pic)
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun swap(articleList: MutableList<Article?>) {
        this.articleList.clear()
        this.articleList.addAll(articleList)
        notifyDataSetChanged()
    }

    //send article to activity on article list element click
    interface CustomCallback {
        fun onArticleClicked(current: Article?)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.article_title) as TextView
        val author = itemView.findViewById(R.id.article_author) as TextView
        val pic = itemView.findViewById(R.id.article_pic) as ImageView
        val container = itemView.findViewById(R.id.container) as CardView
    }
}