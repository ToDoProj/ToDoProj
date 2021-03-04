package ru.androidlab.todoproj.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var title: String = "",
    var description: String = "",
    var priority: String = "",
    var done: Boolean = false
) : Serializable {

    companion object {
        fun fromDto(dto: TaskEntity) =
            TaskEntity(dto.id, dto.title, dto.description, dto.priority, dto.done)

    }
}