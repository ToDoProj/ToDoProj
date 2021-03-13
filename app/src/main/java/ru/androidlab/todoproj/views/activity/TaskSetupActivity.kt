package ru.androidlab.todoproj.views.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import ru.androidlab.todoproj.MainActivity
import ru.androidlab.todoproj.R
import ru.androidlab.todoproj.data.TaskEntity
import ru.androidlab.todoproj.databinding.TaskSetupBinding
import ru.androidlab.todoproj.presenters.TaskSetupPresenter
import ru.androidlab.todoproj.util.DateTimePickerDialogPickerListener
import ru.androidlab.todoproj.util.DateTimePickerDialogProducer
import ru.androidlab.todoproj.util.MockUtil.GET_TASK_ENTITY
import ru.androidlab.todoproj.util.MockUtil.ID_EXTRA
import ru.androidlab.todoproj.viewmodels.TaskViewModel
import ru.androidlab.todoproj.views.fragments.TasksFragment
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = TaskSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dialogProducer =
            DateTimePickerDialogProducer(this, object : DateTimePickerDialogPickerListener {
                override fun dateTimePicked(date: Date) {
                    Toast.makeText(this@TaskSetupActivity, date.toString(), Toast.LENGTH_LONG)
                        .show()
                    remindDate = date
                    presenter.showRemindInfo(remindDate!!)
                }
            })

        presenter = TaskSetupPresenter(this, dialogProducer)

        (intent?.getSerializableExtra(GET_TASK_ENTITY) as? TaskEntity)?.let { entity ->
            task = entity
            isEditState = true
            binding.editTextTitle.setText(entity.title)
            binding.btnSave.text = getString(R.string.btn_save)
        }

        val taskId = intent?.getLongExtra(ID_EXTRA, -1)

        if (taskId != null && taskId > 0) {
            task = taskViewModel.getById(taskId)
            if (task.done) {
                binding.completeTaskButton.text =
                    resources.getText(R.string.task_has_been_completed)
                binding.completeTaskButton.isEnabled = false
            }
            isEditState = true
            presenter.showCompleteForm()
            binding.editTextTitle.setText(task.title)
            binding.btnSave.text = getString(R.string.btn_save)
        }

        binding.completeTaskButton.setOnClickListener {
            taskViewModel.updateDone(task)
            val dialog = AlertDialog.Builder(this)
                .setMessage(resources.getString(R.string.open_main_app))
                .setPositiveButton(
                    resources.getString(R.string.yes)
                ) { _, _ ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton(
                    resources.getString(R.string.no)
                ) { _, _ ->
                    finish()
                }
                .create()
            dialog.show()


        }

        binding.btnSave.setOnClickListener {
            if (binding.editTextTitle.text?.toString() == "") {
                Toast.makeText(this, "add a title", Toast.LENGTH_SHORT).show()
                binding.editTextTitle.background.setTint(resources.getColor(R.color.custom_red))
            } else {
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
                        val newTask = tasks.get(tasks.size - 1)
                        newTask.description = "10"
                        presenter.createAlarm(remindDate!!, newTask, this)
                    }
                }
                finish()
            }
        }

        binding.editTextTitle.addTextChangedListener { editable ->
            editable?.let { task.title = it.toString() }
        }

        binding.addReminderButton.setOnClickListener {
            presenter.openDateTimeDialog()
        }
        binding.removeReminder.setOnClickListener {
            remindDate = null
            presenter.removeReminder()
        }
        setupSpinner()
    }

    private fun setupSpinner() {
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
        binding.spinner.adapter = dataAdapter
    }

    override fun openDateTimeDialog(datePickerDialog: DatePickerDialog) {
        datePickerDialog.show()
    }

    override fun showRemindButton(show: Boolean) {
        binding.addReminderButton.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showRemindInfo(show: Boolean) {
        binding.reminderInfo.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun setRemindInfo(remindDate: Date) {
        val dateFormat =
            android.text.format.DateFormat.format("dd.MM.yy HH:mm", remindDate)
        binding.reminderDatetime.text = dateFormat
    }

    override fun showCompleteForm() {
        binding.editTextTitle.isEnabled = false

        binding.spinner.visibility = View.INVISIBLE
        binding.reminderLayout.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.INVISIBLE

        binding.completeTaskButton.visibility = View.VISIBLE
    }
}
