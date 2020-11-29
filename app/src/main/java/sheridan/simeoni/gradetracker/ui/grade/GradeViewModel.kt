package sheridan.simeoni.gradetracker.ui.grade

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.model.GradeCalculator

class GradeViewModel(application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao

    fun updateGrade(assignmentId : Long, points : Int){
        viewModelScope.launch {

            val assignment = gradeTrackerDao.getAssignmentData(assignmentId)
            val course = gradeTrackerDao.getCourseData(assignment.courseID)

            gradeTrackerDao.updateAssignmentGrade(assignmentId, points)

            val assignments = gradeTrackerDao.getAllAssignmentsList(assignment.courseID)
            gradeTrackerDao.updateCourseGrade(course.id, GradeCalculator.calculateGrade(assignments))

            val courses = gradeTrackerDao.getAllCoursesList(course.termID)
            gradeTrackerDao.updateTermGrade(course.termID, GradeCalculator.termAverage(courses))

        }
    }
}