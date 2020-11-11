package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Course")
data class Course(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CourseID")
    var id: Long,

    @ColumnInfo(name = "TermID")
    val termID: Long,

    @ColumnInfo(name = "CourseName")
    val courseName: String,

    @ColumnInfo(name = "CourseGrade")
    val grade: Int,

    @ColumnInfo(name = "CourseTargetGrade")
    val targetGrade: Int,
    )