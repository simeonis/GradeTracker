package sheridan.simeoni.gradetracker.ui.dialog


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.ui.term.TermViewModel
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding
import sheridan.simeoni.gradetracker.model.GradeCalculator


import java.text.SimpleDateFormat
import java.util.*


class TermDialog : DialogFragment() {

    private val termViewModel: TermViewModel by viewModels()
    private val safeArgs: TermDialogArgs by navArgs()
    private lateinit var binding: DialogTermBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DialogTermBinding.inflate(inflater, container, false)

        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        binding.doneButton.setOnClickListener { submit() }
        binding.cancelButton.setOnClickListener { dismiss() }
        binding.dialogTermNameInput.setOnClickListener {
            binding.dialogTermNameWrapper.error = null
            binding.dialogTermNameWrapper.isErrorEnabled = false
        }
        binding.dialogTermNameInput.addTextChangedListener {
            binding.dialogTermNameWrapper.error = null
            binding.dialogTermNameWrapper.isErrorEnabled = false
        }
        binding.dialogTermStartInput.setOnClickListener {
            binding.dialogTermStartWrapper.error = null
            binding.dialogTermStartWrapper.isErrorEnabled = false
        }

        binding.dialogTermStartInput.addTextChangedListener {
            binding.dialogTermStartWrapper.error = null
            binding.dialogTermStartWrapper.isErrorEnabled = false
        }
        binding.dialogTermEndInput.setOnClickListener {
            binding.dialogTermEndWrapper.error = null
            binding.dialogTermEndWrapper.isErrorEnabled = false
        }

        binding.dialogTermEndInput.addTextChangedListener {
            binding.dialogTermEndWrapper.error = null
            binding.dialogTermEndWrapper.isErrorEnabled = false
        }
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

        val termStart = binding.dialogTermStartInput.text.toString()
        val termEnd = binding.dialogTermEndInput.text.toString()

        if(termName.isEmpty()) {
            if (status) termName = term!!.termName
            else binding.dialogTermNameInput.error = "required"
        }
        if(termStart.isEmpty()){
            if (status) termStart = "TODO"
            else{
                binding.dialogTermStartWrapper.isErrorEnabled = true
                binding.dialogTermStartWrapper.error = "Required"
            }
        }
        if(termEnd.isEmpty()){
            if (status) termEnd = "TODO"
            else {
                binding.dialogTermEndWrapper.isErrorEnabled = true
                binding.dialogTermEndWrapper.error = "Required"
            }
        }

        if ((termName.isNotEmpty() && termStart.isNotEmpty() && termEnd.isNotEmpty()) || status){
            if (status) termViewModel.edit(term!!.id, term.position, termName, term.grade, term.progress, term.start, term.end)
            else{
                val sDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termStart);
                val eDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termEnd);
                val start = sDate!!.getTime()
                val end = eDate!!.getTime()
                termViewModel.add(termName, start, end)
                binding.dialogTermNameWrapper.error = null
            }
            dismiss()
        }
    }
}
