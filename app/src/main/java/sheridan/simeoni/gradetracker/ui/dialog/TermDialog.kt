package sheridan.simeoni.gradetracker.ui.dialog


import android.app.DatePickerDialog
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
import com.google.android.material.textfield.TextInputEditText
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding
import sheridan.simeoni.gradetracker.ui.term.TermViewModel
import java.text.SimpleDateFormat
import java.util.*


class TermDialog : DialogFragment() {

    private val termViewModel: TermViewModel by viewModels()
    private val safeArgs: TermDialogArgs by navArgs()
    private lateinit var binding: DialogTermBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DialogTermBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        if(safeArgs.status.edit){ initEdit() }


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
        binding.dialogTermStartDate.setOnClickListener {
            binding.dialogTermStartWrapper.error = null
            binding.dialogTermStartWrapper.isErrorEnabled = false
            datePicker(binding.dialogTermStartDate)
        }

        binding.dialogTermEndDate.setOnClickListener {
            binding.dialogTermEndWrapper.error = null
            binding.dialogTermEndWrapper.isErrorEnabled = false
            datePicker(binding.dialogTermEndDate)
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

        var termStart = binding.dialogTermStartDate.text.toString()
        var termEnd = binding.dialogTermEndDate.text.toString()

        if(termName.isEmpty()) {
            if (status) termName = term!!.termName
            else binding.dialogTermNameWrapper.error = "required"
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


        if ((termName.isNotEmpty() && termStart.isNotEmpty() && termEnd.isNotEmpty()) || status  ){

            val sDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termStart);
            val eDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termEnd);
            val start = sDate!!.getTime()
            val end = eDate!!.getTime()

            if((end - start) < 0){
               binding.dialogTermEndWrapper.isErrorEnabled = true
               binding.dialogTermEndWrapper.error = "Invalid date range"
            }
            else{
                if (status) {
                    termViewModel.edit(term!!.id, term.position, termName, term.grade, start, end)
                }
                else{
                    termViewModel.add(termName, start, end)
                    binding.dialogTermNameWrapper.error = null
                }
                dismiss()
            }
        }
    }

    private fun datePicker(text : TextInputEditText){
        val cldr = Calendar.getInstance()
        val _day = cldr[Calendar.DAY_OF_MONTH]
        val _month = cldr[Calendar.MONTH]
        val _year = cldr[Calendar.YEAR]

        // date picker dialog
        val picker = DatePickerDialog(this.binding.root.context,
                { view, year, monthOfYear, dayOfMonth ->
                    text.setText("$dayOfMonth" + "/" + (monthOfYear + 1) + "/" + year)
                }, _year, _month, _day)
        picker.show()
    }
}
