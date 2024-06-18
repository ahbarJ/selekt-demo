package com.ahbarx.selektdemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.bloomberg.selekt.SQLiteJournalMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.bloomberg.selekt.android.support.createSupportSQLiteOpenHelperFactory
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: ContactDao

    init {
        val factory = createSupportSQLiteOpenHelperFactory(
            SQLiteJournalMode.WAL,
            deriveKey()
        )
        val database = Room.databaseBuilder(application, ContactDatabase::class.java, "contacts")
            .openHelperFactory(factory)
            .build()
        dao = database.contactDao()

        // Initialize dummy data if the database is empty
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (dao.getAllFlow().toList().isEmpty()) {
                    val contactList = arrayListOf(
                        Contact(name = "Ahbar Ami", age = 31, phoneNumber = "09145474261"),
                        Contact(name = "Mary Joseph", age = 27, phoneNumber = "09145001001"),
                        Contact(name = "Joe Biden", age = 121, phoneNumber = "00000000001")
                    )
                    dao.insertAll(contactList)
                }
            }
        }
    }

    val contacts = dao.getAllFlow().asLiveData() // Using Flow to LiveData for Compose

    private fun deriveKey(): ByteArray {
        return "ThisIsTheKeyStringWhichIForgot!!".toByteArray()
    }
}
