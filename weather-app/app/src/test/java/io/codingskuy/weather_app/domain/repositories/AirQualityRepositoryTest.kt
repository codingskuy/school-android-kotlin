package io.codingskuy.weather_app.domain.repositories

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AirQualityRepositoryTest {
    private val mockApi = mockk<AirQualityApi>()
    private val repo = AirQualityRepositoryImpl(mockApi)

    @Test
    fun `repository should map response to domain`(): Unit = runTest {
        // Given: API kasih response palsu (sesuai json)
        val expectedResponse = AirQualityResponse(
            latitude = -6.2,
            longitude = 106.8,
            generationtimeMs = 0.13,
            utcOffsetSeconds = 0,
            timezone = "GMT",
            timezoneAbbreviation = "GMT",
            elevation = 15,
            currentUnits = AirQualityCurrentUnits(
                time = "iso8601",
                interval = "seconds",
                pm10 = "μg/m³",
                pm2_5 = "μg/m³",
                carbonMonoxide = "μg/m³"
            ),
            current = AirQualityCurrent(
                time = "2026-08-31T04:00",
                interval = 3600,
                pm10 = 32.5,
                pm2_5 = 29.3,
                carbonMonoxide = 1009.0
            )
        )

        coEvery { mockApi.getAirQuality(-6.2, 106.8, any(), any()) } returns expectedResponse

        // When
        val result = repo.getAirQuality(-6.2, 106.8)

        // Then: mapping harus benar
        assertEquals(32.5, result.pm10, 0.01)
        assertEquals(29.3, result.pm25, 0.01)
        assertEquals(1009.0, result.carbonMonoxide, 0.01)
    }
}