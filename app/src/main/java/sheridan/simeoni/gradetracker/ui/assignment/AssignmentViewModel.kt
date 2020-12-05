package sheridan.simeoni.gradetracker.ui.assignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.model.GradeCalculator

class AssignmentViewModel(envelopeKey: Long, application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao
    private val _envelopeKey : Long = envelopeKey

    val assignments : LiveData<List<Assignment>> = gradeTrackerDao.getAllAssignments(_envelopeKey)
    val course : LiveData<Course> = gradeTrackerDao.getCourse(_envelopeKey)

    fun add (assignmentName: String, points: Int, totalPoints: Int, weight: Float){
        viewModelScope.launch {
            gradeTrackerDao.insert(Assignment(0, _envelopeKey, assignmentName, points, totalPoints, weight))

            //Update Upper-level Table
            val course = gradeTrackerDao.getCourseData(_envelopeKey)

            val assignments = gradeTrackerDao.getAllAssignmentsList(course.id)
            gradeTrackerDao.updateCourseGrade(course.id, GradeCalculator.calculateGrade(assignments))

            val courses = gradeTrackerDao.getAllCoursesList(course.termID)
            gradeTrackerDao.updateTermGrade(course.termID, GradeCalculator.termAverage(courses))
        }
    }

    fun edit (id: Long, assignmentName: String, points: Int, totalPoints: Int, weight: Float) {
        viewModelScope.launch {
            gradeTrackerDao.updateAssignment(Assignment(id, _envelopeKey, assignmentName, points, totalPoints, weight))

            //Update Upper-level Table
            val course = gradeTrackerDao.getCourseData(_envelopeKey)

            val assignments = gradeTrackerDao.getAllAssignmentsList(course.id)
            gradeTrackerDao.updateCourseGrade(course.id, GradeCalculator.calculateGrade(assignments))

            val courses = gradeTrackerDao.getAllCoursesList(course.termID)
            gradeTrackerDao.updateTermGrade(course.termID, GradeCalculator.termAverage(courses))
        }
    }

    fun delete (assignmentID: Long) {
        viewModelScope.launch {
            //Delete Assignment
            gradeTrackerDao.deleteAssignment(assignmentID)

            //Update Upper-level Table
            val course = gradeTrackerDao.getCourseData(_envelopeKey)

            val assignments = gradeTrackerDao.getAllAssignmentsList(course.id)
            gradeTrackerDao.updateCourseGrade(course.id, GradeCalculator.calculateGrade(assignments))

            val courses = gradeTrackerDao.getAllCoursesList(course.termID)
            gradeTrackerDao.updateTermGrade(course.termID, GradeCalculator.termAverage(courses))
        }
    }
}