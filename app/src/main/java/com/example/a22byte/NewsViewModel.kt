package com.example.a22byte

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a22byte.data.Articles
import com.example.a22byte.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(): ViewModel() {
    val newsList = MutableLiveData<Articles>()

    private val repository = Repository()

    fun getNews(search: String, page: Int){
        viewModelScope.launch(Dispatchers.IO){
            val response = repository.getAllNews(search, page)
            newsList.postValue(response.body())
        }
    }
}