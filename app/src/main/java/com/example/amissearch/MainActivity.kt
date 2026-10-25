package com.example.amissearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amissearch.ui.theme.AMISSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMISSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AmisSearchScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Composable
fun AmisSearchScreen(viewModel: AmisViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Check AMIS Data") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.searchQuery = it },
                label = { Text("Enter NIC or Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = viewModel.searchType == "nic",
                    onClick = { viewModel.searchType = "nic" }
                )
                Text("NIC")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = viewModel.searchType == "name",
                    onClick = { viewModel.searchType = "name" }
                )
                Text("Name")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.performSearch() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val state = viewModel.uiState) {
                is UiState.Idle -> {
                    Text("Details type maadi search click maadi.")
                }
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Success -> {
                    if (state.records.isEmpty()) {
                        Text("Yavude records sigalilla.")
                    } else {
                        LazyColumn {
                            items(state.records) { item ->
                                RecordCard(item)
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun RecordCard(record: SuspectRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Name: ${record.name ?: "N/A"}", style = MaterialTheme.typography.titleMedium)
            Text("NIC: ${record.nic ?: "N/A"}")
            Text("Offense: ${record.offense ?: "N/A"}")
            Text("Date: ${record.date ?: "N/A"}")
        }
    }
}
