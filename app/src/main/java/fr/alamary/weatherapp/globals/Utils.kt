package fr.alamary.weatherapp.globals

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object{
        fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
            val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
            parser.timeZone = timeZone
            return parser.parse(this)
        }

        fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
            formatter.timeZone = timeZone
            return formatter.format(this)
        }
    }
}class GlobalUtils {

    companion object{
        fun getDrawableFromString(c: Context, imageName: String?): Drawable? {

            if (imageName != null && !imageName.isEmpty()) {
                try {
                    return ResourcesCompat.getDrawable(c.resources,getDrawableIdentifierFromString(c, imageName),null)
                }catch (e : Resources.NotFoundException){
                    return null
                }
            } else
                return null
        }
        private  fun getDrawableIdentifierFromString(c: Context, imageName: String?): Int {
            return if (imageName != null && !imageName.isEmpty()) {
                c.resources.getIdentifier(imageName, "drawable", c.packageName)
            } else
                0
        }

    }

}