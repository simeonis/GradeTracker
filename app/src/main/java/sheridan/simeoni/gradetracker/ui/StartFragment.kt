package sheridan.simeoni.gradetracker.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentStartBinding
import sheridan.simeoni.gradetracker.model.*


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("filler_grade"))
            GradeCalculator.fillerGrade = sharedPreferences.getFloat("filler_grade", 1f)

        binding.startBeginButton.setOnClickListener {
            if(!sharedPreferences.contains("filler_grade")){
                findNavController().navigate(R.id.action_start_to_data)
            }else{
                findNavController().navigate(R.id.action_start_to_term)
            }
        }

        return binding.root
    }
}