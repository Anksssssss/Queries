package com.example.queries.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.queries.Repository.MyRepository
import com.example.queries.data.QuestionDatabase

class MainViewModelFactory(private val repository: MyRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}