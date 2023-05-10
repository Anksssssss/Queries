package com.example.queries.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.queries.api.RetrofitInstance
import com.example.queries.models.Item
import com.example.queries.models.QuestionsList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private var questionLiveData = MutableLiveData<List<Item>>()

     fun getTrendingQuestions(){
        RetrofitInstance.api.getTrendingQuestions().enqueue(object :Callback<QuestionsList>{
            override fun onResponse(call: Call<QuestionsList>, response: Response<QuestionsList>) {
                if(response.body()!=null){
                   questionLiveData.postValue(response.body()!!.items)
                }
                else return
            }

            override fun onFailure(call: Call<QuestionsList>, t: Throwable) {
               Log.d("MainViewModel",t.message.toString())
            }
        })
    }

    fun observeQuestionsLiveData(): LiveData<List<Item>>{
        return questionLiveData
    }
}