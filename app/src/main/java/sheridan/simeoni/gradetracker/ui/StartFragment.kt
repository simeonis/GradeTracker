package sheridan.simeoni.gradetracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.databinding.FragmentStartBinding
import sheridan.simeoni.gradetracker.model.*


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        binding.startBeginButton.setOnClickListener {
            val action = StartFragmentDirections.actionStartToTerm(
                StartData("Select Term",
                    listOf(TermData("Term 1", listOf(CourseData("Course 1", listOf(AssignmentData("Midterm", GradeData(80, 100, 15.0f, 90)))))),
                        TermData("Term 2", listOf(CourseData("Course 1", listOf(AssignmentData("Quiz 1", GradeData(8, 10, 15.0f, 9)))))))))
            findNavController().navigate(action)
        }

        return binding.root
    }
}