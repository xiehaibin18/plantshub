package com.xiehaibin.plantshub.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.model.data.DetailLocationItem
import com.xiehaibin.plantshub.view.fragment.DetailFragment
import kotlinx.android.synthetic.main.detail_about_cell.view.*
import org.jetbrains.anko.toast

class DetailLocationAdapter : ListAdapter<DetailLocationItem, DetailLocationViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailLocationViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_about_cell, parent, false)
        // 创建holder
        val holder = DetailLocationViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {
            if (CommonData.getInstance().getIsDialog()) {
                parent.context.toast("点击图片进入该页面才能查看哦")
            } else {
                // 以dialog形式打开
                CommonData.getInstance().setIsDialog(true)
                // 实例化dialog
                val newDialog = DetailFragment.newInstance()
                // 存放数据
                val info = Bundle()
                // 存放类型：0为植物，1为位置
                info.putInt("type", 1)
                // 存放id
                info.putInt("itemUid", getItem(holder.adapterPosition).location_uid)
                newDialog.setDetailFragmentData(info)
                //打开dialog
                newDialog.show(
                    (parent.context as AppCompatActivity).supportFragmentManager,
                    "DetailFragment"
                )
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: DetailLocationViewHolder, position: Int) {
        holder.itemView.detail_about_name.text = getItem(position).location_name
    }
    // 比较算法
    private object DIFFCALLBACK : DiffUtil.ItemCallback<DetailLocationItem>() {
        override fun areItemsTheSame(
            oldItem: DetailLocationItem,
            newItem: DetailLocationItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: DetailLocationItem,
            newItem: DetailLocationItem
        ): Boolean {
            return oldItem.location_uid == newItem.location_uid
        }

    }

}

class DetailLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)