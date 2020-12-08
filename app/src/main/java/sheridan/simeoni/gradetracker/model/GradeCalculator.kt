package sheridan.simeoni.gradetracker.model

import android.util.Log
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.Course
import java.time.Instant
import java.time.ZoneId
import kotlin.math.abs
import kotlin.math.ceil

class GradeCalculator {
    companion object {
        var fillerGrade = .7f // Dynamically changed by savedPreferences

        // Course Grade
        fun calculateGrade(list: List<Assignment>): Float {
            var totalGrade = 0.0f

            var fillerCounter = 0
            for (item in list) {
                var weightedGrade: Float
                if (item.points != -1)
                    weightedGrade = (item.points / item.totalPoints.toFloat()) * (item.weight / 100f)
                else {
                    weightedGrade = fillerGrade * (item.weight / 100f)
                    fillerCounter++
                }
                totalGrade += weightedGrade
            }
            if (fillerCounter == list.size) return -1f
            return totalGrade * 100f
        }

        // Calculate Average
        fun termAverage(list: List<Course>): Float {
            var total = 0.0f
            for (item in list) {
                if (item.grade != -1f)
                    total += item.grade
            }
            return if (list.isNotEmpty())
                total / list.size.toFloat()
            else -1.0f
        }

        // Calculate minimum grade required by assignment passed in to hit target grade
        fun minimumRequirement(assignment: Assignment, course: Course): Int {
            val grade = removeAssignmentFromAverageGrade(assignment, course)
            return ceil((((course.targetGrade - grade) / assignment.weight) * assignment.totalPoints)).toInt()
        }

        // Calculate potential course grade base on potentialGrade
        fun calculatePotential(assignment: Assignment, course: Course, potentialGrade: Int): Float {
            val grade = removeAssignmentFromAverageGrade(assignment, course)
            return grade + ((potentialGrade * assignment.totalPoints / 100) / assignment.totalPoints.toFloat() * assignment.weight)
        }

        // Removes assignment grade from average course grade
        private fun removeAssignmentFromAverageGrade(assignment: Assignment, course: Course): Float {
            val assignmentGrade =
                    // Course Average: Non blank | Assignment Grade: blank ---> Filler grade was used
                    if (course.grade >= 0 && assignment.points < 0) this.fillerGrade * (100f - assignment.weight)
                    // Course Average: blank | Assignment Grade: blank ---> No grade i.e. -1
                    else if (course.grade < 0 && assignment.points < 0) -1f
                    // Otherwise ...
                    else (assignment.points / assignment.totalPoints.toFloat() * assignment.weight)
            // Remove old Assignment Grade from Course Average
            return course.grade - assignmentGrade
        }

        fun getTermProgress(start: Long, end: Long, now: Long): Int {
            val dem = (end - start).toDouble()
            val num = (now - start).toDouble()
            val progress = abs(num)/ abs(dem) * 100f
            return progress.toInt()
        }
    }
}