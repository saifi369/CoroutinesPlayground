package com.u4universe.coroutinesplayground.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomUserResponse(
    val results: List<UserDto>
)

@Serializable
data class UserDto(
    @SerialName("name")
    val name: NameDto,
    @SerialName("email")
    val email: String
) {
    override fun toString(): String {
        return """
            |👤${name.title} ${name.first} ${name.last}
            |
            |📧$email
        """.trimMargin()
    }
}

@Serializable
data class NameDto(
    @SerialName("title")
    val title: String,
    @SerialName("first")
    val first: String,
    @SerialName("last")
    val last: String
)
