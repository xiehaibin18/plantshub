package com.xiehaibin.plantshub.view.fragment.dialog

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson

import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.adapter.dialog.PictureRecognitionAdapter
import com.xiehaibin.plantshub.model.data.PictureRecognitionData
import com.xiehaibin.plantshub.viewModel.dialog.PictureRecognitionDialogViewModel
import kotlinx.android.synthetic.main.picture_recognition_dialog_fragment.*

class PictureRecognitionDialog : DialogFragment() {

    companion object {
        fun newInstance() = PictureRecognitionDialog()
    }

    private lateinit var dialogData: Bundle

    private val viewModel: PictureRecognitionDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picture_recognition_dialog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 实例化Adapter
        val pictureRecognitionAdapter = PictureRecognitionAdapter()
        // recyclerView设置
        PR_dialog_recyclerView.apply {
            adapter = pictureRecognitionAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        val pictureRecognitionData = Gson().fromJson(dialogData.getString("data"), PictureRecognitionData::class.java).data!!.toList()
        pictureRecognitionAdapter.submitList(pictureRecognitionData)
    }
    fun setDialogData(value: Bundle) {
        dialogData = value
    }
}
