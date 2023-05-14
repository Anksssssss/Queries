package com.example.queries

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queries.Repository.MyRepository
import com.example.queries.ViewModel.MainViewModel
import com.example.queries.ViewModel.MainViewModelFactory
import com.example.queries.adapters.CustomAdapter
import com.example.queries.data.QuestionDatabase
import com.example.queries.databinding.ActivityMainBinding
import com.example.queries.utils.Constants.Companion.LINK
import com.example.queries.utils.Constants.Companion.QUESTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionDatabase = QuestionDatabase.getInstance(this)
        val repository = MyRepository(questionDatabase.questionsDao())
        val viewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        val itemList = mutableListOf<Any>()
        customAdapter = CustomAdapter()


        prepareRecyclerViewOfCustomAdapter()



            if (isInternetConnectionAvailable()) {

                mainViewModel.fetchAndCacheData()

                var items = repository.getCachedData()
                for ((index, item) in items.withIndex()) {
                    itemList.add(item)

                    if ((index + 1) % 5 == 0) {
                        // Add an ad item to the itemList
                        itemList.add("Ad") // Replace "Ad" with your ad item or model
                    }
                }
                customAdapter.setData(itemList)


            }
            else {
                var items = repository.getCachedData()
                for ((index, item) in items.withIndex()) {
                    itemList.add(item)

                    if ((index + 1) % 5 == 0) {
                        // Add an ad item to the itemList
                        itemList.add("Ad") // Replace "Ad" with your ad item or model
                    }
                }
                customAdapter.setData(itemList)


            }

        onItemClick()

        binding.searchButton.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            intent.putExtra(QUESTION,binding.etMainActivity.text.toString())
            startActivity(intent)
        }

    }

    private fun prepareRecyclerViewOfCustomAdapter() {
        binding.rvTrendingQuestions.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = customAdapter
        }
    }

        private fun onItemClick() {
            customAdapter.onItemClick = {
                val intent = Intent(this, QuestionPage::class.java)
                intent.putExtra(LINK, it.link)
                startActivity(intent)
            }
        }

    fun isInternetConnectionAvailable(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val networkCapabilities =
                connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }


}