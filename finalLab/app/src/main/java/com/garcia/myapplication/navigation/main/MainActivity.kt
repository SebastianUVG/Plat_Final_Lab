package com.garcia.myapplication.navigation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.garcia.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar() },
                    bottomBar = { OfflineButton() }
                ) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "CryptoApp") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun OfflineButton() {
    Surface(
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { /* Acción no implementada */ },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Ver Offline")
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val sampleData = listOf(
        AssetUiModel("Bitcoin", "BTC", "30000.00", "+2.5%"),
        AssetUiModel("Ethereum", "ETH", "2000.00", "-1.2%"),
        AssetUiModel("Litecoin", "LTC", "150.00", "+0.8%")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(sampleData) { asset ->
            AssetItem(asset)
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }
    }
}

@Composable
fun AssetItem(asset: AssetUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = asset.name, style = MaterialTheme.typography.titleMedium)
            Text(text = asset.symbol, style = MaterialTheme.typography.bodySmall)
        }
        Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
            Text(text = "$${asset.priceUsd}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = asset.changePercent,
                color = if (asset.changePercent.startsWith("+")) Color.Green else Color.Red
            )
        }
    }
}

data class AssetUiModel(
    val name: String,
    val symbol: String,
    val priceUsd: String,
    val changePercent: String
)

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    MyApplicationTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { OfflineButton() }
        ) {
            MainContent(modifier = Modifier.padding(it))
        }
    }
}
