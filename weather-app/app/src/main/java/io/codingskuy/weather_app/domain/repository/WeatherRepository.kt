package io.codingskuy.weather_app.domain.repository

import io.codingskuy.weather_app.domain.entities.Weather

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double ): Weather
}