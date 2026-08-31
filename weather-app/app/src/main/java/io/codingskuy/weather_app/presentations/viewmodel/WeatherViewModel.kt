package io.codingskuy.weather_app.presentations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.codingskuy.weather_app.domain.entities.AirQuality
import io.codingskuy.weather_app.domain.entities.Weather
import io.codingskuy.weather_app.domain.usecase.GetAirQualityUseCase
import io.codingskuy.weather_app.domain.usecase.GetCurrentWeatherUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherState {
    data object Idle: WeatherState()
    data object Loading: WeatherState()
    data class Success(val data: Weather): WeatherState()
    data class Error(val message: String): WeatherState()
}

sealed class AirQualityState {
    data object Idle: AirQualityState()
    data object Loading: AirQualityState()
    data class Success(val data: AirQuality): AirQualityState()
    data class Error(val message: String): AirQualityState()
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getAirQualityUseCase: GetAirQualityUseCase
): ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherState = _weatherState.asStateFlow()

    private val _airQualityState = MutableStateFlow<AirQualityState>(AirQualityState.Idle)
    val airQualityState = _airQualityState.asStateFlow()

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
            _weatherState.value = WeatherState.Loading

            try {
                val result = getCurrentWeatherUseCase(city.latitude, city.longitude)
                _weatherState.value = WeatherState.Success(result)
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error("Gagal mengambil data: ${e.message}")
            }
        }
    }

    fun fetchAirQuality(city: City) {
        viewModelScope.launch {
            val result = getAirQualityUseCase(city.latitude, city.longitude)
            _airQualityState.value = AirQualityState.Success(result)
        }
    }

    fun fetchAll(city: City) {
        fetchWeather(city)
        fetchAirQuality(city)
    }
}