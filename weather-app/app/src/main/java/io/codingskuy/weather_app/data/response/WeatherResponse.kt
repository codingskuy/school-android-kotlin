package io.codingskuy.weather_app.data.response

import com.google.gson.annotations.SerializedName


data class WeatherResponse (
    val latitude: Double,
    val longitude: Double,

    @SerializedName("generationtime_ms")
    val generationtimems: Double,

    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Long,

    val timezone: String,

    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String,

    val elevation: Long,

    @SerializedName("current_units")
    val currentUnits: CurrentUnits,

    val current: Current
)

data class Current (
    val time: String,
    val interval: Long,

    @SerializedName("temperature_2m")
    val temperature2M: Double,

    @SerializedName("relative_humidity_2m")
    val relativeHumidity2M: Long,

    @SerializedName("weather_code")
    val weatherCode: Long
)

data class CurrentUnits (
    val time: String,
    val interval: String,

    @SerializedName("temperature_2m")
    val temperature2M: String,

    @SerializedName("relative_humidity_2m")
    val relativeHumidity2M: String,

    @SerializedName("weather_code")
    val weatherCode: String
)

