package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Term")
data class Term(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TermID")
    var id: Long,

    @ColumnInfo(name = "Position")
    var position: Int,

    @ColumnInfo(name = "TermName")
    val termName: String,

    @ColumnInfo(name = "Average")
    val average: Int,

    @ColumnInfo(name = "Progress")
    val progress: Int
    )