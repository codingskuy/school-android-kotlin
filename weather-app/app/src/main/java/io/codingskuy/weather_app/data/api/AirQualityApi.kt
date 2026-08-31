package io.codingskuy.weather_app.data.api

import io.codingskuy.weather_app.data.response.AirQualityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApi {

    @GET("v1/air-quality")
    suspend fun getAirQuality(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: List<String> = listOf("pm10", "pm2_5", "carbon_monoxide"),
        @Query("timezone") timezone: String = "GMT"
    ): AirQualityResponse
}