package io.codingskuy.weather_app.domain.entities

data class Weather(
    val temperature: Double,
    val temperatureUnits: String,
    val humidity: Long,
    val humidityUnits: String,
    val weatherCode: Long,
    val weatherCodeUnits: String,
    val time: String,
    val timeUnits: String,
    val timezone: String,
    val timezoneAbbreviation: String
)
