package com.example.queries.adapters

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.queries.databinding.QuestionItemBinding
import com.example.queries.models.Item
import java.util.*

class TrendingQuestionsAdapter: RecyclerView.Adapter<TrendingQuestionsAdapter.TrendingQuestionsViewHolder> (){

    inner class TrendingQuestionsViewHolder(val binding: QuestionItemBinding):RecyclerView.ViewHolder(binding.root)

    lateinit var onItemClick : ((Item)->Unit)

    private var diffUtil = object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.question_id == newItem.question_id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }



    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingQuestionsViewHolder {
        return TrendingQuestionsViewHolder(
            QuestionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent ,false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingQuestionsViewHolder, position: Int) {
        val item = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(item.owner.profile_image)
            .into(holder.binding.imgOwner)

        holder.binding.tvQuestion.text = item.title
        holder.binding.tvViews.text = item.view_count.toString()
        holder.binding.tvAnswerCount.text = "Answers: "+item.answer_count.toString()
        holder.binding.userName.text = "Name: "+item.owner.display_name
        holder.binding.userReputation.text = "Reputation: "+ item.owner.reputation.toString()


        holder.binding.tvDate.text = convertEpochToDateString(item.creation_date?:0)

        var tag = ""
        var c = 1
        for (t in item.tags){
            if(c>3){
                break
            }
            tag += t+", "
            c++
        }
        holder.binding.tvTags.text = tag

        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }


    }

    fun convertEpochToDateString(epochTime: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(epochTime * 1000)
        return dateFormat.format(date)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}