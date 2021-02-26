package ru.androidlab.todoproj.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.androidlab.todoproj.adapters.Adapter
import ru.androidlab.todoproj.data.Task
import ru.androidlab.todoproj.databinding.FragmentTasksBinding
import ru.androidlab.todoproj.util.MockUtil

class TasksFragment : Fragment(), Adapter.IMovieClick {

    private var removedPosition: Int = 0
    private var removedItem: String = ""
    private var removedTask: Task.AlarmTask = Task.AlarmTask(0, "", "", "")
    private val adapter = Adapter(this)
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        adapter.submitList(MockUtil.geList())

        showAndHideFloatingActionButton()
        binding.floatingActionButton.setOnClickListener {
            val list = adapter.currentList.toMutableList().also {
                it.add(
                    Task.AlarmTask((adapter.currentList.size+1).toLong(),"Shopp", "till 5:00 pm", "low")
                )
            }
            adapter.submitList(list)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                removeItem(viewHolder)

                Snackbar.make(viewHolder.itemView, "$removedItem deleted ", Snackbar.LENGTH_INDEFINITE)
                    .setDuration(5000)
                    .setAction("UNDO") {
                        val list = adapter.currentList.toMutableList().also {
                            it.add(
                                removedPosition,
                                Task.AlarmTask(
                                    removedTask.id,
                                    removedItem,
                                    removedTask.description,
                                    removedTask.priority
                                )
                            )
                        }
                        adapter.submitList(list)
                    }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)
    }

    private fun writeRemoveTask(viewHolder: RecyclerView.ViewHolder) {
        removedPosition = viewHolder.adapterPosition
        removedTask.id = (adapter.currentList[viewHolder.adapterPosition] as Task.AlarmTask).id
        removedItem = (adapter.currentList[viewHolder.adapterPosition] as Task.AlarmTask).title
        removedTask.description = (adapter.currentList[viewHolder.adapterPosition] as Task.AlarmTask).description
        removedTask.priority = (adapter.currentList[viewHolder.adapterPosition] as Task.AlarmTask).priority
    }

    private fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        val list = adapter.currentList.toMutableList()
            .also { it.removeAt(viewHolder.adapterPosition) }
        adapter.submitList(list)
        writeRemoveTask(viewHolder)
    }

    private fun showAndHideFloatingActionButton() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && binding.floatingActionButton.visibility == View.VISIBLE) {
                    binding.floatingActionButton.hide()
                } else if (dy < 0 && binding.floatingActionButton.visibility != View.VISIBLE) {
                    binding.floatingActionButton.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showToast(position: Task) {
        Toast.makeText(activity, "it's $position", Toast.LENGTH_LONG).show()
    }
}
