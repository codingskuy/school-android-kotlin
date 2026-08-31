package io.codingskuy.weather_app

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.codingskuy.weather_app.data.api.AirQualityApi
import io.codingskuy.weather_app.data.api.WeatherApi
import io.codingskuy.weather_app.data.repository.AirQualityRepositoryImpl
import io.codingskuy.weather_app.data.repository.WeatherRepositoryImpl
import io.codingskuy.weather_app.domain.repository.AirQualityRepository
import io.codingskuy.weather_app.domain.repository.WeatherRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object AppModule {
    private const val BASE_URL = "https://api.open-meteo.com/"
    private const val BASE_URL_AIR = "https://air-quality-api.open-meteo.com/"

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .client(httpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(
        WeatherApi::class.java
    )


    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository = WeatherRepositoryImpl(api)


    @Singleton
    @Provides
    @Named("airQuality")
    fun provideAirQualityRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit
            .Builder()
            .client(httpClient)
            .baseUrl(BASE_URL_AIR)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideAirQualityApi(@Named("airQuality") retrofit: Retrofit): AirQualityApi = retrofit.create(
        AirQualityApi::class.java
    )

    @Singleton
    @Provides
    fun provideAirQualityRepository(api: AirQualityApi): AirQualityRepository =
        AirQualityRepositoryImpl(api)
}