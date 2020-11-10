package sheridan.simeoni.gradetracker.database

import androidx.room.Embedded
import androidx.room.Relation

data class TermWithCourse(
        @Embedded val course: Course,
        @Relation(
                parentColumn = "TermID",
                entityColumn = "CourseTermID"
        )
        val assignemnts: List<Assignment>
)