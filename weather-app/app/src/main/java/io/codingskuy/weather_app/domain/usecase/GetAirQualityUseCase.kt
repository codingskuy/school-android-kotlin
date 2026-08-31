package io.codingskuy.weather_app.domain.usecase

import io.codingskuy.weather_app.domain.entities.AirQuality
import io.codingskuy.weather_app.domain.repository.AirQualityRepository

class GetAirQualityUseCase(
    private val repository: AirQualityRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): AirQuality {
        return repository.getAirQuality(latitude, longitude)
    }
}