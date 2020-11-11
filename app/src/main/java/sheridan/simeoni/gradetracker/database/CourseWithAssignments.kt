package sheridan.simeoni.gradetracker.database

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithAssignments(
        @Embedded val course: Course,
        @Relation(
                parentColumn = "CourseID",
                entityColumn = "CourseID"
        )
        val assignments: List<Assignment>
)