package sheridan.simeoni.gradetracker.ui.course

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.model.GradeCalculator
import sheridan.simeoni.gradetracker.ui.dialog.CourseDialog.CourseDialogData

class CourseViewModel(envelopeKey: Long, application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao
    private val _envelopeKey : Long = envelopeKey

    val courses : LiveData<List<Course>> = gradeTrackerDao.getAllCourses(_envelopeKey)

    fun add (c : CourseDialogData) {
        viewModelScope.launch {
            gradeTrackerDao.insert(Course.from(c, _envelopeKey))
        }
    }

    fun edit (c : CourseDialogData) {
        viewModelScope.launch {
            gradeTrackerDao.updateCourse(Course.from(c, _envelopeKey))
        }
    }

    fun delete (courseID: Long) {
        viewModelScope.launch {
            gradeTrackerDao.deleteCourse(courseID)
            val courses = gradeTrackerDao.getAllCoursesList(_envelopeKey)
            gradeTrackerDao.updateTermGrade(_envelopeKey, GradeCalculator.termAverage(courses))
        }
    }
}