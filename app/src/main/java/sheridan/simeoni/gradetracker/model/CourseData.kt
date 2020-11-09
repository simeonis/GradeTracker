package sheridan.simeoni.gradetracker.model

import java.io.Serializable

data class CourseData(var name: String, var assignmentData: List<AssignmentData>): Serializable