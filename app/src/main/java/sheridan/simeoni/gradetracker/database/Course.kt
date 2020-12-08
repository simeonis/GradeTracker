package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

data class CourseStatus(
        val edit: Boolean,
        val course: CourseData?
): Serializable

data class CourseData(
        val id: Long,
        val termID: Long,
        val courseName: String,
        val courseCode: String,
        val grade: Float,
        val targetGrade: Float
)

@Entity(tableName = "Course", foreignKeys = [ForeignKey(
        entity = Term::class,
        parentColumns = ["TermID"],
        childColumns = ["TermID"],
        onDelete = CASCADE)])
data class Course(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CourseID")
    var id: Long,

    @ColumnInfo(name = "TermID")
    val termID: Long,

    @ColumnInfo(name = "CourseName")
    val courseName: String,

    @ColumnInfo(name = "CourseCode")
    val courseCode: String,

    @ColumnInfo(name = "CourseGrade")
    val grade: Float,

    @ColumnInfo(name = "CourseTargetGrade")
    val targetGrade: Float,
) {
    companion object {
        fun from(c: CourseData) : Course {
            return Course(c.id, c.termID, c.courseName,c.courseCode, c.grade, c.targetGrade)
        }
    }
    fun toData(): CourseData {
        return CourseData(id, termID, courseName,courseCode, grade, targetGrade)
    }
}