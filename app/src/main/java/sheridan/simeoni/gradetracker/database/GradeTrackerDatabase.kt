package sheridan.simeoni.gradetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Grade::class], version = 1, exportSchema = false)
abstract class GradeTrackerDatabase : RoomDatabase() {

    abstract val gradeTrackerDao: GradeTrackerDao

    companion object{

        @Volatile
        private var INSTANCE: GradeTrackerDatabase? = null

        fun getInstance(context: Context): GradeTrackerDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GradeTrackerDatabase::class.java,
                        "gradetracker_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}