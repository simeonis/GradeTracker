package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog.CourseDialogData
import java.io.Serializable

data class CourseStatus(
        val edit: Boolean,
        val course: CourseData?
): Serializable

data class CourseData(
        val id: Long,
        val termID: Long,
        val position: Int,
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

    @ColumnInfo(name = "Position")
    var position: Int,

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
            return Course(c.id, c.termID, c.position, c.courseName,c.courseCode, c.grade, c.targetGrade)
        }
        fun from(c: CourseDialogData, termID: Long) : Course {
            return Course(c.id, termID, c.position, c.name, c.courseCode, c.grade, c.targetGrade)
        }
        fun from(c: CourseDialogData, termID: Long, position: Int) : Course {
            return Course(c.id, termID, position, c.name, c.courseCode, c.grade, c.targetGrade)
        }
    }
    fun toData(): CourseData {
        return CourseData(id, termID, position, courseName, courseCode, grade, targetGrade)
    }
}