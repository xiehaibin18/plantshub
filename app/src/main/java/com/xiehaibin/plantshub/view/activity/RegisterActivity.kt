package com.xiehaibin.plantshub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.databinding.ActivityLoginBinding
import com.xiehaibin.plantshub.databinding.ActivityRegisterBinding
import com.xiehaibin.plantshub.viewModel.DetailViewModel
import com.xiehaibin.plantshub.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
        // DataBing
        val binding: ActivityRegisterBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.data = viewModel
        binding.lifecycleOwner = this

        // 观察pwdcheckValue变化
        val pwdcheckValueObserver = Observer<String> {
            if (viewModel.passwordValue.value.toString() != it) {
                viewModel.pwdcheckErrorHint.value = this.getString(R.string.app_pwdcheck_textInputLayout_error)
            } else {
                viewModel.pwdcheckErrorHint.value = ""
            }
        }
        viewModel.pwdcheckValue.observe(this, pwdcheckValueObserver)

        // 观察passwordValue变化
        val passwordValueObserver = Observer<String> {
            viewModel.pwdcheckErrorHint.value = this.getString(R.string.app_pwdcheck_textInputLayout_error)
        }
        viewModel.passwordValue.observe(this, passwordValueObserver)

        // 观察accountErrorHint变化
        val accountErrorHintObserver = Observer<String> {
            register_account_textInputLayout.error = it
        }
        viewModel.accountErrorHint.observe(this, accountErrorHintObserver)

        // 观察passwordErrorHint变化
        val passwordErrorHintObserver = Observer<String> {
            register_password_textInputLayout.error = it
        }
        viewModel.passwordErrorHint.observe(this, passwordErrorHintObserver)

        // 观察pwdcheckErrorHint变化
        val pwdcheckErrorHintObserver = Observer<String> {
            register_pwdcheck_textInputLayout.error = it
        }
        viewModel.pwdcheckErrorHint.observe(this, pwdcheckErrorHintObserver)

        // 观察nicknameErrorHint变化
        val nicknameErrorHintObserver = Observer<String> {
            register_nickname_textInputLayout.error = it
        }
        viewModel.nicknameErrorHint.observe(this, nicknameErrorHintObserver)

        // 观察phoneErrorHint变化
        val phoneErrorHintObserver = Observer<String> {
            register_phone_textInputLayout.error = it
        }
        viewModel.phoneErrorHint.observe(this, phoneErrorHintObserver)

        // 观察submitButtonIsEnabled变化
        val submitButtonIsEnabledObserver = Observer<Boolean> {
            register_submit_button.isEnabled = it
        }
        viewModel.submitButtonIsEnabled.observe(this, submitButtonIsEnabledObserver)

        // 观察statusCode变化，0验证成功，1手机号码已注册，400客户端数据传输失败，500服务器端错误
        val statusCodeObserver = Observer<Int> {
            if (it == 0) {
                startActivity<MainActivity>()
                finish()
                LoginActivity.fa!!.finish()
            } else {
                viewModel.submitButtonIsEnabled.value = true
                toast(viewModel.message.value.toString())
            }
        }
        viewModel.status_code.observe(this,statusCodeObserver)

        // 返回按钮
        register_back_button.setOnClickListener {
            finish()
        }
    }
}
