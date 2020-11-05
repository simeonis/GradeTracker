package sheridan.simeoni.gradetracker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import sheridan.simeoni.gradetracker.R
import java.lang.IllegalStateException

class AssignmentDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder
                    .setView(inflater.inflate(R.layout.dialog_assignment, null))
                    .setTitle("Add Assignment")
                    .setPositiveButton(android.R.string.ok,null)
                    .setNegativeButton(android.R.string.cancel,null)
                    .create()
        } ?: throw IllegalStateException("Fragment cannot be null")
    }
}