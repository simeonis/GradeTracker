package sheridan.simeoni.gradetracker.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog.AssignmentDialogData
import java.io.Serializable

data class AssignmentStatus(
       val edit: Boolean,
       val assignment: AssignmentData?
): Serializable

data class AssignmentData(
        val id: Long,
        val courseID: Long,
        val assignmentName: String,
        val points: Int,
        val totalPoints: Int,
        val weight: Float
)

@Entity(tableName = "Assignment", foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = ["CourseID"],
        childColumns = ["CourseID"],
        onDelete = CASCADE)])
data class Assignment(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AssignmentID")
    var id: Long,

    @ColumnInfo(name = "CourseID")
    val courseID: Long,

    @ColumnInfo(name = "AssignmentName")
    val assignmentName: String,

    @ColumnInfo(name = "Points")
    val points: Int,

    @ColumnInfo(name = "TotalPoints")
    val totalPoints: Int,

    @ColumnInfo(name = "Weight")
    val weight: Float,
) {
   companion object {
       fun from(a: AssignmentData) : Assignment {
           return Assignment(a.id, a.courseID, a.assignmentName, a.points, a.totalPoints, a.weight)
       }
       fun from(a: AssignmentDialogData, courseID: Long) : Assignment {
           return Assignment(a.id, courseID, a.name, -1, a.totalPoints, a.weight)
       }
   }
    fun toData(): AssignmentData {
        return AssignmentData(id, courseID, assignmentName, points, totalPoints, weight)
    }
}
