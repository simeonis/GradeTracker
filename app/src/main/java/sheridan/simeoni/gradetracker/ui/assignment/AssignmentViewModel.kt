package sheridan.simeoni.gradetracker.ui.assignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.model.GradeCalculator
import sheridan.simeoni.gradetracker.ui.dialog.AssignmentDialog.AssignmentDialogData

class AssignmentViewModel(envelopeKey: Long, application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao
    private val _envelopeKey : Long = envelopeKey

    val assignments : LiveData<List<Assignment>> = gradeTrackerDao.getAllAssignments(_envelopeKey)
    val course : LiveData<Course> = gradeTrackerDao.getCourse(_envelopeKey)

    fun add (a : AssignmentDialogData){
        viewModelScope.launch {
            val position = gradeTrackerDao.getAssignmentRowCount()
            gradeTrackerDao.insert(Assignment.from(a, _envelopeKey, position))
            update()
        }
    }

    fun edit (a : AssignmentDialogData) {
        viewModelScope.launch {
            gradeTrackerDao.updateAssignment(Assignment.from(a, _envelopeKey))
            update()
        }
    }

    fun update(newAssignments: List<Assignment>) {
        viewModelScope.launch {
            gradeTrackerDao.updateAssignments(newAssignments)
        }
    }

    fun delete (assignmentID: Long) {
        viewModelScope.launch {
            gradeTrackerDao.deleteAssignment(assignmentID)
            update()
        }
    }

    private suspend fun update() = withContext(Dispatchers.Default) {
        val course = gradeTrackerDao.getCourseData(_envelopeKey)
        val assignments = gradeTrackerDao.getAllAssignmentsList(course.id)
        gradeTrackerDao.updateCourseGrade(course.id, GradeCalculator.calculateGrade(assignments))
        val courses = gradeTrackerDao.getAllCoursesList(course.termID)
        gradeTrackerDao.updateTermGrade(course.termID, GradeCalculator.termAverage(courses))
    }
}