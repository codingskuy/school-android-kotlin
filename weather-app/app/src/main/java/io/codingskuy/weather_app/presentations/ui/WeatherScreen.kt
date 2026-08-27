package io.codingskuy.weather_app.presentations.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.codingskuy.weather_app.presentations.viewmodel.WeatherState
import io.codingskuy.weather_app.presentations.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel, modifier: Modifier) {

    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Aplikasi Cuaca", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))

        Text("Pilih Kota:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp))

        LazyColumn(modifier =  Modifier.weight(1f)) {
            items(viewModel.cities) { city ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { viewModel.fetchWeather(city) }) {
                    Text(
                        text = city.name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        when(val currentState = state) {
            WeatherState.Idle -> {
                Text(
                    text = "Pilih kota untuk melihat cuaca!",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            WeatherState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                Text("Mengambil data...", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is WeatherState.Error -> {
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

            is WeatherState.Success -> {
                val data = currentState.data
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Cuaca Saat ini",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = "Suhu: ${data.current.temperature2M} ${data.currentUnits.temperature2M}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "Kode Cuaca: ${data.current.weatherCode} ${data.currentUnits.weatherCode}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Kelembapan: ${data.current.relativeHumidity2M} ${data.currentUnits.relativeHumidity2M}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = "Waktu: ${data.current.time} ${data.timezone} ${data.timezoneAbbreviation}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }

}