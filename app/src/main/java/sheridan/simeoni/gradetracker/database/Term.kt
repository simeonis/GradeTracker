package sheridan.simeoni.gradetracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class TermStatus(
        val edit: Boolean,
        val term: TermData?
) : Serializable

data class TermData(
        val id: Long,
        val position: Int,
        val termName: String,
        val grade: Float,
        val start: Long,
        val end: Long
)

@Entity(tableName = "Term")
data class Term(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TermID")
    var id: Long,

    @ColumnInfo(name = "Position")
    var position: Int,

    @ColumnInfo(name = "TermName")
    val termName: String,

    @ColumnInfo(name = "TermGrade")
    val grade: Float,

    @ColumnInfo(name = "Start")
    val start: Long,

    @ColumnInfo(name = "End")
    val end: Long
) {
    companion object {
        fun from(t: TermData) : Term {
            return Term(t.id, t.position, t.termName, t.grade,t.start, t.end)
        }
    }
    fun toData(): TermData {
        return TermData(id, position, termName, grade, start, end)
    }
}
