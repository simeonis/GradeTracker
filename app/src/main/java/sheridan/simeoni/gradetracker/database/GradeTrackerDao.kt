package sheridan.simeoni.gradetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GradeTrackerDao {
    @Insert
    suspend fun insert(grade: Grade)

    @Insert
    suspend fun insert(course: Course)

    @Insert
    suspend fun insert(term: Term)

    @Query("SELECT * FROM Grade WHERE id=:key")
    fun get(key: Long) : LiveData<Grade>

    @Transaction
    @Query("SELECT * FROM Course")
    fun getCourseWithAssignments(): List<CourseWithAssignments>

    @Query("SELECT * FROM Grade ORDER BY id")
    fun getAll() : LiveData<List<Grade>>

    @Query("SELECT SUM(Grade) FROM Grade")
    fun getTotal() : LiveData<Int>

    @Query("DELETE FROM Grade")
    suspend fun deleteAll()

}