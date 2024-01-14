package com.portfolio.todo_final.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.portfolio.todo_final.models.Group
import com.portfolio.todo_final.models.Task

@Dao
interface GroupsDao {
    @Insert
    suspend fun insertGroup(group: Group): Long

    @Query("SELECT * FROM groups")
    suspend fun getGroups(): List<Group>
}
