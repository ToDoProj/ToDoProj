package ru.androidlab.todoproj.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<TaskEntity>>

    @Insert
    fun insert(task: TaskEntity)

    @Query("UPDATE TaskEntity SET title = :title AND priority = :priority AND done = :done AND description = :description WHERE id = :id")
    fun updateContentById(
            id: Long,
            title: String,
            description: String,
            priority: String,
            done: Boolean
    )

    fun save(task: TaskEntity) =
        task.id?.let {id ->
            updateContentById(
                    id,
                    task.title,
                    task.description,
                    task.priority,
                    task.done
            )
        } ?: insert(task)

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    fun removeById(id: Long)

    @Update
    fun update(task: TaskEntity)
}