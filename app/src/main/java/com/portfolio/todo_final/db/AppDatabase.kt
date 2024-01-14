package com.portfolio.todo_final.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.portfolio.todo_final.models.Group
import com.portfolio.todo_final.models.Task
import com.portfolio.todo_final.utils.DateConverter

@Database(entities = [Task::class, Group::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    abstract fun groupsDao(): GroupsDao
}
