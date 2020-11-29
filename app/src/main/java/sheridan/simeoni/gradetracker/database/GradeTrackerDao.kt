package sheridan.simeoni.gradetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GradeTrackerDao {

    @Insert
    suspend fun insert(term: Term)

    @Insert
    suspend fun insert(course: Course)

    @Insert
    suspend fun insert(assignment: Assignment)

    @Query("SELECT * FROM Term ORDER BY Position")
    fun getAllTerms() : LiveData<List<Term>>

    @Query("SELECT * FROM Term WHERE TermID=:key")
    fun get(key: Long) : LiveData<Term>

    @Query("SELECT * FROM Course WHERE TermID=:key")
    fun getAllCourses(key: Long) : LiveData<List<Course>>

    @Query("SELECT * FROM Course WHERE CourseID=:key")
    fun getCourse(key: Long) : LiveData<Course>

    @Query("SELECT * FROM Assignment WHERE CourseID=:key")
    fun getAllAssignments(key : Long) : LiveData<List<Assignment>>

    @Query("DELETE FROM Term WHERE TermID=:key")
    suspend fun deleteTerm(key: Long)

    @Query("DELETE FROM Course WHERE CourseID=:key")
    suspend fun deleteCourse(key: Long)

    @Query("DELETE FROM Assignment WHERE AssignmentID=:key")
    suspend fun deleteAssignment(key: Long)

    @Query("DELETE FROM Term")
    suspend fun deleteAll()

    @Update(entity = Term::class)
    suspend fun updateTerms(terms: List<Term>)

    @Update(entity = Course::class)
    suspend fun updateCourses(courses: List<Course>)

    @Update(entity = Assignment::class)
    suspend fun updateAssignments(assignments: List<Assignment>)

    @Query("SELECT COUNT(TermID) FROM Term")
    suspend fun getRowCount(): Int

    @Query("SELECT * FROM Assignment WHERE CourseID=:key")
    suspend fun getGradesInCourse(key: Long): List<Assignment>

    @Query("UPDATE Course SET CourseGrade = :grade WHERE CourseID = :key")
    suspend fun updateCourseGrade(key: Long, grade: Int): Int

    @Query("SELECT * FROM Course WHERE TermID=:key")
    suspend fun getCourseInfo(key: Long): List<Course>?

    @Query("UPDATE Term SET Average = :avg, Progress = :currentProgress WHERE TermID = :key")
    suspend fun updateTerm(key: Long, avg: Int, currentProgress: Int): Int

    @Query("SELECT * FROM Term ORDER BY TermID")
    suspend fun getAllTermsList() : List<Term>

    @Query("SELECT * FROM Course ORDER BY CourseID")
    suspend fun getAllCoursesList() : List<Course>

    @Query("UPDATE Assignment SET Grade = :grade WHERE AssignmentID = :key")
    suspend fun updateAssignmentGrade(key: Long, grade: Int): Int


}