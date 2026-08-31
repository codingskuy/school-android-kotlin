package io.codingskuy.weather_app

import io.mockk.coEvery
import kotlinx.coroutines.test.runTest
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class AirQualityUseCaseTest {
    private val fakeRepo = mockk<AirQualityRepository>()
    private val useCase = GetAirQualityUseCase(fakeRepo)

    @Test
    fun `get air quality should return pm10 and pm25`(): Unit = runTest {
        // Given: repo bakal kasi data palsu
        val fakeData = AirQuality(pm10 = 20.5, pm25 = 12.3, carbonMonoxide = 0.8)

        coEvery { fakeRepo.getAirQuality(-6.2, 106.8) } returns fakeData

        // When: panggil usecase
        val result = useCase(-6.2, 106.8)

        //Then: harus usecase
        assertEquals(20.5, result.pm10, 0.01)
        assertEquals(12.3, result.pm25, 0.01)
    }
}