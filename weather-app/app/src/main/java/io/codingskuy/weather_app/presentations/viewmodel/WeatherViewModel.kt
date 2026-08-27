package io.codingskuy.weather_app.presentations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.codingskuy.weather_app.data.repository.WeatherRepository
import io.codingskuy.wheater_app.data.response.WeatherResponse
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherState {
    data object Idle: WeatherState()
    data object Loading: WeatherState()
    data class Success(val data: WeatherResponse): WeatherState()
    data class Error(val message: String): WeatherState()
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val repository: WeatherRepository
): ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state = _state.asStateFlow()

    data class City(val name: String, val latitude: Double, val longitude: Double)
    val cities = listOf(
        City("Jakarta", -6.2088, 106.8456),
        City("Bandung", -6.9175, 107.6191),
        City("Surabaya", -7.2575, 112.7521),
        City("Yogyakarta", -7.7956, 110.3695),
        City("Medan", 3.5952, 98.6722),
        City("Makassar", -5.1477, 119.4327),
        City("Bali", -8.3405, 115.0920),
    )

    fun fetchWeather(city: City) {
        viewModelScope.launch {
            _state.value = WeatherState.Loading

            try {
                val result = repository.getWeather(city.latitude, city.longitude)
                _state.value = WeatherState.Success(result)
            } catch (e: Exception) {
                _state.value = WeatherState.Error("Gagal mengambil data: ${e.message}")
            }
        }
    }
}