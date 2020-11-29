package sheridan.simeoni.gradetracker.ui.grade

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import sheridan.simeoni.gradetracker.databinding.FragmentGradeBinding
import sheridan.simeoni.gradetracker.ui.course.CourseFragmentArgs

class GradeFragment : Fragment() {

    private lateinit var binding: FragmentGradeBinding
    private val safeArgs: GradeFragmentArgs by navArgs()
    private val viewModel: GradeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        } else {
            viewModel.updateGrade(gradeEarned.text.toString().toInt())
        }
    }
}