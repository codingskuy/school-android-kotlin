package io.codingskuy.weather_app.domain.repository

import io.codingskuy.weather_app.domain.entities.AirQuality

interface AirQualityRepository {
    suspend fun getAirQuality(latitude: Double, longitude: Double): AirQuality
}