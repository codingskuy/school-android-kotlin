package io.codingskuy.weather_app.domain.usecases

import io.codingskuy.weather_app.domain.entities.AirQuality
import io.codingskuy.weather_app.domain.repository.AirQualityRepository
import io.codingskuy.weather_app.domain.usecase.GetAirQualityUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AirQualityUseCaseTest {
    private val mockRepo = mockk<AirQualityRepository>()
    private val useCase = GetAirQualityUseCase(mockRepo)

    @Test
    fun `get air quality should return pm10 and pm25`(): Unit = runTest {
        // Given: repo bakal kasi data palsu
        val expectedData = AirQuality(pm10 = 20.5, pm25 = 12.3, carbonMonoxide = 0.8)

        coEvery { mockRepo.getAirQuality(-6.2, 106.8) } returns expectedData

        // When: panggil usecase
        val result = useCase(-6.2, 106.8)

        //Then: harus usecase
        Assert.assertEquals(20.5, result.pm10, 0.01)
        Assert.assertEquals(12.3, result.pm25, 0.01)
    }
}