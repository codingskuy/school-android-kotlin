package io.codingskuy.weather_app.data.repository

import io.codingskuy.weather_app.data.api.RetrofitClient
import io.codingskuy.wheater_app.data.response.WeatherResponse

class WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double ): WeatherResponse {
        return RetrofitClient.weatherApi.getCurrentWeather(latitude, longitude)
    }
}