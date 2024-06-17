package com.ahbarx.selektdemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    suspend fun getAll(): List<Contact>

    @Insert
    suspend fun insertAll(contacts: List<Contact>)
}