package com.u4universe.coroutinesplayground.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String
) {
    override fun toString(): String {
        return """
            |👤${name}
            |
            |📧$email
        """.trimMargin()

    }
}
