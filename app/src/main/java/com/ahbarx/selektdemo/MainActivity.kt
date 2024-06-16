package com.ahbarx.selektdemo

import android.os.Bundle
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
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun ContactsListView(contacts: List<Contact>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(contacts) { contact ->
            Text(text = "$contact")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactsListViewPreview() {
    SelektDemoTheme {
        ContactsListView(List<Contact> (size = 4){index: Int ->
            Contact("Contact", 25U, "0694") }) // dummy data
    }
}

data class Contact(val name: String, val age: UInt, val phone: String)
private fun deriveKey(): ByteArray = byteArrayOf(0x05, 0x07, 0x08, 0x0f)
private fun getContactsFromDatabase() : List<Contact> {
    // to be defined later
    return emptyList()
}