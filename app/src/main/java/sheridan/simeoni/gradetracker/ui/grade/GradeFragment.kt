package sheridan.simeoni.gradetracker.ui.grade

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.databinding.FragmentGradeBinding
import sheridan.simeoni.gradetracker.ui.course.CourseViewModel
import sheridan.simeoni.gradetracker.ui.course.CourseViewModelFactory

class GradeFragment : Fragment() {

    private lateinit var binding: FragmentGradeBinding
    private val safeArgs: GradeFragmentArgs by navArgs()
    private val viewModel: GradeViewModel by viewModels {
        GradeViewModelFactory(safeArgs.keyEnvelope.key, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentGradeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        activity?.title = safeArgs.keyEnvelope.title

        viewModel.assignment.observe(viewLifecycleOwner) {
            binding.gradeEarnedInput.hint = String.format("%s/%s", if (it.points == -1) "-" else it.points, it.totalPoints)
            binding.gradeSeekBar.progress = if (it.points == -1) 0 else (it.points/it.totalPoints.toFloat()*100).toInt()
        }
        viewModel.course.observe(viewLifecycleOwner) {
            binding.gradeTotalNumberLabel.text = String.format("%.1f%%", it.grade)
            binding.gradeTotalProgress.progress = if (it.grade == -1f) 0 else it.grade.toInt()
        }
        binding.gradeEarnedInput.setOnEditorActionListener {
            _, event, _ ->
            if(event == EditorInfo.IME_ACTION_DONE) {
                updateGrade()
                true
            }
            else { false }
        }
        binding.gradeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO: Create business method to see possible grades based on seekBar
                possibleGrade(progress)
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
        } else {
            viewModel.updateGrade(safeArgs.keyEnvelope.key, gradeEarned.text.toString().toInt())
            gradeEarned.hint = String.format("%d/50", gradeEarned.text.toString().toInt())
            gradeEarned.setText("")
        }
    }

    private fun possibleGrade(progress: Int) {
        val assignment = viewModel.assignment.value
        binding.gradeEarnedInput.hint =
                String.format("%s/%s", assignment?.totalPoints?.times(progress/100f)?.toInt(),
                        assignment?.totalPoints)
    }
}