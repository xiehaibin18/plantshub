package com.xiehaibin.plantshub.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.UserFavoriteDataItem
import kotlinx.android.synthetic.main.user_favorite_cell.view.*

class UserFavoriteAdapter :
    ListAdapter<UserFavoriteDataItem, UserFavoriteViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavoriteViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_favorite_cell, parent, false)
        // 创建holder
        val holder = UserFavoriteViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {

        }

        return holder
    }

    override fun onBindViewHolder(holder: UserFavoriteViewHolder, position: Int) {
        holder.itemView.user_favorite_cell_shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        holder.itemView.user_favorite_cell_name_textView.text = getItem(position).name
        holder.itemView.user_favorite_cell_content_textView.text = getItem(position).content
        // 加载图片
        Glide.with(holder.itemView)
            .load(getItem(position).picture)
            .placeholder(R.drawable.ic_image_gary_24dp)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also { holder.itemView.user_favorite_cell_shimmerLayout?.stopShimmerAnimation() }
                }

            })
            .into(holder.itemView.user_favorite_cell_imageView)
    }

    // 比较算法
    private object DIFFCALLBACK : DiffUtil.ItemCallback<UserFavoriteDataItem>() {
        override fun areItemsTheSame(
            oldItem: UserFavoriteDataItem,
            newItem: UserFavoriteDataItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: UserFavoriteDataItem,
            newItem: UserFavoriteDataItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

}

class UserFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)