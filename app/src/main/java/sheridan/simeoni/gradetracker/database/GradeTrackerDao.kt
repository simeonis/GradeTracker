package sheridan.simeoni.gradetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GradeTrackerDao {
    @Insert
    suspend fun insert(savedRoll: Grade): Long

    @Query("SELECT * FROM Grade WHERE id=:key")
    fun get(key: Long) : LiveData<Grade>

    @Query("SELECT * FROM Grade ORDER BY id")
    fun getAll() : LiveData<List<Grade>>

    @Query("SELECT SUM(Grade) FROM Grade")
    fun getTotal() : LiveData<Int>

    @Query("DELETE FROM Grade")
    suspend fun deleteAll()

}