package sheridan.simeoni.gradetracker.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.DialogConfirmationBinding

private lateinit var binding: DialogConfirmationBinding
class ConfirmationDialog : DialogFragment() {

    companion object {
        const val CONFIRMATION_RESULT = "confirmation_result"
    }

    private val safeArgs: ConfirmationDialogArgs by navArgs()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogConfirmationBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        navController = findNavController()

        isCancelable = false
        binding.doneButton.setOnClickListener { confirmed() }
        binding.cancelButton.setOnClickListener { declined() }

        binding.dialogConfirmationTitleLabel.text = getString(R.string.delete_confirmation)
        binding.dialogConfirmationMsg.text = String.format("Are you sure you want to delete %s?", safeArgs.message)
        return binding.root
    }

    private fun confirmed() {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set(CONFIRMATION_RESULT, safeArgs.requestCode)
        dismiss()
    }

    private fun declined() {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set(CONFIRMATION_RESULT, -1L)
        dismiss()
    }
}