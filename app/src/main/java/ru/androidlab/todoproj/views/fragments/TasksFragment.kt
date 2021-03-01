package ru.androidlab.todoproj.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.androidlab.todoproj.adapters.Adapter
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.databinding.FragmentTasksBinding
import ru.androidlab.todoproj.util.MockUtil
import ru.androidlab.todoproj.viewmodels.TaskViewModel
import ru.androidlab.todoproj.views.activity.TaskSetupActivity

class TasksFragment : Fragment(), Adapter.IMovieClick {

    private var removedPosition: Int = 0
    private var removedItem: String = ""
    private var removedTaskEntity: TaskEntity = TaskEntity(0, "", "", "", false)
    private val adapter = Adapter(this)
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

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
        viewModel.data.observe(viewLifecycleOwner, { posts ->
            adapter.submitList(posts)
        })
        showAndHideFloatingActionButton()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(context, TaskSetupActivity::class.java)
            startActivity(intent)
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
                                        TaskEntity(
                                                removedTaskEntity.id,
                                                removedItem,
                                                removedTaskEntity.description,
                                                removedTaskEntity.priority,
                                                removedTaskEntity.done
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
        removedTaskEntity.id = (adapter.currentList[viewHolder.adapterPosition] as TaskEntity).id
        removedItem = (adapter.currentList[viewHolder.adapterPosition] as TaskEntity).title
        removedTaskEntity.description = (adapter.currentList[viewHolder.adapterPosition] as TaskEntity).description
        removedTaskEntity.priority = (adapter.currentList[viewHolder.adapterPosition] as TaskEntity).priority
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

    override fun showToast(position: TaskEntity) {

        val intent = Intent(context, TaskSetupActivity::class.java).apply {
            putExtra(MockUtil.GET_TASK_ENTITY, position)
        }
        startActivity(intent)

        Toast.makeText(activity, "it's $position", Toast.LENGTH_LONG).show()
    }
}
