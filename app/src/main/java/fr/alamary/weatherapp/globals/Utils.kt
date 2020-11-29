package fr.alamary.weatherapp.globals

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeUtils {

    companion object {
        fun toDate(
            dateString: String,
            dateFormat: String = "yyyy-MM-dd HH:mm:ss",
            timeZone: TimeZone = TimeZone.getTimeZone("UTC")
        ): Date {
            val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
            parser.timeZone = timeZone
            return parser.parse(dateString)
        }

        fun formatTo(
            date: Date,
            dateFormat: String,
            timeZone: TimeZone = TimeZone.getDefault()
        ): String {
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
            formatter.timeZone = timeZone
            return formatter.format(date)
        }

        fun fromUnixToDate(unix: Long): String {
            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter.ISO_INSTANT
                    .format(java.time.Instant.ofEpochSecond(unix))
            } else {
                var c = Calendar.getInstance()
                c.timeInMillis = unix
                return c.time.toString()
            }
        }
        fun toHour(date: String): String {

            val defaultFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val defaultDateFormat = defaultFormat.parse(date)
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            return timeFormat.format(defaultDateFormat)
        }

        fun toDayMonth(date: String): String {
            val defaultFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val defaultDateFormat = defaultFormat.parse(date)
            val dayFormat = SimpleDateFormat("EEE,dd,MMM", Locale.getDefault())

            return dayFormat.format(defaultDateFormat)
        }


    }
}

class GlobalUtils {

    companion object {
        fun getDrawableFromString(c: Context, imageName: String?): Drawable? {

            if (imageName != null && !imageName.isEmpty()) {
                try {
                    return ResourcesCompat.getDrawable(
                        c.resources,
                        getDrawableIdentifierFromString(c, imageName),
                        null
                    )
                } catch (e: Resources.NotFoundException) {
                    return null
                }
            } else
                return null
        }

        private fun getDrawableIdentifierFromString(c: Context, imageName: String?): Int {
            return if (imageName != null && !imageName.isEmpty()) {
                c.resources.getIdentifier(imageName, "drawable", c.packageName)
            } else
                0
        }

    }

}