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

class SearchViewModel :ViewModel() {

    private val searchedQuestionsLiveData = MutableLiveData<List<Item>>()

    fun getsearchedQuestions(question:String){
        RetrofitInstance.api.getSearchedQuestions(question).enqueue(object :Callback<QuestionsList>{
            override fun onResponse(call: Call<QuestionsList>, response: Response<QuestionsList>) {
                if(response.body()!=null){
                    searchedQuestionsLiveData.postValue(response.body()!!.items)
                }
            }

            override fun onFailure(call: Call<QuestionsList>, t: Throwable) {
                Log.d("SearchViewModel",t.message.toString())
            }
        })
    }

    fun observeSearchedQuestionsLiveData():LiveData<List<Item>>{
        return searchedQuestionsLiveData
    }
}