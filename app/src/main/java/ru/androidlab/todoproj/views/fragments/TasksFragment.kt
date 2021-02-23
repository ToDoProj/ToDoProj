package ru.androidlab.todoproj.views.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.androidlab.todoproj.adapters.Adapter
import ru.androidlab.todoproj.adapters.Item
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showToast(position: Item) {
        Toast.makeText(activity, "it's ${position}", Toast.LENGTH_LONG).show()
    }

}

