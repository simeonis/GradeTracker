package sheridan.simeoni.gradetracker.database

import androidx.room.Embedded
import androidx.room.Relation

data class CourseWithAssignments(
        @Embedded val course: Course,
        @Relation(
                parentColumn = "courseID",
                entityColumn = "assignmentCourseID"
        )
        val assignemnts: List<Assignment>
)