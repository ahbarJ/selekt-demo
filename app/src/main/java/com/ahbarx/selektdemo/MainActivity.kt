package com.ahbarx.selektdemo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ahbarx.selektdemo.ui.theme.SelektDemoTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.room.Room
import com.bloomberg.selekt.SQLiteJournalMode
import com.bloomberg.selekt.android.support.createSupportSQLiteOpenHelperFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var database: ContactDatabase
private lateinit var dao: ContactDao

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SelektDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactsListView(
                        contacts = getContactsFromDatabase(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        val TAG = "MaiAct"
        Log.d(TAG, "onCreate called")

        val factory = createSupportSQLiteOpenHelperFactory(
            SQLiteJournalMode.WAL,
            deriveKey()
        )
        Log.d(TAG, "factory val created")

        database = Room.databaseBuilder(this, ContactDatabase::class.java,
            "contacts")
            .openHelperFactory(factory)
            .build()                        // ERROR ON THIS LINE

        Log.d(TAG, "database object created")
        dao = database.contactDao()
        Log.d("Main", "database object: ${database}\ndao object: $dao")
        CoroutineScope(Dispatchers.IO).launch {
            if (dao.getAll().isEmpty()) { // inserting some Contacts
                Log.d("MainActivity","Database empty. Inserting some dummy data.")
                val contactList = arrayListOf(
                    Contact(name = "Ahbar Ami", age = 31U, phoneNumber = "09145474261"),
                    Contact(name = "Mary Joseph", age = 27U, phoneNumber = "09145001001"),
                    Contact(name = "Joe Biden", age = 121U, phoneNumber = "00000000001")
                )
                dao.insertAll(contactList)
            }
        }



    }
}


@Composable
fun ContactsListView(contacts: List<Contact>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(contacts) { contact ->
            Text(text = "$contact", modifier)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Preview(showBackground = true)
@Composable
fun ContactsListViewPreview() {
    SelektDemoTheme {
        var contacts: List<Contact> = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            contacts = getContactsFromDatabase()
        }
        ContactsListView(contacts) // dummy data
    }
}


private fun deriveKey(): ByteArray{
    return "ThisIsTheKeyStringWhichIForgot!!".toByteArray()
}

private fun getContactsFromDatabase() : List<Contact> {
    // to be defined later
    var contacts: List<Contact> = emptyList()
    CoroutineScope(Dispatchers.IO).launch {
        contacts = dao.getAll()
    }
    return contacts
}

