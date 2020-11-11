package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Term")
data class Term(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "TermID")
    val termID: String,

    @ColumnInfo(name = "Average")
    val grade: Int,

    @ColumnInfo(name = "Num_Classes")
    val weight: Int,

    @ColumnInfo(name = "Progress")
    val targetGrade: Int,
    )