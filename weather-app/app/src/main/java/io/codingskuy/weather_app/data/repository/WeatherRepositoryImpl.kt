package io.codingskuy.weather_app.data.repository

import io.codingskuy.weather_app.data.api.WeatherApi
import io.codingskuy.weather_app.domain.entities.Weather
import io.codingskuy.weather_app.domain.repository.WeatherRepository

class WeatherRepositoryImpl (
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(latitude: Double, longitude: Double ): Weather {
        val response = api.getCurrentWeather(latitude, longitude)
        return Weather(
            temperature = response.current.temperature2M,
            temperatureUnits = response.currentUnits.temperature2M,
            weatherCode = response.current.weatherCode,
            weatherCodeUnits = response.currentUnits.weatherCode,
            humidity = response.current.relativeHumidity2M,
            humidityUnits = response.currentUnits.relativeHumidity2M,
            time = response.current.time,
            timeUnits = response.currentUnits.time,
            timezone = response.timezone,
            timezoneAbbreviation = response.timezoneAbbreviation
        )
    }
}