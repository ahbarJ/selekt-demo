package com.ahbarx.selektdemo

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
var database: ContactDatabase? = null
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // according to https://bloomberg.github.io/selekt/getting_started/
        // we will need these variables
        val factory = createSupportSQLiteOpenHelperFactory(
            SQLiteJournalMode.WAL,
            deriveKey()
        )
        database = Room.databaseBuilder(applicationContext, ContactDatabase::class.java,
            "contact")
            .openHelperFactory(factory)
            .build()
        val dao = database!!.contactDao()
        if (dao.getAll().isEmpty()) { // inserting some Contacts
            Log.d("MainActivity","Database empty. Inserting some dummy data.")
            val contactList = arrayListOf(
                Contact(1, "Ahbar Ami", 31U, "09145474261"),
                Contact(1, "Peter Parker", 33U, "04365122"),
                Contact(1, "Mary Jane", 28U, "03254881")
            )
            dao.insertAll(contactList)
        }

        enableEdgeToEdge()
        setContent {
            SelektDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    getContactsFromDatabase()?.let {
                        ContactsListView(
                            contacts = it,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
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

@Preview(showBackground = true)
@Composable
fun ContactsListViewPreview() {
    SelektDemoTheme {
        getContactsFromDatabase()?.let { ContactsListView(it) } // dummy data
    }
}


private fun deriveKey(): ByteArray = byteArrayOf(0x05, 0x07, 0x08, 0x0f)
private fun getContactsFromDatabase() : List<Contact>? {
    // to be defined later
    return database?.contactDao()?.getAll()
}