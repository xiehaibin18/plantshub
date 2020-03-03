package com.xiehaibin.plantshub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.DetailPlantsItem
import kotlinx.android.synthetic.main.detail_about_cell.view.*

class DetailPlantsAdapter : ListAdapter<DetailPlantsItem, DetailPlantsViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailPlantsViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_about_cell, parent, false)
        // 创建holder
        val holder = DetailPlantsViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {}

        return holder
    }

    override fun onBindViewHolder(holder: DetailPlantsViewHolder, position: Int) {
        holder.itemView.detail_about_name.text = getItem(position).plants_name
    }
    // 比较算法
    private object DIFFCALLBACK : DiffUtil.ItemCallback<DetailPlantsItem>() {
        override fun areItemsTheSame(
            oldItem: DetailPlantsItem,
            newItem: DetailPlantsItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: DetailPlantsItem,
            newItem: DetailPlantsItem
        ): Boolean {
            return oldItem.plants_uid == newItem.plants_uid
        }

    }

}

class DetailPlantsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)