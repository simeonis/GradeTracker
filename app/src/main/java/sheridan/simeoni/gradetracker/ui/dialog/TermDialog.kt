package sheridan.simeoni.gradetracker.ui.dialog


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import sheridan.simeoni.gradetracker.databinding.DialogTermBinding
import sheridan.simeoni.gradetracker.model.GradeCalculator
import sheridan.simeoni.gradetracker.ui.term.TermViewModel

import java.text.SimpleDateFormat
import java.util.*


class TermDialog : DialogFragment() {

    private val termViewModel: TermViewModel by viewModels()
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

    private fun submit(){
        val termName = binding.dialogTermNameInput.text.toString()
        val termStart = binding.dialogTermStartInput.text.toString()
        val termEnd = binding.dialogTermEndInput.text.toString()
        if(termName.isEmpty()){
            binding.dialogTermNameWrapper.isErrorEnabled = true
            binding.dialogTermNameWrapper.error = "Required"
        }
        if(termStart.isEmpty()){
            binding.dialogTermStartWrapper.isErrorEnabled = true
            binding.dialogTermStartWrapper.error = "Required"
        }
        if(termEnd.isEmpty()){
            binding.dialogTermEndWrapper.isErrorEnabled = true
            binding.dialogTermEndWrapper.error = "Required"
        }
        else{

            val sDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termStart);
            val eDate = SimpleDateFormat("dd/MM/yyyy", Locale.CANADA).parse(termEnd);
            val start = sDate!!.getTime()
            val end = eDate!!.getTime()
            termViewModel.add(termName, start, end)
            binding.dialogTermNameWrapper.error = null
            dismiss()
        }

    }
}
