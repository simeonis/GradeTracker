package sheridan.simeoni.gradetracker.ui.course

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.database.Term
import sheridan.simeoni.gradetracker.model.KeyEnvelope
import java.security.Key

class CourseViewModel(envelopeKey: Long, application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao
    private val _envelopeKey : Long = envelopeKey

    val courses : LiveData<List<Course>> = gradeTrackerDao.getAllCourses(_envelopeKey)

    fun add (courseName : String, targetGrade: Int){
        viewModelScope.launch {
            gradeTrackerDao.insert(Course(0, _envelopeKey, courseName, -1, targetGrade))
        }
    }

    fun delete (courseID: Long) {
        viewModelScope.launch {
            gradeTrackerDao.deleteCourse(courseID)
        }
    }
}