package ru.androidlab.todoproj.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.androidlab.todoproj.data.Task
import ru.androidlab.todoproj.databinding.ItemHolderBinding

class Adapter(private val listener: IMovieClick) :
    ListAdapter<Task, BaseViewHolder>(MoviesDiffCallback) {

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

        override fun bind(item: Task) {
            item as Task.AlarmTask
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
        fun showToast(position: Task)
    }

    object MoviesDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return when {
                oldItem is Task.AlarmTask && newItem is Task.AlarmTask -> {
                    oldItem.textTitle == newItem.textTitle
                }
                else -> true
            }
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }
}
