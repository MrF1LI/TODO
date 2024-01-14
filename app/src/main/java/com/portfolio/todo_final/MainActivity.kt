package com.portfolio.todo_final

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.portfolio.todo_final.db.GroupsDao
import com.portfolio.todo_final.db.TasksDao
import com.portfolio.todo_final.fragments.AddTaskBottomSheetFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var toolbarTitle: TextView
    private lateinit var buttonShowLists: ImageButton
    private lateinit var buttonProperties: ImageButton

    private val titleLiveData = MutableLiveData<String>()

    private lateinit var tasksDao: TasksDao
    private lateinit var groupsDao: GroupsDao

    var activeGroupId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setupDb()
        registerListeners()
    }

    // MARK: - Private methods
    private fun init() {
        toolbarTitle = findViewById(R.id.toolbar_title)
        bottomNavView = findViewById(R.id.bottom_nav_view)
        buttonShowLists = findViewById(R.id.buttonShowLists)
        buttonProperties = findViewById(R.id.buttonProperties)
        navController = findNavController(R.id.host_fragment)

        bottomNavView.setupWithNavController(navController)
        setAppBarTitle("My Lists")

        titleLiveData.observe(this) { title ->
            toolbarTitle.text = title
        }
    }

    private fun setupDb() {
        val database = (application as MyApplication).database
        tasksDao = database.tasksDao()
        groupsDao = database.groupsDao()
    }

    private fun registerListeners() {
        buttonShowLists.setOnClickListener {
            navController.navigateUp()
        }

        buttonProperties.setOnClickListener {
            val bottomSheetFragment = AddTaskBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    // MARK: - Other methods
    fun setAppBarTitle(title: String) {
        titleLiveData.value = title
    }

    fun menuButtonVisibility(visibility: Int) {
        try {
            buttonShowLists.visibility = visibility
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("Saba", it) }
        }
    }

    fun propertiesButtonVisibility(visibility: Int) {
        try {
            buttonProperties.visibility = visibility
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("Saba", it) }
        }
    }
}