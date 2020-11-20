package sheridan.simeoni.gradetracker.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentDataBinding

class DataFragment : Fragment() {
    private lateinit var binding : FragmentDataBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentDataBinding.inflate(inflater, container, false)
        binding.dataNextButton.setOnClickListener { next() }


        return binding.root
    }

    private fun next(){
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val name = binding.fragmentDataNameInput.text.toString()
        val fillerGrade = binding.fragmentDataFillerGrade.text.toString().toInt()
        editor.putString("Name", name)
        editor.putInt("filler_grade", fillerGrade)
        editor.apply()

        findNavController().navigate(DataFragmentDirections.actionDataToTerm())
    }


}