package com.xiehaibin.plantshub.view.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.databinding.DetailFragmentBinding
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import org.jetbrains.anko.support.v4.toast

class DetailFragment : DialogFragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var detailFragmentData: Bundle
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.detail_fragment, container, false)
        val binding: DetailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        binding.data = viewModel
        binding.lifecycleOwner = activity
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (CommonData.getInstance().getIsDialog()) {
            CommonData.getInstance().setIsDialog(false)
            // dialog方式打开
            viewModel.getDetailViewData(
                detailFragmentData.getInt("type", 400),
                detailFragmentData.getInt("itemUid", 400)
            )
        } else {
        }
        // 加载图片
        Glide.with(this)
            .load("")
            .placeholder(R.drawable.ic_image_gary_24dp)
            .into(detail_picture)
        viewModel.picture.observe(this, Observer {
            detail_shimmerLayout.apply {
                setShimmerColor(0x55FFFFFF)
                setShimmerAngle(0)
                startShimmerAnimation()
            }
            Glide.with(this)
                .load(it)
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
                        return false.also { detail_shimmerLayout?.stopShimmerAnimation() }
                    }

                })
                .into(detail_picture)
        })
        // 收藏状态
        viewModel.isFavorite.observe(this, Observer {
            detail_star_button.isEnabled = true
            when (it) {
                0 -> {
                    detail_star_button.background = resources.getDrawable(R.drawable.ic_star_border_black_24dp)
                    detail_star_button.setOnClickListener {
                        detail_star_button.isEnabled = false
                        viewModel.userFavorite(
                            detailFragmentData.getInt("type", 400),
                            detailFragmentData.getInt("itemUid", 400)
                        )
                    }
                }
                1 -> {
                    detail_star_button.background = resources.getDrawable(R.drawable.ic_star_black_24dp)
                    detail_star_button.setOnClickListener {
                        detail_star_button.isEnabled = false
                        viewModel.userFavorite(
                            detailFragmentData.getInt("type", 400),
                            detailFragmentData.getInt("itemUid", 400)
                        )
                    }
                }
            }
        })
        // 报错信息
        viewModel.msg.observe(this, Observer {
            toast(it)
        })
    }

    fun setDetailFragmentData(value: Bundle) {
        detailFragmentData = value
    }

}
