package com.xiehaibin.plantshub.view.fragment

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.adapter.DetailLocationAdapter
import com.xiehaibin.plantshub.adapter.DetailMessageAdapter
import com.xiehaibin.plantshub.adapter.DetailPlantsAdapter
import com.xiehaibin.plantshub.databinding.DetailFragmentBinding
import com.xiehaibin.plantshub.model.data.CommonData
import com.xiehaibin.plantshub.view.activity.MainActivity
import com.xiehaibin.plantshub.view.fragment.user.UserFavoriteFragment
import com.xiehaibin.plantshub.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.lang.Exception
import java.nio.channels.spi.SelectorProvider

class DetailFragment : androidx.fragment.app.DialogFragment() {

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
        var type: Int = 400
        if (CommonData.getInstance().getIsDialog()) {
            // dialog方式打开
            type = detailFragmentData.getInt("type", 400)
            viewModel.getDetailViewData(
                detailFragmentData.getInt("type", 400),
                detailFragmentData.getInt("itemUid", 400)
            )
            detail_picture.setOnClickListener {
                dismiss()
                CommonData.getInstance().setRouterData(detailFragmentData)
                CommonData.getInstance().setRouter(R.id.action_homeFragment_to_detailFragment)
                startActivity<MainActivity>()
            }
        } else {
            // 跳转全屏方式打开
            type = CommonData.getInstance().getRouterData().getInt("type", 400)
            when (CommonData.getInstance().getRouter()) {
                R.id.action_homeFragment_to_detailFragment -> {
                    CommonData.getInstance()
                        .setRouter(R.id.action_homeFragment_to_userLoggedFragment)
                }
                1001 -> {
                    CommonData.getInstance().setRouter(0)
                }
            }
            viewModel.getDetailViewData(
                CommonData.getInstance().getRouterData().getInt("type", 400),
                CommonData.getInstance().getRouterData().getInt("itemUid", 400)
            )
        }
        // 实例化 Adapter
        val detailMessageAdapter = DetailMessageAdapter()
        val detailLocationAdapter = DetailLocationAdapter()
        val detailPlantsAdapter = DetailPlantsAdapter()
        // recyclerView设置
        detail_message_recyclerView.apply {
            adapter = detailMessageAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        detail_distributions_recylerView.apply {
            adapter = if (type == 0) {
                detailLocationAdapter
            } else {
                detailPlantsAdapter
            }
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
        viewModel.message.observe(this, Observer {
            detailMessageAdapter.submitList(it)
            detail_swiperRefreshLayout.isRefreshing = false
        })
        viewModel.location.observe(this, Observer {
            detailLocationAdapter.submitList(it)
            detail_swiperRefreshLayout.isRefreshing = false
        })
        viewModel.plants.observe(this, Observer {
            detailPlantsAdapter.submitList(it)
            detail_swiperRefreshLayout.isRefreshing = false
        })
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
                    detail_star_button.background =
                        resources.getDrawable(R.drawable.ic_star_border_black_24dp)
                    detail_star_button.setOnClickListener {
                        detail_star_button.isEnabled = false
                        if (!CommonData.getInstance().getIsDialog()) {
                            detailFragmentData = CommonData.getInstance().getRouterData()
                        }
                        viewModel.userFavorite(
                            detailFragmentData.getInt("type", 400),
                            detailFragmentData.getInt("itemUid", 400)
                        )
                    }
                }
                1 -> {
                    detail_star_button.background =
                        resources.getDrawable(R.drawable.ic_star_black_24dp)
                    detail_star_button.setOnClickListener {
                        detail_star_button.isEnabled = false
                        if (!CommonData.getInstance().getIsDialog()) {
                            detailFragmentData = CommonData.getInstance().getRouterData()
                        }
                        viewModel.userFavorite(
                            detailFragmentData.getInt("type", 400),
                            detailFragmentData.getInt("itemUid", 400)
                        )
                    }
                }
            }
        })
        // 点赞
        detail_like_button.setOnClickListener {
            if (!CommonData.getInstance().getIsDialog()) {
                detailFragmentData = CommonData.getInstance().getRouterData()
            }
            viewModel.userLike(
                detailFragmentData.getInt("type", 400),
                detailFragmentData.getInt("itemUid", 400)
            )
            viewModel.getDetailViewData(
                detailFragmentData.getInt("type", 400),
                detailFragmentData.getInt("itemUid", 400)
            )
        }
        // 报错信息
        viewModel.msg.observe(this, Observer {
            toast(it)
        })
        detail_swiperRefreshLayout.setOnRefreshListener {
            detail_swiperRefreshLayout.isRefreshing = false
            if (!CommonData.getInstance().getIsDialog()) {
                detailFragmentData = CommonData.getInstance().getRouterData()
            }
            viewModel.getDetailViewData(
                detailFragmentData.getInt("type", 400),
                detailFragmentData.getInt("itemUid", 400)
            )
        }
        detail_message_add_button.setOnClickListener {
            if (!CommonData.getInstance().getIsDialog()) {
                detailFragmentData = CommonData.getInstance().getRouterData()
                val senderDialog =
                    com.xiehaibin.plantshub.view.fragment.DialogFragment.newInstance()
                val info = Bundle()
                info.putString(
                    "messageLocation",
                    detailFragmentData.getInt("itemUid", 400).toString()
                )
                info.putInt("type", detailFragmentData.getInt("type", 400))
                senderDialog.receiverInfo(info)
                senderDialog.show(
                    (context as AppCompatActivity).supportFragmentManager,
                    "DialogFragment"
                )
            } else {
                toast("点击图片进入该页面才能留言哦")
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        CommonData.getInstance().setIsDialog(false)
    }

    fun setDetailFragmentData(value: Bundle) {
        detailFragmentData = value
    }

}
