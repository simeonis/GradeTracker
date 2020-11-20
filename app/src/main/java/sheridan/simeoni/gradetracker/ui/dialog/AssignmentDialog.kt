package sheridan.simeoni.gradetracker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import sheridan.simeoni.gradetracker.R

class AssignmentDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.Theme_GradeTracker_Dialog)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_assignment, null))
                    .setPositiveButton(android.R.string.ok,null)
                    .setNegativeButton(android.R.string.cancel,null)
                    .create()
        } ?: throw IllegalStateException("Fragment cannot be null")
    }
}