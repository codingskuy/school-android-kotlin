package io.codingskuy.wheater_app.data.api

import io.codingskuy.wheater_app.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: List<String> = listOf("temperature_2m","relative_humidity_2m", "weather_code"),
        @Query("timezone") timezone: String = "Asia/Jakarta"
    ): WeatherResponse

}