package ru.androidlab.todoproj.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.databinding.ItemHolderBinding

class Adapter(private val listener: IMovieClick) :
    ListAdapter<TaskEntity, BaseViewHolder>(MoviesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class Holder(private val binding: ItemHolderBinding) : BaseViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            ItemHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun bind(item: TaskEntity) {
            binding.apply {
                textTitle.text = item.title
                description.text = item.description
                root.setOnClickListener { listener.openTask(item) }
            }
        }
    }

    interface IMovieClick {
        fun openTask(position: TaskEntity)
    }

    object MoviesDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return when {
                oldItem is TaskEntity && newItem is TaskEntity -> {
                    oldItem.title == newItem.title
                }
                else -> true
            }
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }
}
