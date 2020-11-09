package sheridan.simeoni.gradetracker.model

import java.io.Serializable

data class GradeData(var grade: Int, var totalGrade: Int, var weight: Float, var targetGrade: Int): Serializable