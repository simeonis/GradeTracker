package sheridan.simeoni.gradetracker.ui.term

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sheridan.simeoni.gradetracker.database.GradeTrackerDao
import sheridan.simeoni.gradetracker.database.GradeTrackerDatabase
import sheridan.simeoni.gradetracker.database.Term
import sheridan.simeoni.gradetracker.model.KeyEnvelope


class TermViewModel(application: Application) : AndroidViewModel(application) {
    private val gradeTrackerDao : GradeTrackerDao = GradeTrackerDatabase.getInstance(application).gradeTrackerDao

    val terms : LiveData<List<Term>> = gradeTrackerDao.getAllTerms()

    fun add (termName: String){
        viewModelScope.launch {
            gradeTrackerDao.insert(Term(0, termName, -1, 0))
        }
    }

    fun delete (termID: Long) {
        viewModelScope.launch {
            gradeTrackerDao.deleteTerm(termID)
        }
    }
}