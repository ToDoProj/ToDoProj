package ru.androidlab.todoproj.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var description: String,
    var priority: String
) {

    companion object {
        fun fromDto(dto: TaskEntity) =
            TaskEntity(dto.id, dto.title, dto.description, dto.priority)

    }
}