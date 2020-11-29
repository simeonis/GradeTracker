package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

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

    @ColumnInfo(name = "CourseGrade")
    val grade: Float,

    @ColumnInfo(name = "CourseTargetGrade")
    val targetGrade: Float,
    )