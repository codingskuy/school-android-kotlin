package io.codingskuy.weather_app.presentation.viewmodels

import app.cash.turbine.test
import io.codingskuy.weather_app.domain.entities.AirQuality
import io.codingskuy.weather_app.domain.entities.Weather
import io.codingskuy.weather_app.domain.usecase.GetAirQualityUseCase
import io.codingskuy.weather_app.domain.usecase.GetCurrentWeatherUseCase
import io.codingskuy.weather_app.presentations.viewmodel.AirQualityState
import io.codingskuy.weather_app.presentations.viewmodel.WeatherState
import io.codingskuy.weather_app.presentations.viewmodel.WeatherViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockWeatherUseCase = mockk<GetCurrentWeatherUseCase>()
    private val mockAirQualityUseCase = mockk<GetAirQualityUseCase>()
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(mockWeatherUseCase, mockAirQualityUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchWeather should update Weather state to Success`(): Unit = runTest(testDispatcher) {
        // Given
        val fakeWeather =  Weather(30.7, "°C", 58, "%", 0, "wmo code", "2026-08-31T13:45", "iso8601", "Asia/Jakarta", "GMT+7" )

        coEvery { mockWeatherUseCase(-6.2, 106.8) } returns fakeWeather

        // When
        viewModel.fetchWeather(WeatherViewModel.City("Yogyakta", -6.2, 106.8))

        // Then
        viewModel.weatherState.test {
            assertEquals(WeatherState.Success(fakeWeather), awaitItem())
        }
    }

    @Test
    fun `fetchAirQuality should update airQuality state to Success`(): Unit = runTest(testDispatcher) {
        // Given
        val expectedAirQuality = AirQuality(32.5, 29.3, 1009.0)

        coEvery { mockAirQualityUseCase(-6.2, 106.8) } returns expectedAirQuality

        // When
        viewModel.fetchAirQuality(WeatherViewModel.City("Yogyakta", -6.2, 106.8))

        // Then
        viewModel.airQualityState.test { assertEquals(AirQualityState.Success(expectedAirQuality), awaitItem()) }
    }
}