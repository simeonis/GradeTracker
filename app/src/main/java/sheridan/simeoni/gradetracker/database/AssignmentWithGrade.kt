package sheridan.simeoni.gradetracker.database

import androidx.room.Embedded
import androidx.room.Relation

data class AssignmentWithGrade(
        @Embedded val course: Course,
        @Relation(
                parentColumn = "AssignmentID",
                entityColumn = "GradeAssignmentID"
        )
        val assignemnts: List<Assignment>
)