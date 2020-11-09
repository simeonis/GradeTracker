package sheridan.simeoni.gradetracker.model

import java.io.Serializable

data class TermData(var name: String, var courseData: List<CourseData>): Serializable