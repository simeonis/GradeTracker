package sheridan.simeoni.gradetracker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.R

class ConfirmationDialog : DialogFragment() {

    companion object {
        const val CONFIRMATION_RESULT = "confirmation_result"
    }

    private val safeArgs: ConfirmationDialogArgs by navArgs()
    private lateinit var navController: NavController

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        navController = findNavController()

        return AlertDialog.Builder(requireActivity(), R.style.Theme_GradeTracker_Dialog).apply {
            setTitle("Delete Confirmation")
            setMessage(String.format("Are you sure you want to delete %s?", safeArgs.message))
            setPositiveButton(android.R.string.ok) { _, _ -> confirmed() }
            setNegativeButton(android.R.string.cancel) {_, _ -> declined() }
        }.create()
    }

    private fun confirmed() {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set(CONFIRMATION_RESULT, safeArgs.requestCode)
    }

    private fun declined() {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set(CONFIRMATION_RESULT, -1L)
    }
}