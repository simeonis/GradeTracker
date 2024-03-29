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
import sheridan.simeoni.gradetracker.helper.DateConverters
import sheridan.simeoni.gradetracker.helper.KeyboardManager
import sheridan.simeoni.gradetracker.ui.term.TermViewModel
import java.text.SimpleDateFormat
import java.util.*

class TermDialog : DialogFragment() {

    private val termViewModel: TermViewModel by viewModels()
    private val safeArgs: TermDialogArgs by navArgs()
    private lateinit var binding: DialogTermBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogTermBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(safeArgs.status.edit){ initEdit() } // Couldn't use data-binding due to Long -> Date conversion

        binding.doneButton.setOnClickListener { submit() }
        binding.cancelButton.setOnClickListener { dismiss() }
        binding.dialogTermNameInput.setOnClickListener {
            binding.dialogTermNameWrapper.apply{
                error = null
                isErrorEnabled = false
            }
        }
        binding.dialogTermNameInput.addTextChangedListener {
            binding.dialogTermNameWrapper.apply{
                error = null
                isErrorEnabled = false
            }
        }
        binding.dialogTermStartDate.setOnClickListener {
            binding.dialogTermStartWrapper.apply{
                error = null
                isErrorEnabled = false
            }
            datePicker(binding.dialogTermStartDate)
        }

        binding.dialogTermEndDate.setOnClickListener {
            binding.dialogTermEndWrapper.apply{
                error = null
                isErrorEnabled = false
            }
            KeyboardManager.hideKeyboard(requireActivity())
            datePicker(binding.dialogTermEndDate)
        }
        return binding.root
    }

    private fun initEdit() {
        val term = safeArgs.status.term!!
        binding.dialogTermTitleLabel.text = getString(R.string.edit_term)
        binding.dialogTermNameInput.hint = term.termName
        binding.dialogTermStartDate.hint = getDate(term.start)
        binding.dialogTermEndDate.hint = getDate(term.end)
    }

    private fun submit(){
        val status = safeArgs.status.edit
        val term = safeArgs.status.term
        var termName = binding.dialogTermNameInput.text.toString()
        var termStart = binding.dialogTermStartDate.text.toString()
        var termEnd = binding.dialogTermEndDate.text.toString()
        var validated = true

        if(termName.isEmpty()) {
            if (status) termName = term!!.termName
            else { binding.dialogTermNameWrapper.error = "required"; validated = false }
        }
        if(termStart.isEmpty()){
            if (status) termStart = getDate(term!!.start)
            else {
                validated = false
                binding.dialogTermStartWrapper.apply{
                    isErrorEnabled = true
                    error = "Required"
                }
            }
        }
        if(termEnd.isEmpty()){
            if (status) termEnd = getDate(term!!.end)
            else {
                validated = false
                binding.dialogTermEndWrapper.apply{
                    isErrorEnabled = true
                    error = "Required"
                }
            }
        }
        if (validated){
            val sDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termStart)
            val eDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termEnd)
            val start = sDate!!.time
            val end = eDate!!.time

            if((end - start) < 0){
               binding.dialogTermEndWrapper.apply{
                   isErrorEnabled = true
                   error = "Invalid date range"
               }
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
        val calendar = Calendar.getInstance()
        val calendarDay = calendar[Calendar.DAY_OF_MONTH]
        val calendarMonth = calendar[Calendar.MONTH]
        val calendarYear = calendar[Calendar.YEAR]

        // Date picker dialog
        val picker = DatePickerDialog(this.binding.root.context,
                { _, year, monthOfYear, dayOfMonth ->
                    text.setText(getString(R.string.date_picker_string, dayOfMonth, monthOfYear + 1, year))
                }, calendarYear, calendarMonth, calendarDay)
        picker.show()
    }

    private fun getDate(time : Long): String{
        val tempDate = DateConverters.fromTimestamp(time)
        return DateConverters.formatDate(tempDate)
    }
}
