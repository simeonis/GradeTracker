package sheridan.simeoni.gradetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GradeTrackerDao {

    @Insert
    suspend fun insert(term: Term)

    @Insert
    suspend fun insert(course: Course)

    @Insert
    suspend fun insert(assignment: Assignment)

    @Query("SELECT * FROM Term ORDER BY TermID")
    fun getAllTerms() : LiveData<List<Term>>

    @Query("SELECT * FROM Course WHERE TermID=:key")
    fun getAllCourses(key : Long) : LiveData<List<Course>>

    @Query("SELECT * FROM Assignment WHERE CourseID=:key")
    fun getAllAssignments(key : Long) : LiveData<List<Assignment>>

    @Transaction
    @Query("SELECT * FROM Course")
    fun getCourseWithAssignments(): List<CourseWithAssignments>

}