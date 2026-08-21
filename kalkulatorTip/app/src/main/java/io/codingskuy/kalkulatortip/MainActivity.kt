package io.codingskuy.kalkulatortip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.codingskuy.kalkulatortip.ui.theme.KalkulatorTipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTipTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Rois! Ini app Android pertamaku",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var bill by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15f) }
    val billAmount = bill.toDoubleOrNull() ?: 0.0
    val tipAmount = billAmount * tipPercent / 100
    val total = billAmount + tipAmount

    Column(modifier = modifier.padding(16.dp)) {
        Text("Kalkulator Tip", style = MaterialTheme.typography.headlineMedium)
        Text("Hitung tip dengan mudah")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Tip : ${tipPercent.toInt()}%")
        Slider(
            value = tipPercent,
            onValueChange = {
                tipPercent = it
            },
            valueRange = 0f..30f,
            steps = 5
        )
        OutlinedTextField(
            value = bill,
            onValueChange = {
                bill = it
            },
            label = { Text("Nominal Tagihan")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Tip $tipPercent%: Rp ${String.format("%,d", tipAmount.toInt())}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Total: Rp ${total.toInt()}",
            style = MaterialTheme.typography.titleLarge
        )


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KalkulatorTipTheme {
        Greeting("Android")
    }
}