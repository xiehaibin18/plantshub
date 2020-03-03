package com.xiehaibin.plantshub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.DetailMessageItem
import kotlinx.android.synthetic.main.detail_message_cell.view.*

class DetailMessageAdapter : ListAdapter<DetailMessageItem, DetailMessageViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMessageViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_message_cell, parent, false)
        // 创建holder
        val holder = DetailMessageViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {}

        return holder
    }

    override fun onBindViewHolder(holder: DetailMessageViewHolder, position: Int) {
        holder.itemView.detail_message_cell_name.text = "${getItem(position).message_sender_name}说：@${getItem(position).message_receiver_name}"
        holder.itemView.detail_message_cell_like.text = getItem(position).message_like.toString()
        holder.itemView.detail_message_cell_content.text = getItem(position).message_content
    }
    // 比较算法
    private object DIFFCALLBACK : DiffUtil.ItemCallback<DetailMessageItem>() {
        override fun areItemsTheSame(
            oldItem: DetailMessageItem,
            newItem: DetailMessageItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: DetailMessageItem,
            newItem: DetailMessageItem
        ): Boolean {
            return oldItem.message_uid == newItem.message_uid
        }

    }

}

class DetailMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)