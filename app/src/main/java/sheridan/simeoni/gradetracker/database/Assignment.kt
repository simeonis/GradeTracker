package sheridan.simeoni.gradetracker.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

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

    @ColumnInfo(name = "Grade")
    val grade: Int,

    @ColumnInfo(name = "GradeTotal")
    val gradeTotal: Int,

    @ColumnInfo(name = "GradeTarget")
    val gradeTarget: Int,

    @ColumnInfo(name = "Weight")
    val weight: Float,
)
