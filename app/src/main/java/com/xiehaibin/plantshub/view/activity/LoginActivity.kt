package com.xiehaibin.plantshub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.xiehaibin.plantshub.R
import com.xiehaibin.plantshub.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.hintResource
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_sumbit_button.setOnClickListener {

            val accountRegex = Regex(this.getString(R.string.accountRegex))
            if (!accountRegex.matches(login_account_editText.text.toString())) {
                login_account_textInputLayout.error =
                    this.getString(R.string.login_account_textInputLayout_error)
            } else {
                login_account_textInputLayout.error = null
            }

            val passwordRegex = Regex(this.getString(R.string.passwordRegex))
            if (!passwordRegex.matches(login_password_editText.text.toString())) {
                login_password_textInputLayout.error =
                    this.getString(R.string.login_password_textInputLayout_error)
            } else {
                login_password_textInputLayout.error = null
            }

            if(login_account_textInputLayout.error === null && login_password_textInputLayout.error === null){
                toast("succ")
            }
        }
    }
}
