package com.ahbarx.selektdemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): List<Contact>

    @Insert
    fun insertAll(vararg contact: ArrayList<Contact>)
}