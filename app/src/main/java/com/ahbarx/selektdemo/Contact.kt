package com.ahbarx.selektdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String,
    val age: Int,
    val phoneNumber: String
)
