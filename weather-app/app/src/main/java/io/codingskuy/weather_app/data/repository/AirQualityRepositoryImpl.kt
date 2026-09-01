package io.codingskuy.weather_app.data.repository

import io.codingskuy.weather_app.data.api.AirQualityApi
import io.codingskuy.weather_app.domain.entities.AirQuality
import io.codingskuy.weather_app.domain.repository.AirQualityRepository

class AirQualityRepositoryImpl(
    private val api: AirQualityApi
) : AirQualityRepository {
    override suspend fun getAirQuality(
        latitude: Double,
        longitude: Double
    ): AirQuality {
        val response = api.getAirQuality(latitude, longitude)
        return AirQuality(
            pm10 = response.current.pm10 / 1000,
            pm25 = response.current.pm25 / 1000,
            carbonMonoxide = response.current.carbonMonoxide
        )
    }

}