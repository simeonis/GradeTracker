package sheridan.simeoni.gradetracker.ui


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.databinding.FragmentDataBinding
import sheridan.simeoni.gradetracker.helper.KeyboardManager
import sheridan.simeoni.gradetracker.model.GradeCalculator

class DataFragment : Fragment() {
    private lateinit var binding : FragmentDataBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentDataBinding.inflate(inflater, container, false)
        binding.dataNextButton.setOnClickListener { next() }
        binding.fragmentDataFillerGrade.isVisible = false

        return binding.root
    }
    var clickCount = 0
    private fun next(){
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val name = binding.fragmentDataNameInput.text.toString()
        val fillerGrade = binding.fragmentDataFillerGrade.text.toString()

        clickCount +=1
        if(name.isNotEmpty()){
            binding.fragmentDataNameInput.isVisible = false
            binding.fragmentDataFillerGrade.isVisible = true
            editor.putString("Name", name)

        }
        else{
            binding.fragmentDataNameInput.error = "required"
        }

        if(fillerGrade.isNotEmpty()){
            editor.putFloat("filler_grade", (fillerGrade.toFloat()/ 100f))
            GradeCalculator.fillerGrade = (fillerGrade.toFloat()/ 100f)
        }
        else if(fillerGrade.isEmpty() && clickCount > 1){
            binding.fragmentDataFillerGrade.error = "required"
        }

        if(name.isNotEmpty() && fillerGrade.isNotEmpty()){
            editor.apply()
            findNavController().navigate(DataFragmentDirections.actionDataToTerm())
            KeyboardManager.hideKeyboard(requireActivity())
        }
    }
}