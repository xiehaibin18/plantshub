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
import com.xiehaibin.plantshub.model.data.AllPlantsDataItem
import kotlinx.android.synthetic.main.overview_cell.view.*

class OverviewAdapter: ListAdapter<AllPlantsDataItem, OverviewViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        // 加载view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.overview_cell, parent, false)
        // 创建holder
        val holder = OverviewViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {

        }

        return holder
    }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.itemView.overview_cell_shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        holder.itemView.overview_cell_name_textView.text = "植物名：${getItem(position).plants_name}"
        holder.itemView.overview_cell_textView2.text = getItem(position).plants_introduction
        holder.itemView.overview_cell_like.text = getItem(position).plants_like.toString()
        // 加载图片
        Glide.with(holder.itemView)
            .load(getItem(position).plants_picture)
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
                    return false.also { holder.itemView.overview_cell_shimmerLayout?.stopShimmerAnimation() }
                }

            })
            .into(holder.itemView.overview_cell_imageView)
    }

    // 比较算法
    private object DIFFCALLBACK: DiffUtil.ItemCallback<AllPlantsDataItem>() {
        override fun areItemsTheSame(
            oldItem: AllPlantsDataItem,
            newItem: AllPlantsDataItem
        ): Boolean {
            return oldItem === newItem
        }


        override fun areContentsTheSame(
            oldItem: AllPlantsDataItem,
            newItem: AllPlantsDataItem
        ): Boolean {
            return oldItem.plants_uid == newItem.plants_uid
        }
    }
}

class OverviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)