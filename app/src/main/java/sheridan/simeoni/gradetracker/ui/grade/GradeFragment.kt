package sheridan.simeoni.gradetracker.ui.grade

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.databinding.FragmentGradeBinding

class GradeFragment : Fragment() {

    private lateinit var binding: FragmentGradeBinding
    private val safeArgs: GradeFragmentArgs by navArgs()
    private val viewModel: GradeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentGradeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        activity?.title = safeArgs.keyEnvelope.title

        binding.gradeUpdateButton.setOnClickListener { updateGrade() }
        binding.gradeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.gradeTotalNumberLabel.text = progress.toFloat().toString()
                binding.gradeTotalProgress.progress = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        return binding.root
    }

    private fun updateGrade() {

        val gradeEarned = binding.gradeEarnedInput
        if (gradeEarned.text.isEmpty()) {
            gradeEarned.error = "required"
        }
        else {
            viewModel.updateGrade( safeArgs.keyEnvelope.key,gradeEarned.text.toString().toInt())
            hideKeyboard()
        }
    }
    fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.getWindowToken(), 0)
    }
}