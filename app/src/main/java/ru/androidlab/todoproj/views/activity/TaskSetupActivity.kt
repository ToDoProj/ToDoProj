package ru.androidlab.todoproj.views.activity

import android.os.Bundle
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
    private var binding: TaskSetupBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskSetupBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        (intent?.getSerializableExtra(MockUtil.GET_TASK_ENTITY) as? TaskEntity)?.let { entity ->
            task = entity
            isEditState = true
            binding?.editTextTitle?.setText(entity.title)
            binding?.btnSave?.setText(getString(R.string.btn_save))
        }

        binding?.btnSave?.setOnClickListener {
            if (isEditState) {
                //Изменение приоритета работает только при изменении текста
                task.priority = binding?.spinner?.selectedItem.toString()
                task.apply { taskViewModel.update(this) }
            } else {
                taskViewModel.changeContent(
                        binding?.editTextTitle?.text?.toString() ?: "",
                        binding?.spinner?.selectedItem?.toString() ?: ""
                )
                taskViewModel.save()
            }
            finish()
        }

        binding?.editTextTitle?.addTextChangedListener { editable ->
            editable?.let { task.title = it.toString() }
        }

        binding?.btnClose?.setOnClickListener {
            finish()
        }

    }
}

