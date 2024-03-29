package sheridan.simeoni.gradetracker.ui.term

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.database.Term

class TermViewModel(application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao

    val terms : LiveData<List<Term>> = gradeTrackerDao.getAllTerms()

    fun add (termName: String, start : Long, end : Long) {
        viewModelScope.launch {
            val position = gradeTrackerDao.getTermRowCount()
            gradeTrackerDao.insert(Term(0, position, termName, -1.0f, start, end))
        }
    }

    fun edit (id: Long, position: Int, name: String, grade: Float, start: Long, end : Long) {
        viewModelScope.launch {
            gradeTrackerDao.updateTerm(Term(id, position, name, grade, start, end))
        }
    }

    fun update(newTerm: List<Term>) {
        viewModelScope.launch {
            gradeTrackerDao.updateTerms(newTerm)
        }
    }

    fun delete (termID: Long) {
        viewModelScope.launch {
            gradeTrackerDao.deleteTerm(termID)
        }
    }
}