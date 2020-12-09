package sheridan.simeoni.gradetracker.model

import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.Course
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
            return if (list.isNotEmpty() && total > 0)
                total / list.size.toFloat()
            else -1.0f
        }

        // Calculate minimum grade required by assignment passed in to hit target grade
        fun minimumRequirement(assignment: Assignment, course: Course, isAlone : Boolean): Int {
            var grade = removeAssignmentFromAverageGrade(assignment, course)
            if(!isAlone && grade <= 0.0f) grade = this.fillerGrade * (100f - assignment.weight)
            return ceil((((course.targetGrade - grade) / assignment.weight) * assignment.totalPoints)).toInt()
        }

        // Calculate potential course grade base on potentialGrade
        fun calculatePotential(assignment: Assignment, course: Course, potentialGrade: Int): Float {
            val grade = removeAssignmentFromAverageGrade(assignment, course)
            return abs(grade + ((potentialGrade * assignment.totalPoints / 100) / assignment.totalPoints.toFloat() * assignment.weight))
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
            //Striped time from date
            val startStripped = start/100000
            val endStripped = end/100000
            val nowStripped = now/100000
            if(endStripped <= nowStripped) return 100
            val dem = (endStripped - startStripped).toDouble()
            val num = (nowStripped - startStripped).toDouble()
            val progress = if(num < 0) 0 else num/dem * 100f
            return progress.toInt()
        }
    }
}