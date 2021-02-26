package ru.androidlab.todoproj.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<TaskEntity>>

    @Insert
    fun insert(task: TaskEntity)

    @Query("UPDATE TaskEntity SET title = :title AND priority = :priority  WHERE id = :id")
    fun updateContentById(id: Long, title: String, priority: String)

    fun save(task: TaskEntity) =
        if (task.id == 0L) insert(task) else updateContentById(task.id, task.title, task.priority)

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    fun removeById(id: Long)

}