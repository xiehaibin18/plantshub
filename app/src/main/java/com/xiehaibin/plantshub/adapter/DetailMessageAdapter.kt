package com.xiehaibin.plantshub.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.DetailMessageItem
import com.xiehaibin.plantshub.view.fragment.DetailFragment
import com.xiehaibin.plantshub.view.fragment.DialogFragment
import kotlinx.android.synthetic.main.detail_message_cell.view.*
import org.jetbrains.anko.toast

class DetailMessageAdapter : ListAdapter<DetailMessageItem, DetailMessageViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMessageViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_message_cell, parent, false)
        // 创建holder
        val holder = DetailMessageViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {
            if (CommonData.getInstance().getIsDialog()) {
                parent.context.toast("点击图片进入该页面才能查看哦")
            } else {
                val senderDialog = DialogFragment.newInstance()
                val info = Bundle()
                info.putString("name", getItem(holder.adapterPosition).message_receiver_name)
                info.putString("content", getItem(holder.adapterPosition).message_content)
                info.putString("senderUid", getItem(holder.adapterPosition).message_sender_uid)
                info.putString("messageId", getItem(holder.adapterPosition).message_uid)
                info.putString(
                    "messageLocation",
                    CommonData.getInstance().getRouterData().getInt("itemUid", 400).toString()
                )
                info.putInt("type", CommonData.getInstance().getRouterData().getInt("type", 400))
                senderDialog.receiverInfo(info)
                senderDialog.show(
                    (parent.context as AppCompatActivity).supportFragmentManager,
                    "DialogFragment"
                )
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: DetailMessageViewHolder, position: Int) {
        holder.itemView.detail_message_cell_name.text =
            "${getItem(position).message_sender_name}说：@${getItem(position).message_receiver_name}"
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