package com.xiehaibin.plantshub.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.navigation.findNavController
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
import com.xiehaibin.plantshub.model.data.CommonData
import kotlinx.android.synthetic.main.study_cell.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert

class StudyAdapter : ListAdapter<AllPlantsDataItem, StudyAdapterHolder>(DIFFCALLBACK) {

    // 比较算法
    private object DIFFCALLBACK : DiffUtil.ItemCallback<AllPlantsDataItem>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyAdapterHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.study_cell, parent, false)
        // 创建holder
        val holder = StudyAdapterHolder(view)
        holder.itemView.study_cell_textView.isVisible = false
        holder.itemView.study_cell_textView2.isVisible = false
        holder.itemView.study_cell_button.setOnClickListener {
            if (holder.itemView.study_cell_input.text.toString() == getItem(holder.adapterPosition).plants_name) {
                holder.itemView.study_cell_input_layout.isVisible = false
                holder.itemView.study_cell_button.isVisible = false
                holder.itemView.study_cell_textView.isVisible = true
                holder.itemView.study_cell_textView2.isVisible = true
                parent.context.toast("回答正确")
            } else {
                parent.context.toast("猜错了呢，点击图片直接看答案")
            }
        }
        holder.itemView.setOnClickListener {
            holder.itemView.study_cell_input_layout.isVisible = false
            holder.itemView.study_cell_button.isVisible = false
            holder.itemView.study_cell_textView.isVisible = true
            holder.itemView.study_cell_textView2.isVisible = true
        }

        return holder
    }

    override fun onBindViewHolder(holder: StudyAdapterHolder, position: Int) {
        holder.itemView.study_cell_shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        holder.itemView.study_cell_textView.text = getItem(position).plants_name
        holder.itemView.study_cell_textView2.text = getItem(position).plants_introduction
        // 加载图片
        Glide.with(holder.itemView)
            .load("${CommonData.getInstance().baseUrl}${getItem(position).plants_picture}")
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
                    return false.also { holder.itemView.study_cell_shimmerLayout?.stopShimmerAnimation() }
                }

            })
            .into(holder.itemView.study_cell_imageView)
    }
}

class StudyAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)