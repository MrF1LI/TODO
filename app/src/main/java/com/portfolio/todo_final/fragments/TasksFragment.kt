package com.portfolio.todo_final.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.todo_final.MainActivity
import com.portfolio.todo_final.MyApplication
import com.portfolio.todo_final.R
import com.portfolio.todo_final.adapters.TasksAdapter
import com.portfolio.todo_final.models.Task
import kotlinx.coroutines.launch

class TasksFragment: Fragment(R.layout.fragment_tasks) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter

    private var arrayOfTasks = listOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Saba", "onViewCreated")
        init(view)
        setupRecyclerView()
    }
    // MARK: - Private methods
    private fun init(view: View) {
        val parentActivity = (activity as? MainActivity)?: return
        parentActivity.menuButtonVisibility(VISIBLE)
        parentActivity.propertiesButtonVisibility(VISIBLE)

        recyclerView = view.findViewById(R.id.recyclerView)

        arguments?.let {
            val id = it.getLong("id")
            val label = it.getString("label")?: "Tasks"

            (activity as MainActivity).activeGroupId = id
            parentActivity.setAppBarTitle(label)
            loadTasks(id)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        tasksAdapter = TasksAdapter(arrayOfTasks)

        tasksAdapter.onItemClick = { task ->
            Log.d("Saba", "onItemClick - Task$task")
        }

        recyclerView.adapter = tasksAdapter
    }

    private fun loadTasks(groupId: Long) {
        // Obtain TasksDao from Room database
        val database = (activity?.application as MyApplication).database
        var tasksDao = database.tasksDao()


        lifecycleScope.launch {
            val tasks = tasksDao.getTasksByGroupId(groupId)
            tasksAdapter.updateTasks(tasks)

            tasks.forEach {  task ->
                Log.d("Saba", "Task: " + task.id + "-" + task.groupId)
            }
        }
    }
}