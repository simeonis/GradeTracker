package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Term")
data class Term(

    @PrimaryKey
    @ColumnInfo(name = "Term")
    val assignmentTitle: String,

    @ColumnInfo(name = "Average")
    val grade: Int,

    @ColumnInfo(name = "Num_Classes")
    val weight: Int,

    @ColumnInfo(name = "Progress")
    val targetGrade: Int,
    )