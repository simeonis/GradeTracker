package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Grade")
data class Grade(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "Assignment_Name")
    val assignmentTitle: String,

    @ColumnInfo(name = "GradeAssignmentID")
    val gradeAssignmentID: String,

    @ColumnInfo(name = "Grade")
    val grade: Int,

    @ColumnInfo(name = "Weight")
    val weight: Int,

    @ColumnInfo(name = "Target_Grade")
    val targetGrade: Int,

    )