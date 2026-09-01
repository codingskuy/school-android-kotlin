package io.codingskuy.weather_app.presentations.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.codingskuy.weather_app.presentations.viewmodel.AirQualityState
import io.codingskuy.weather_app.presentations.viewmodel.WeatherViewModel

@Composable
fun AirQualityScreen(viewModel: WeatherViewModel) {
    val airQuality by viewModel.airQualityState.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Kualitas Udara", style = MaterialTheme.typography.headlineMedium)
        when(val currentState = airQuality) {
            AirQualityState.Idle -> {
                Text(
                    text = "Pilih kota untuk melihat cuaca!",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is AirQualityState.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            AirQualityState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                Text("Mengambil data...", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is AirQualityState.Success -> {
                Text("PM10 ${currentState.data.pm10} μg/m³")
                Text("PM2.5 ${currentState.data.pm25} μg/m³")
                Text("CO ${currentState.data.carbonMonoxide} μg/m³")
            }
        }
    }
}