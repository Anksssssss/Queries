package com.example.queries.ViewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.queries.Repository.MyRepository
import com.example.queries.api.RetrofitInstance
import com.example.queries.data.QuestionDatabase
import com.example.queries.data.QuestionItem
import com.example.queries.models.Item
import com.example.queries.models.QuestionsList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val repository: MyRepository) : ViewModel(){

     fun fetchAndCacheData(){
         viewModelScope.launch {
             repository.fetchDataAndCache()
         }
    }


}