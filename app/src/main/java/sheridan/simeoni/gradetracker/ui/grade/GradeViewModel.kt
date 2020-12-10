package sheridan.simeoni.gradetracker.ui.grade

import android.app.Application
import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.model.GradeCalculator

class GradeViewModel(envelopeKey: Long, application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao
    private val _envelopeKey : Long = envelopeKey

    val assignment : LiveData<Assignment> = gradeTrackerDao.getAssignment(_envelopeKey)
    val course : LiveData<Course> = gradeTrackerDao.getCourseFromGrade(_envelopeKey)

    fun updateGrade(points : Int){
        viewModelScope.launch {
            val assignment = gradeTrackerDao.getAssignmentData(_envelopeKey)
            val course = gradeTrackerDao.getCourseData(assignment.courseID)
            gradeTrackerDao.updateAssignmentGrade(_envelopeKey, points)
            val assignments = gradeTrackerDao.getAllAssignmentsList(assignment.courseID)
            gradeTrackerDao.updateCourseGrade(course.id, GradeCalculator.calculateGrade(assignments))
            val courses = gradeTrackerDao.getAllCoursesList(course.termID)
            gradeTrackerDao.updateTermGrade(course.termID, GradeCalculator.termAverage(courses))
        }
    }

    fun setRequired(gradeFractionLabel: TextView, gradePercentageLabel: TextView) {
        viewModelScope.launch {
            val assignment = gradeTrackerDao.getAssignmentData(_envelopeKey)
            val assignments = gradeTrackerDao.getAllAssignmentsList(assignment.courseID)
            val course = gradeTrackerDao.getCourseData(assignment.courseID)
            val points = GradeCalculator.minimumRequirement(assignment, course, assignments.size < 2 )
            gradeFractionLabel.text = String.format("%s/%s", points, assignment.totalPoints)
            gradePercentageLabel.text = String.format("%.1f%%", (points / assignment.totalPoints.toFloat()*100))
        }
    }

    fun setPotential(gradeTotalNumberLabel: TextView, gradeTotalProgress: ProgressBar, progress: Int) {
        viewModelScope.launch {
            val assignment = gradeTrackerDao.getAssignmentData(_envelopeKey)
            val course = gradeTrackerDao.getCourseData(assignment.courseID)
            val result = GradeCalculator.calculatePotential(assignment, course, progress)
            gradeTotalNumberLabel.text = String.format("%.1f%%", result)
            gradeTotalProgress.progress = result.toInt()
        }
    }
}

