package com.mapbox.navigation.trip.notification.utils.time

import android.content.Context
import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.test.core.app.ApplicationProvider
import com.mapbox.navigation.base.typedef.NONE_SPECIFIED
import com.mapbox.navigation.base.typedef.TWELVE_HOURS
import com.mapbox.navigation.base.typedef.TWENTY_FOUR_HOURS
import java.util.GregorianCalendar
import java.util.Locale
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class TimeFormatterTest {

    private lateinit var ctx: Context

    @Before
    fun setup() {
        ctx = ApplicationProvider.getApplicationContext<Context>()
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingDefaultLocaleReturnsMinutes() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 428.0, null)

        Assert.assertEquals("7 min ", result.toString())
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingNonDefaultLocaleReturnsMinutes() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 428.0, Locale("hu"))

        Assert.assertEquals("7 perc ", result.toString())
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingDefaultLocaleReturnsHoursMin() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 4286.3, null)

        Assert.assertEquals("1 hr 11 min ", result.toString())
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingDefaultLocaleReturnsDayHoursMin() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 93963.3, null)

        Assert.assertEquals("1 day 2 hr 6 min ", result.toString())
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingDefaultLocaleReturnsMultiDayHoursMin() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 193963.3, null)

        Assert.assertEquals("2 days 5 hr 53 min ", result.toString())
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingDefaultLocaleReturnsMultiDayHoursMinNonDefaultLocale() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 193963.3, Locale("hu"))

        Assert.assertEquals("2 nap 5 óra 53 perc ", result.toString())
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingHasCorrectNumberOfSpans() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 428.0, null)

        Assert.assertEquals(2, result.getSpans(0, result.count(), Object::class.java).size)
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingTypeFace() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 428.0, null)

        Assert.assertEquals(
            Typeface.BOLD,
            (result.getSpans(0, result.count(), Object::class.java)[0] as StyleSpan).style
        )
    }

    @Config(qualifiers = "en")
    @Test
    fun formatTimeRemainingRelativeSizeSpan() {
        val result = TimeFormatter.formatTimeRemaining(ctx, 428.0, null)

        Assert.assertEquals(
            1.0f,
            (result.getSpans(
                0,
                result.count(),
                Object::class.java
            )[1] as RelativeSizeSpan).sizeChange
        )
    }

    @Ignore
    @Test
    fun formatTimeFormatNoneIsDeviceTwentyFourTrue() {
        val cal = GregorianCalendar().also {
            it.timeInMillis = 1584746685675
        }

        val result = TimeFormatter.formatTime(
            cal,
            434.0,
            NONE_SPECIFIED,
            true
        )

        Assert.assertEquals("16:31", result)
    }

    @Ignore
    @Test
    fun formatTimeFormatNoneIsDeviceTwentyFourFalse() {
        val cal = GregorianCalendar().also {
            it.timeInMillis = 1584746685675
        }

        val result = TimeFormatter.formatTime(
            cal,
            434.0,
            NONE_SPECIFIED,
            false
        )

        Assert.assertEquals("4:31 pm", result)
    }

    @Ignore
    @Test
    fun formatTimeFormatTwelveHour() {
        val cal = GregorianCalendar().also {
            it.timeInMillis = 1584746685675
        }

        val result = TimeFormatter.formatTime(
            cal,
            434.0,
            TWELVE_HOURS,
            false
        )

        Assert.assertEquals("4:31 pm", result)
    }

    @Test
    fun formatTimeFormatTwentyFourHour() {
        val cal = GregorianCalendar().also {
            it.timeInMillis = 1584746685675
        }

        val result = TimeFormatter.formatTime(
            cal,
            434.0,
            TWENTY_FOUR_HOURS,
            false
        )

        Assert.assertEquals("16:31", result)
    }
}
