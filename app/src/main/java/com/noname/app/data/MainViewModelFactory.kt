package com.noname.app.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MainViewModelFactory<B>(private var context: Context, private val data: String, var Repository: B) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Context::class.java, String::class.java, MainRepository::class.java).newInstance(context, data, Repository)
    }
}