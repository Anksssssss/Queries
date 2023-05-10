package com.example.queries.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.queries.databinding.QuestionItemBinding
import com.example.queries.databinding.TagsItemBinding
import com.example.queries.models.Item
import com.example.queries.models.ItemX

class TagsAdapter : RecyclerView.Adapter<TagsAdapter.TagsViewHolder>() {

    inner class TagsViewHolder(val binding: TagsItemBinding):RecyclerView.ViewHolder(binding.root)

    lateinit var onItemClick : ((ItemX)->Unit)

    private var diffUtil = object : DiffUtil.ItemCallback<ItemX>(){
        override fun areItemsTheSame(oldItem: ItemX, newItem: ItemX): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ItemX, newItem: ItemX): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        return TagsViewHolder(TagsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        holder.binding.tvTags.text = differ.currentList[position].name

        holder.itemView.setOnClickListener {
            onItemClick.invoke(differ.currentList[position])
        }
    }

}