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

    fun add (assignmentName: String, grade: Int, gradeTotal: Int, targetGrade: Int, weight: Float){
        viewModelScope.launch {
            gradeTrackerDao.insert(Assignment(0, _envelopeKey, assignmentName, grade, gradeTotal, targetGrade, weight))

        }
    }

    fun delete (assignmentID: Long) {
        viewModelScope.launch {
            gradeTrackerDao.deleteAssignment(assignmentID)
        }
    }
    fun updateGrade(grade : Int){
        viewModelScope.launch {
            gradeTrackerDao.updateAssignmentGrade(_envelopeKey, grade)
        }
    }
}