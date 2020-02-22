package com.xiehaibin.plantshub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.xiehaibin.plantshub.model.http.VolleySingleton

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _accString = MutableLiveData<String>()
    val accString: LiveData<String>
    get() = _accString

    private val baseUrl: String = "http://10.0.2.2:3000"
    private var url: String = "/api/test"

    //
    fun fetchData() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            baseUrl.plus(url),
            Response.Listener {
                _accString.value =  it
            },
            Response.ErrorListener {  }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }
}
