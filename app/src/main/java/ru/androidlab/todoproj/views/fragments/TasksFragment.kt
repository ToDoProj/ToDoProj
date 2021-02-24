package ru.androidlab.todoproj.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ru.androidlab.todoproj.adapters.Adapter
import ru.androidlab.todoproj.data.Task
import ru.androidlab.todoproj.databinding.FragmentTasksBinding
import ru.androidlab.todoproj.util.MockUtil

class TasksFragment : Fragment(), Adapter.IMovieClick {

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
                    Task.AlarmTask(1,"Shopp", "till 5:00 pm","low")
                )
            }
            adapter.submitList(list)
        }
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
