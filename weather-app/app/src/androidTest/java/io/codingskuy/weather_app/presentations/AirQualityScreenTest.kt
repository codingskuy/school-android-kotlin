package io.codingskuy.weather_app.presentations

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.codingskuy.weather_app.domain.entities.AirQuality
import io.codingskuy.weather_app.domain.repository.AirQualityRepository
import io.codingskuy.weather_app.domain.usecase.GetAirQualityUseCase
import io.codingskuy.weather_app.domain.usecase.GetCurrentWeatherUseCase
import io.codingskuy.weather_app.presentations.ui.AirQualityScreen
import io.codingskuy.weather_app.presentations.ui.WeatherScreen
import io.codingskuy.weather_app.presentations.viewmodel.WeatherViewModel
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class AirQualityScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun clickSuccessCard_shouldNavigateToAirQualityScreen() {
        // Given: viem model success
        val vm = WeatherViewModel(
            GetCurrentWeatherUseCase(FakeWeatherRepo()),
            GetAirQualityUseCase(FakeAirRepo())
        )

        composeRule.setContent {
            val nav = rememberNavController()
            NavHost(navController = nav, startDestination = "weather") {
                composable("weather") {
                    WeatherScreen(vm, onWeatherClick = {
                        nav.navigate("airQuality")
                    })
                }
                composable("airQuality") {
                    AirQualityScreen(vm)
                }
            }
        }

        // When: tap kota -> tunggu Success -> tap card Success
        composeRule.onNodeWithText("Yogyakarta").performClick()
        composeRule.waitUntil(3000) {
            composeRule.onAllNodesWithText("Cuaca Saat ini").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("Cuaca Saat ini").performClick()

        // Then: harus ada Pm10 dan PM25 di AirQuality
        composeRule.onNodeWithText("PM10 32.5 μg/m³").assertIsDisplayed()
    }
}

class FakeAirRepo(
    private val shouldFail: Boolean = false
) : AirQualityRepository {
    override suspend fun getAirQuality(lat: Double, lng: Double): AirQuality {
        delay(500.milliseconds)
        if(shouldFail) throw Exception("Gagal mengambil cuaca")

        return AirQuality(32.5, 29.3, 1009.0)
    }
}