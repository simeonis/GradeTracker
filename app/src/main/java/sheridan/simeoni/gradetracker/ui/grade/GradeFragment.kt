package sheridan.simeoni.gradetracker.ui.grade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.databinding.FragmentGradeBinding
import sheridan.simeoni.gradetracker.helper.KeyboardManager
import sheridan.simeoni.gradetracker.model.GradeCalculator

class GradeFragment : Fragment() {

    private lateinit var binding: FragmentGradeBinding
    private val safeArgs: GradeFragmentArgs by navArgs()
    private val viewModel: GradeViewModel by viewModels {
        GradeViewModelFactory(safeArgs.keyEnvelope.key, requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGradeBinding.inflate(inflater, container, false)
        activity?.title = safeArgs.keyEnvelope.title

        // Update UI on Assignment LiveData change
        viewModel.assignment.observe(viewLifecycleOwner) {
            if (it.points < 0) {
                binding.gradeSeekBar.progress = (GradeCalculator.fillerGrade * it.totalPoints).toInt()
                binding.gradeEarnedInput.hint = String.format("%s/%s", "-", it.totalPoints)
            } else {
                binding.gradeSeekBar.progress = (it.points / it.totalPoints.toFloat() * 100).toInt()
                binding.gradeEarnedInput.hint = String.format("%s/%s", it.points, it.totalPoints)
            }
        }

        // Update UI on Course LiveData change
        viewModel.course.observe(viewLifecycleOwner) {
            if (it.grade < 0) {
                binding.gradeTotalNumberLabel.text = String.format("%s", "-")
                binding.gradeTotalProgress.progress = 0
            } else {
                binding.gradeTotalNumberLabel.text = String.format("%.1f%%", it.grade)
                binding.gradeTotalProgress.progress = it.grade.toInt()
            }
        }

        // Update Minimum Grade Requirement
        viewModel.setRequired(binding.gradeFractionLabel, binding.gradePercentageLabel, binding.gradeRequirementTip)

        // OnSubmit Listener for Input Text
        binding.gradeEarnedInput.setOnEditorActionListener {
            _, event, _ ->
            if(event == EditorInfo.IME_ACTION_DONE) {
                updateGrade()
                true
            }
            else {false}
        }

        // SeekBar Listener
        binding.gradeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                possibleGrade(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        return binding.root
    }

    private fun updateGrade() {
        val gradeEarned = binding.gradeEarnedInput
        if (gradeEarned.text.isEmpty()) gradeEarned.error = "required"
        else {
            viewModel.updateGrade(gradeEarned.text.toString().toInt())
            gradeEarned.hint = String.format("%d/50", gradeEarned.text.toString().toInt())
            gradeEarned.setText("")
            KeyboardManager.hideKeyboard(requireActivity())
        }
    }

    private fun possibleGrade(progress: Int) {
        val assignment = viewModel.assignment.value
        binding.gradeEarnedInput.hint =
                String.format("%d/%d", assignment?.totalPoints?.times(progress / 100f)?.toInt(),
                        assignment?.totalPoints)
        val result = viewModel.getPotential(progress) ?: 0f
        binding.gradeTotalNumberLabel.text = String.format("%.1f%%", result)
        binding.gradeTotalProgress.progress = result.toInt()
    }
}