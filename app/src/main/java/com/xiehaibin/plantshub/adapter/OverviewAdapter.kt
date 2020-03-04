package com.xiehaibin.plantshub.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.xiehaibin.plantshub.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.overview_cell.view.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.IOException

class OverviewAdapter : ListAdapter<AllPlantsDataItem, OverviewViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.overview_cell, parent, false)
        // 创建holder
        val holder = OverviewViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {
            CommonData.getInstance().setIsDialog(false)
            val info = Bundle()
            // 存放类型：0为植物，1为位置
            info.putInt("type", getItem(holder.adapterPosition).type ?: 400)
            // 存放id
            info.putInt("itemUid", getItem(holder.adapterPosition).plants_uid)
            CommonData.getInstance().setRouterData(info)
            if (CommonData.getInstance().getRouter() == 1001){
                parent.findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
            } else {
                parent.findNavController().navigate(R.id.action_overviewFragment_to_detailFragment)
            }
        }
        holder.itemView.overview_cell_like_button.setOnClickListener {
            doAsync {
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("type", "PorLLike")
                    .add("itemType", getItem(holder.adapterPosition).type.toString())
                    .add("itemUid", getItem(holder.adapterPosition).plants_uid.toString())
                    .build()
                val request = Request.Builder()
                    .url("http://192.168.0.105:3000/api/UserAddData")
                    .post(formBody)
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        uiThread { parent.context.toast("点赞失败，$e") }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        uiThread {
                            parent.context.toast("点赞成功")
                            val text = holder.itemView.overview_cell_like.text.toString()
                            holder.itemView.overview_cell_like.text =
                                Integer.parseInt(text).plus(1).toString()
                        }
                    }

                })
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.itemView.overview_cell_shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        holder.itemView.overview_cell_name_textView.text = getItem(position).plants_name
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
}

class OverviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)