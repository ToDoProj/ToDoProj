package ru.androidlab.todoproj.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.androidlab.todoproj.databinding.ItemHolderBinding

class Adapter(private val listener: IMovieClick) : ListAdapter<Item, BaseViewHolder>(MoviesDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class Holder(private val binding: ItemHolderBinding) :  BaseViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(ItemHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun bind(item: Item) {
            item as Item.Card
            binding.apply {
                textTitle.text = item.textTitle
                textTime.text = item.textTime
                alarmClock.setImageResource(item.alarmClockImage)
                time.text = item.time
                root.setOnClickListener { listener.showToast(item) }
            }
        }
    }

    interface IMovieClick {
        fun showToast(position: Item)
  //      fun onDelete(position: Item)
    }

    object MoviesDiffCallback : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return when {
                oldItem is Item.Card && newItem is Item.Card -> {
                    oldItem.textTitle == newItem.textTitle
                }
                else -> true
            }
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

}


