package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Course")
data class Course(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "Course")
    val assignmentTitle: String,

    @ColumnInfo(name = "Course_Grade")
    val grade: Int,

    @ColumnInfo(name = "Course_Target_Grade")
    val targetGrade: Int,

    )