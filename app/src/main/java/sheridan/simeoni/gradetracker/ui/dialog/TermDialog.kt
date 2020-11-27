package sheridan.simeoni.gradetracker.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding
import sheridan.simeoni.gradetracker.ui.term.TermViewModel

class TermDialog : DialogFragment() {

    private val termViewModel: TermViewModel by viewModels()
    private lateinit var binding: DialogTermBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogTermBinding.inflate(inflater, container, false)

        binding.doneButton.setOnClickListener { submit() }
        binding.cancelButton.setOnClickListener { dismiss() }
        return binding.root
    }

    private fun submit(){
        val termName = binding.dialogTermNameInput.text.toString()
        if(termName.isEmpty()){
            binding.dialogTermNameInput.error = "required"
        }
        else {
            termViewModel.add(termName)
            dismiss()
        }
    }
}