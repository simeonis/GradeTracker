package sheridan.simeoni.gradetracker.ui.grade

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val requiredGrade : LiveData<Pair<Int, Int>> = getRequired()

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

    private fun getRequired() : LiveData<Pair<Int, Int>> {
        val result = MutableLiveData<Pair<Int, Int>>()
        viewModelScope.launch {
            val assignment = gradeTrackerDao.getAssignmentData(_envelopeKey)
            val course = gradeTrackerDao.getCourseData(assignment.courseID)
            val points = GradeCalculator.minimumRequirement(assignment, course)
            result.postValue(Pair(points, assignment.totalPoints))
        }
        return result
    }

    fun getPotential(progress: Int): Float? {
        return assignment.value?.let {
            course.value?.let {
                itCourse -> GradeCalculator.calculatePotential(it, itCourse, progress)
            }
        }
    }
}