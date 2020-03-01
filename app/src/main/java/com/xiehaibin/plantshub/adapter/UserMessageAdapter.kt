package com.xiehaibin.plantshub.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.UserMessageDataItem
import com.xiehaibin.plantshub.view.fragment.DialogFragment
import com.xiehaibin.plantshub.view.fragment.OverviewFragment
import com.xiehaibin.plantshub.view.fragment.user.UserMessageFragment
import kotlinx.android.synthetic.main.user_message_cell.view.*
import org.jetbrains.anko.support.v4.fragmentTabHost
import org.jetbrains.anko.toast

class UserMessageAdapter : ListAdapter<UserMessageDataItem, UserMessageViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMessageViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_message_cell, parent, false)
        // 创建holder
        val holder = UserMessageViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {
            CommonData.getInstance().setIsDialog(true)
            val newFragment = OverviewFragment()
            newFragment.show(
                (parent.context as AppCompatActivity).supportFragmentManager,
                "OverviewFragment"
            )
        }
        holder.itemView.user_message_cell_button.setOnClickListener {
            val senderDialog = DialogFragment.newInstance()
            val info = Bundle()
            info.putString("name", getItem(holder.adapterPosition).name)
            info.putString("content", getItem(holder.adapterPosition).content)
            info.putString("senderUid", getItem(holder.adapterPosition).senderUid)
            senderDialog.receiverInfo(info)
            senderDialog.show(
                (parent.context as AppCompatActivity).supportFragmentManager,
                "DialogFragment"
            )
        }

        return holder
    }

    override fun onBindViewHolder(holder: UserMessageViewHolder, position: Int) {
        if (getItem(position).name == "系统通知") { holder.itemView.user_message_cell_button.isVisible = false }
        holder.itemView.user_message_cell_shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        holder.itemView.user_message_cell_nickname_textView.text = getItem(position).name
        holder.itemView.user_message_cell_time_textView.text = getItem(position).time
        holder.itemView.user_message_cell_textView_content.text = "内容：${getItem(position).content}"
        // 加载图片
        Glide.with(holder.itemView)
            .load(getItem(position).avatar)
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
                    return false.also { holder.itemView.user_message_cell_shimmerLayout?.stopShimmerAnimation() }
                }

            })
            .into(holder.itemView.user_message_cell_imageView)
    }

    // 比较算法
    private object DIFFCALLBACK : DiffUtil.ItemCallback<UserMessageDataItem>() {
        override fun areItemsTheSame(
            oldItem: UserMessageDataItem,
            newItem: UserMessageDataItem
        ): Boolean {
            return oldItem === newItem
        }


        override fun areContentsTheSame(
            oldItem: UserMessageDataItem,
            newItem: UserMessageDataItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

}

class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)