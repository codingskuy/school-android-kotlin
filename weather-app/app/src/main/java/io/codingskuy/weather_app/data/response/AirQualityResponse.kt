package io.codingskuy.weather_app.data.response

import com.google.gson.annotations.SerializedName

data class AirQualityResponse (
    val latitude: Double ,
    val longitude: Double ,

    @SerializedName("generationtime_ms")
    val generationtimeMs: Double ,

    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Long ,

    val timezone: String ,

    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String ,

    val elevation: Long ,

    @SerializedName("current_units")
    val currentUnits: AirQualityCurrentUnits ,

    val current: AirQualityCurrent 
)

data class AirQualityCurrent (
    val time: String ,
    val interval: Long ,
    val pm10: Double ,

    @SerializedName("pm2_5")
    val pm25: Double ,

    @SerializedName("carbon_monoxide")
    val carbonMonoxide: Double 
)

data class AirQualityCurrentUnits (
    val time: String ,
    val interval: String ,
    val pm10: String ,

    @SerializedName("pm2_5")
    val pm25: String ,

    @SerializedName("carbon_monoxide")
    val carbonMonoxide: String 
)

