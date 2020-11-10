package sheridan.simeoni.gradetracker.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "Assignment", primaryKeys = ["Assignment","Term"])
data class Assignment(

    @ColumnInfo(name = "Assignment")
    val assignmentTitle: String,

    @ColumnInfo(name = "Term")
    val term: String,

)
