package ru.androidlab.todoproj.views.activity

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.databinding.TaskSetupBinding
import ru.androidlab.todoproj.util.MockUtil
import ru.androidlab.todoproj.viewmodels.TaskViewModel


class TaskSetupActivity : AppCompatActivity() {

    private var isEditState = false
    private var task: TaskEntity = TaskEntity()
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var binding: TaskSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (intent?.getSerializableExtra(MockUtil.GET_TASK_ENTITY) as? TaskEntity)?.let { entity ->
            task = entity
            isEditState = true
            binding.editTextTitle.setText(entity.title)
            binding.btnSave.setText(getString(R.string.btn_save))
        }

        binding.btnSave.setOnClickListener {
            if (isEditState) {
                //Изменение приоритета работает только при изменении текста
                task.priority = binding.spinner.selectedItem.toString()
                task.apply { taskViewModel.update(this) }
            } else {
                taskViewModel.changeContent(
                    binding.editTextTitle.text?.toString() ?: "",
                    binding.spinner.selectedItem?.toString() ?: ""
                )
                taskViewModel.save()
            }
            finish()
        }

        binding.editTextTitle.addTextChangedListener { editable ->
            editable?.let { task.title = it.toString() }
        }

        binding.btnClose.setOnClickListener {
            finish()
        }

        setupSpinner()
    }

    fun setupSpinner() {
        val spinnerItems = mutableListOf<SpannableString>()
        resources.getStringArray(R.array.taskStatus).forEach { it ->
            when (it) {
                "Low" -> {
                    val spanString = SpannableString(it)
                    spanString.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.custom_green)),
                        0,
                        spanString.length,
                        0
                    )
                    spinnerItems.add(spanString)
                }
                "Medium" -> {
                    val spanString = SpannableString(it)
                    spanString.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.custom_yellow)),
                        0,
                        spanString.length,
                        0
                    )
                    spinnerItems.add(spanString)
                }
                "High" -> {
                    val spanString = SpannableString(it)
                    spanString.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.custom_red)),
                        0,
                        spanString.length,
                        0
                    )
                    spinnerItems.add(spanString)
                }
            }
        }
        val dataAdapter = ArrayAdapter<SpannableString>(this, R.layout.spinner_item)
        dataAdapter.addAll(spinnerItems)
        //dataAdapter.setDropDownViewResource(R.layout.spinner_item)
        binding.spinner.adapter = dataAdapter
    }
}

