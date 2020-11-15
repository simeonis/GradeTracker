package sheridan.simeoni.gradetracker.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import sheridan.simeoni.gradetracker.ui.term.TermViewModel
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding

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
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun submit(name : String){
        termViewModel.add(name)
    }
}