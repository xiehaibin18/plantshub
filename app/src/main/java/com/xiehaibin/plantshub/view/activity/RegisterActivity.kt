package com.xiehaibin.plantshub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.DetailViewModel
import com.xiehaibin.plantshub.viewModel.RegisterViewModel
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}
