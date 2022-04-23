package com.example.c_hostel.model

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: Int,
    val name: String,
    val age: Int
)