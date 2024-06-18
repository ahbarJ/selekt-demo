package com.ahbarx.selektdemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAllFlow(): Flow<List<Contact>>

    @Insert
    suspend fun insertAll(contacts: List<Contact>)
}
