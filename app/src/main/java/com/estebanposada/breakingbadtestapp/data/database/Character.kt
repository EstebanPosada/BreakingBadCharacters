package com.estebanposada.breakingbadtestapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true)
    val char_id: Int,
    val name: String,
    val birthday: String,
    val occupation: List<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val appearance: List<Int>,
    val portrayed: String,
    val category: String,
    val better_call_saul_appearance: List<Int>,
    val favorite: Boolean
)