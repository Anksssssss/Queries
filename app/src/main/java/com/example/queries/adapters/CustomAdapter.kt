package com.example.queries.adapters

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.queries.R
import com.example.queries.data.QuestionItem
import com.example.queries.databinding.AdItemBinding
import com.example.queries.databinding.QuestionItemBinding
import com.example.queries.models.Item
import java.util.*

class CustomAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Define your regular item view type
    private val ITEM_TYPE = 0

    // Define your ad item view type
    private val AD_ITEM_TYPE = 1

    lateinit var onItemClick : ((QuestionItem)->Unit)

    private var itemList: List<Any> = emptyList()

    fun setData(dataList: List<Any>) {
        this.itemList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE -> {
                // Inflate your regular item layout and return the ViewHolder
                ItemViewHolder(QuestionItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent ,false
                ))
            }
            AD_ITEM_TYPE -> {
                AdViewHolder(AdItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false
                ))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                val item = itemList[position] as QuestionItem

                Glide.with(holder.itemView)
                    .load(item.profile_image)
                    .into(holder.bindingItem.imgOwner)

                holder.bindingItem.tvQuestion.text = item.title
                holder.bindingItem.tvViews.text = item.viewCount.toString()
                holder.bindingItem.tvAnswerCount.text = "Answers: "+item.answerCount.toString()
                holder.bindingItem.userName.text = "Name: "+item.displayName
                holder.bindingItem.userReputation.text = "Reputation: "+ item.reputation.toString()

                val timeZoneDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val timeZoneString = timeZoneDate.format(item.creationDate)
                holder.bindingItem.tvDate.text = timeZoneString

                var tag = ""
                var c = 1
                if(item.tags!=null){
                    for (t in item?.tags!!){
                        if(c>3){
                            break
                        }
                        tag += t+", "
                        c++
                    }
                    holder.bindingItem.tvTags.text = tag
                }


                holder.itemView.setOnClickListener {
                    onItemClick.invoke(item)
                }
            }
            // Skip binding for the ad item layout
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] is String && itemList[position] == "Ad") {
            AD_ITEM_TYPE
        } else {
            ITEM_TYPE
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Define your regular item view holder
    class ItemViewHolder(val bindingItem: QuestionItemBinding):RecyclerView.ViewHolder(bindingItem.root)

    // Define your ad item view holder
    class AdViewHolder(val bindingAd : AdItemBinding):RecyclerView.ViewHolder(bindingAd.root)
}