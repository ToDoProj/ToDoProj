package ru.androidlab.todoproj.views.fragments

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.adapters.Adapter
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.databinding.FragmentTasksBinding
import ru.androidlab.todoproj.util.MockUtil
import ru.androidlab.todoproj.viewmodels.TaskViewModel
import ru.androidlab.todoproj.views.activity.TaskSetupActivity
import java.util.*

class TasksFragment : Fragment(), Adapter.ClickableTask {

    private var removedPosition: Int = 0
    private var removedTaskEntity: TaskEntity = TaskEntity(0, "", "", "", false)
    private val adapter = Adapter(this)
    private lateinit var binding: FragmentTasksBinding

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.recyclerView.adapter = adapter
        setObserver()
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
                removeItem(viewHolder.adapterPosition)
                Snackbar.make(
                    viewHolder.itemView,
                    "${viewHolder.adapterPosition} deleted ",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setDuration(5000)
                    .setAction("UNDO") {
                        viewModel.saveRemovedTask(removedTaskEntity)
                    }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        var menuItem = menu.findItem(R.id.low_priority)
        var spanString = SpannableString(menuItem.title.toString())
        spanString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.custom_green)),
            0,
            spanString.length,
            0
        )
        menuItem.title = spanString

        menuItem = menu.findItem(R.id.medium_priority)
        spanString = SpannableString(menuItem.title.toString())
        spanString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.custom_yellow)),
            0,
            spanString.length,
            0
        )
        menuItem.title = spanString

        menuItem = menu.findItem(R.id.high_priority)
        spanString = SpannableString(menuItem.title.toString())
        spanString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.custom_red)),
            0,
            spanString.length,
            0
        )
        menuItem.title = spanString
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.low_priority -> {
                filterByLowPriority()
                true
            }
            R.id.medium_priority -> {
                filterByMediumPriority()
                true
            }
            R.id.high_priority -> {
                filterByHighPriority()
                true
            }
            R.id.show_done -> {
                getAllDone()
                true
            }
            R.id.show_all -> {
                getAllActualTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setObserver() {
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
    }

    private fun filterByLowPriority() {
        binding.floatingActionButton.hide()
        viewModel.data.removeObservers(viewLifecycleOwner)
        viewModel.filterByLowPriority()
        setObserver()
        adapter.notifyDataSetChanged()
    }

    private fun filterByMediumPriority() {
        binding.floatingActionButton.hide()
        viewModel.data.removeObservers(viewLifecycleOwner)
        viewModel.filterByMediumPriority()
        setObserver()
        adapter.notifyDataSetChanged()
    }

    private fun filterByHighPriority() {
        binding.floatingActionButton.hide()
        viewModel.data.removeObservers(viewLifecycleOwner)
        viewModel.filterByHighPriority()
        setObserver()
        adapter.notifyDataSetChanged()
    }

    private fun getAllDone() {
        binding.floatingActionButton.hide()
        viewModel.data.removeObservers(viewLifecycleOwner)
        viewModel.getAllDone()
        setObserver()
        adapter.notifyDataSetChanged()
    }

    private fun getAllActualTask() {
        binding.floatingActionButton.show()
        viewModel.data.removeObservers(viewLifecycleOwner)
        viewModel.getAllActualTask()
        setObserver()
        adapter.notifyDataSetChanged()
    }

    private fun saveTaskBackUp(position: Int) {
        removedPosition = position
        removedTaskEntity.id = (adapter.currentList[position] as TaskEntity).id
        removedTaskEntity.title = (adapter.currentList[position] as TaskEntity).title
        removedTaskEntity.description = (adapter.currentList[position] as TaskEntity).description
        removedTaskEntity.priority = (adapter.currentList[position] as TaskEntity).priority
        removedTaskEntity.done = (adapter.currentList[position] as TaskEntity).done
    }

    private fun removeItem(position: Int) {
        saveTaskBackUp(position)
        (adapter.currentList[position] as TaskEntity).id?.let { viewModel.removeById(it) }
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

    override fun openTask(task: TaskEntity) {
        val intent = Intent(context, TaskSetupActivity::class.java).apply {
            putExtra(MockUtil.GET_TASK_ENTITY, task)
        }
        startActivity(intent)
    }

    override fun complete(task: TaskEntity) {
        Toast.makeText(activity, "${task.title} complete", Toast.LENGTH_LONG).show()
        viewModel.updateDone(task)
        adapter.notifyDataSetChanged()
    }
}
