package ru.androidlab.todoproj.views.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.data.TaskDao
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.databinding.TaskSetupBinding
import ru.androidlab.todoproj.presenters.TaskSetupPresenter
import ru.androidlab.todoproj.util.DateTimePickerDialogPickerListener
import ru.androidlab.todoproj.util.DateTimePickerDialogProducer
import ru.androidlab.todoproj.util.MockUtil
import ru.androidlab.todoproj.util.MockUtil.GET_TASK_ENTITY
import ru.androidlab.todoproj.util.MockUtil.ID_EXTRA
import ru.androidlab.todoproj.viewmodels.TaskViewModel
import java.util.*


class TaskSetupActivity : AppCompatActivity(), ITaskSetupView {

    private var isEditState = false
    private var task: TaskEntity = TaskEntity()
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var binding: TaskSetupBinding
    private lateinit var presenter: TaskSetupPresenter
    private var remindDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (intent?.getSerializableExtra(GET_TASK_ENTITY) as? TaskEntity)?.let { entity ->
            task = entity
            isEditState = true
            binding.editTextTitle.setText(entity.title)
            binding.btnSave.text = getString(R.string.btn_save)
        }

        val taskId = intent?.getLongExtra(ID_EXTRA, -1)

        if(taskId != null && taskId>0){
            task = taskViewModel.getById(taskId)
            isEditState = true
            binding.editTextTitle.setText(task.title)
            binding.btnSave.text = getString(R.string.btn_save)
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
                if (remindDate != null) {
                    val tasks = taskViewModel.getAll()
                    val newTask = tasks.get(tasks.size-1)
                    newTask.description="10"
                    presenter.createAlarm(remindDate!!,newTask, this)
                }
            }

            finish()
        }

        binding.editTextTitle.addTextChangedListener { editable ->
            editable?.let { task.title = it.toString() }
        }

        binding.btnClose.setOnClickListener {
            finish()
        }

        val dialogProducer =
            DateTimePickerDialogProducer(this, object : DateTimePickerDialogPickerListener {
                override fun dateTimePicked(date: Date) {
                    Toast.makeText(this@TaskSetupActivity, date.toString(), Toast.LENGTH_LONG)
                        .show()
                    //presenter.createAlarm(date,"Oh My", this@TaskSetupActivity)
                    remindDate = date
                }
            })

        presenter = TaskSetupPresenter(this, dialogProducer)

        binding.addReminderButton.setOnClickListener {
            presenter.openDateTimeDialog()
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

    override fun openDateTimeDialog(datePickerDialog: DatePickerDialog) {
        datePickerDialog.show()
    }
}

