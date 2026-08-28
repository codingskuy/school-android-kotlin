package io.codingskuy.weather_app.domain.usecase

import io.codingskuy.weather_app.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor (
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double) = repository.getWeather(latitude, longitude)
}