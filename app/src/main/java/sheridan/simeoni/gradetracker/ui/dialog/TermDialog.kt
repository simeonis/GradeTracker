package sheridan.simeoni.gradetracker.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.ui.term.TermViewModel
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermBinding

class TermDialog : DialogFragment() {

    private val termViewModel: TermViewModel by viewModels()
    private lateinit var binding: DialogTermBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogTermBinding.inflate(inflater, container, false)

        binding.doneButton.setOnClickListener {
            val termName = binding.dialogTermNameInput.text.toString()
            submit(termName)
            dismiss()
        }
        return binding.root
    }

    private fun submit(name : String){
        termViewModel.add(name)
    }
}