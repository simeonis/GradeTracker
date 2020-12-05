package sheridan.simeoni.gradetracker.ui.dialog


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.ui.term.TermViewModel
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding

class TermDialog : DialogFragment() {

    private val termViewModel: TermViewModel by viewModels()
    private val safeArgs: TermDialogArgs by navArgs()
    private lateinit var binding: DialogTermBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DialogTermBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (safeArgs.status.edit) { initEdit() }

        binding.doneButton.setOnClickListener { submit() }
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun initEdit() {
        val term = safeArgs.status.term!!
        binding.dialogTermTitleLabel.text = getString(R.string.edit_term)
        binding.dialogTermNameInput.hint = term.termName
        binding.dialogTermStartDate.hint = "TODO"
        binding.dialogTermEndDate.hint = "TODO"
    }

    private fun submit(){
        val status = safeArgs.status.edit
        val term = safeArgs.status.term
        var termName = binding.dialogTermNameInput.text.toString()

        if(termName.isEmpty()) {
            if (status) termName = term!!.termName
            else binding.dialogTermNameInput.error = "required"
        }
        if (termName.isNotEmpty() || status){
            if (status) termViewModel.edit(term!!.id, term.position, termName, term.grade, term.progress)
            else termViewModel.add(termName)
            dismiss()
        }
    }
}
