package sheridan.simeoni.gradetracker.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "Assignment")
data class Assignment(

    @PrimaryKey
    @ColumnInfo(name = "Assignment")
    val assignmentTitle: String,

    @ColumnInfo(name = "AssignmentID")
    val assignmentID: String,

    @ColumnInfo(name = "AssignmentCourseID")
    val assignmentCourseID: String,

    @ColumnInfo(name = "Term")
    val term: String,

)
