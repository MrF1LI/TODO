package com.portfolio.todo_final.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.portfolio.todo_final.MainActivity
import com.portfolio.todo_final.MyApplication
import com.portfolio.todo_final.R
import com.portfolio.todo_final.models.Task
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Timestamp
import java.util.Calendar

class AddTaskBottomSheetFragment: BottomSheetDialogFragment(R.layout.fragment_add_task_bottom_sheet) {
    private lateinit var editTextName: AppCompatEditText
    private lateinit var editTextNote: AppCompatEditText
    private lateinit var buttonCreateTask: AppCompatButton

    private var groupId: Long = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerListeners()
    }

    // MARK: - Private methods
    private fun init(view: View) {
        editTextName = view.findViewById(R.id.editTextName)
        editTextNote = view.findViewById(R.id.editTextNote)
        buttonCreateTask = view.findViewById(R.id.buttonCreateTask)

        groupId = (activity as MainActivity).activeGroupId
    }

    private fun registerListeners() {
        buttonCreateTask.setOnClickListener {
            // Obtain TasksDao from Room database
            val database = (activity?.application as MyApplication).database
            var tasksDao = database.tasksDao()

            try {
                val task = Task(
                    title = editTextName.text.toString(),
                    content = editTextNote.text.toString(),
                    isCompleted = false,
                    date = Calendar.getInstance().time,
                    groupId = groupId
                )

                lifecycleScope.launch {
                    tasksDao.insertTask(task)
                    dismiss()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { it1 -> Log.e("Saba", it1) }
            }
        }
    }
}