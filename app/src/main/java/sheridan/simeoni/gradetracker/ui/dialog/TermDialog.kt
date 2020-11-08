package sheridan.simeoni.gradetracker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import sheridan.simeoni.gradetracker.R

class TermDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it, R.style.CustomDialogBackGround)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_term, null))
                    .setPositiveButton(android.R.string.ok,null)
                    .setNegativeButton(android.R.string.cancel,null)
            builder.create()
        }?: throw IllegalStateException("Fragment cannot be null")
    }
}