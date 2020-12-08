package sheridan.simeoni.gradetracker.helper

import androidx.room.TypeConverter
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DateConverters {
    companion object{

        private val dateFormatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy")

        fun formatDate(date: Date): String {
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .format(dateFormatter)
        }

        @TypeConverter
        fun fromTimestamp(value: Long): Date {
            return Date(value)
        }

//        @TypeConverter
//        fun dateToTimestamp(date: Date): Long {
//            return if (date == null) null else date.getTime()
//        }

    }
}