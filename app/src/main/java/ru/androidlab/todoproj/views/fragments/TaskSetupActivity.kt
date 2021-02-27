package ru.androidlab.todoproj.views.fragments

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.androidlab.todoproj.databinding.TaskSetupBinding
import ru.androidlab.todoproj.viewmodels.TaskViewModel

class TaskSetupActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = TaskSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            taskViewModel.changeContent(
                    binding.editTextTitle.text.toString(),
                    binding.spinner.selectedItem.toString()
            )
            taskViewModel.save()

            finish()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}