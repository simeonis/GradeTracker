package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Assignment")
data class Assignment(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "Assignment")
    val assignmentTitle: String,

    )