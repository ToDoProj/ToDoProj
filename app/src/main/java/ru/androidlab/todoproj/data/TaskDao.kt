package ru.androidlab.todoproj.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskEntity WHERE done = :done ORDER BY id DESC")
    fun  getAllActualTask(done: Boolean): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE done = :done ")
    fun getAllDone(done: Boolean): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE priority='Low' AND done = :done ")
    fun getAllLowPriority(done: Boolean): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE priority='Medium' AND done = :done ")
    fun getAllMediumPriority(done: Boolean): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE priority='High' AND done = :done")
    fun getAllHighPriority(done: Boolean): LiveData<List<TaskEntity>>

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