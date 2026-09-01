package io.codingskuy.weather_app.presentations

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.codingskuy.weather_app.domain.entities.AirQuality
import io.codingskuy.weather_app.domain.entities.Weather
import io.codingskuy.weather_app.domain.repository.AirQualityRepository
import io.codingskuy.weather_app.domain.repository.WeatherRepository
import io.codingskuy.weather_app.domain.usecase.GetAirQualityUseCase
import io.codingskuy.weather_app.domain.usecase.GetCurrentWeatherUseCase
import io.codingskuy.weather_app.presentations.ui.WeatherScreen
import io.codingskuy.weather_app.presentations.viewmodel.WeatherViewModel
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class WeatherScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun tapCity_shouldShowLoading_thenSuccess() {
        // Given: ViewModel pura-pura
        val vm = WeatherViewModel(
            GetCurrentWeatherUseCase(FakeWeatherRepo()),
            GetAirQualityUseCase(FakeAirRepo())
        )

        // When: Tampilkan layar
        composeRule.setContent { WeatherScreen(viewModel = vm) }

        // Then: harus ada teks "Pilih kota untuk melihat cuaca!"
        composeRule.onNodeWithText("Pilih kota untuk melihat cuaca!").assertIsDisplayed()

        // When: tap kota
        composeRule.onNodeWithText("Yogyakarta").performClick()

        // Then: harus ada loading
        composeRule.waitUntil(timeoutMillis = 3000) {
            composeRule.onAllNodesWithText("Mengambil data...").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Mengambil data...").assertIsDisplayed()
    }

    @Test
    fun tapWeatherCity_shouldShowSuccess() {
        val vm = WeatherViewModel(
            GetCurrentWeatherUseCase(FakeWeatherRepo(false)),
            GetAirQualityUseCase(FakeAirRepo())
        )

        composeRule.setContent { WeatherScreen(vm) }

        composeRule.onNodeWithText("Yogyakarta").performClick()
        composeRule.waitUntil(timeoutMillis = 3000) {
            composeRule.onAllNodesWithText("Suhu: 30.0 °C").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Suhu: 30.0 °C").assertIsDisplayed()
    }

    @Test
    fun tapWeatherCity_shouldShowError_whenRepoFails() {
        val vm = WeatherViewModel(
            GetCurrentWeatherUseCase(FakeWeatherRepo(true)),
            GetAirQualityUseCase(FakeAirRepo())
        )

        composeRule.setContent { WeatherScreen(vm) }

        composeRule.onNodeWithText("Yogyakarta").performClick()
        composeRule.waitUntil(timeoutMillis = 3000) {
            composeRule.onAllNodesWithText("Gagal mengambil data: Gagal mengambil cuaca").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Gagal mengambil data: Gagal mengambil cuaca").assertIsDisplayed()
    }

}

// Fake biar nggak perlu mockk
class FakeWeatherRepo(
    private val shouldFail: Boolean = false
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lng: Double): Weather {
        delay(500.milliseconds)
        if(shouldFail) throw Exception("Gagal mengambil cuaca")
        return Weather(
            30.0, "°C", 60, "%", 0, "wmo", "2026-08-31", "iso", "GMT",
            "GMT"
        )
    }
}
class FakeAirRepo(
    private val shouldFail: Boolean = false
) : AirQualityRepository {
    override suspend fun getAirQuality(lat: Double, lng: Double): AirQuality {
        if(shouldFail) throw Exception("Gagal mengambil cuaca")

        return AirQuality(20.0, 10.0, 500.0)
    }
}