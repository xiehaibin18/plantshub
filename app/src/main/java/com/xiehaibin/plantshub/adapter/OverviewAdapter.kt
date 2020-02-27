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
import com.xiehaibin.plantshub.model.data.LocationDataItem
import kotlinx.android.synthetic.main.overview_cell.view.*

class OverviewAdapter: ListAdapter<LocationDataItem, MyViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // 加载view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.overview_cell, parent, false)
        // 创建holder
        val holder = MyViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {  }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.overview_cell_shimmerLayout.apply {
            setShimmerColor(R.color.shimmerColor)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        holder.itemView.overview_cell_textView1.text = getItem(position).location_uid
        holder.itemView.overview_cell_textView2.text = getItem(position).location_name
        // 加载图片
//        Glide.with(holder.itemView)
//            .load(getItem(position).picture_url)
//            .placeholder(R.drawable.ic_image_gary_24dp)
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false.also { holder.itemView.overview_cell_shimmerLayout?.stopShimmerAnimation() }
//                }
//
//            })
//            .into(holder.itemView.overview_cell_imageView)
    }

    // 比较算法
    object DIFFCALLBACK: DiffUtil.ItemCallback<LocationDataItem>() {
        override fun areItemsTheSame(
            oldItem: LocationDataItem,
            newItem: LocationDataItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: LocationDataItem,
            newItem: LocationDataItem
        ): Boolean {
            return oldItem.location_uid == newItem.location_uid
        }

    }
}

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)