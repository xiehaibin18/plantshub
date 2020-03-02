package com.xiehaibin.plantshub.adapter.dialog

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
import com.xiehaibin.plantshub.model.data.PictureRecognitionDataItem
import kotlinx.android.synthetic.main.picture_recognition_dialog_cell.view.*
import java.lang.String.format

class PictureRecognitionAdapter : ListAdapter<PictureRecognitionDataItem, PictureRecognitionViewHolder>(DIFFCALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PictureRecognitionViewHolder {
        // 加载view
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.picture_recognition_dialog_cell, parent, false)
        // 创建holder
        val holder = PictureRecognitionViewHolder(view)
        // 点击事件
        holder.itemView.setOnClickListener {

        }

        return holder
    }

    override fun onBindViewHolder(holder: PictureRecognitionViewHolder, position: Int) {
        holder.itemView.PR_cell_shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        holder.itemView.PR_cell_name.text = "名称：${getItem(position).name}"
        holder.itemView.PR_cell_score.text = "相似度：${getItem(position).score}".substring(0,7)
        if (getItem(position).baike_info != null) {
            holder.itemView.PR_cell_baike.text = getItem(position).baike_info!!.description
        }
        // 加载图片
        Glide.with(holder.itemView)
            .load(getItem(position).baike_info?.image_url)
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
                    return false.also { holder.itemView.PR_cell_shimmerLayout?.stopShimmerAnimation() }
                }

            })
            .into(holder.itemView.PR_cell_imageView)
    }
    // 比较算法
    private object DIFFCALLBACK : DiffUtil.ItemCallback<PictureRecognitionDataItem>() {
        override fun areItemsTheSame(
            oldItem: PictureRecognitionDataItem,
            newItem: PictureRecognitionDataItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PictureRecognitionDataItem,
            newItem: PictureRecognitionDataItem
        ): Boolean {
            return oldItem.score.equals(newItem.score)
        }

    }
}

class PictureRecognitionViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)