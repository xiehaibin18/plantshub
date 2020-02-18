package com.xiehaibin.plantshub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.DetailViewModel
import com.xiehaibin.plantshub.viewModel.UploadPlantsViewModel

class UploadPlantsActivity : AppCompatActivity() {

    private val viewModel: UploadPlantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_plants)
    }
}
