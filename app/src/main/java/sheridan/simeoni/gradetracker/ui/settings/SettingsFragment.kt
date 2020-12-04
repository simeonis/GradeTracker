package sheridan.simeoni.gradetracker.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentSettingsBinding
import sheridan.simeoni.gradetracker.model.GradeCalculator

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.settingsFillerGradeInput.setHint(String.format("%.0f%%", sharedPreferences.getFloat("filler_grade", 1f) * 100f))

        binding.settingsNightmodeSwitch.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) {
                binding.settingsNightmodeIcon.setImageResource(R.drawable.ic_moon)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                editor.putInt("theme", 1)
            }
            else {
                binding.settingsNightmodeIcon.setImageResource(R.drawable.ic_sun)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                editor.putInt("theme", 0)
            }
            editor.apply()
        }

        binding.settingsFillerGradeInput.setOnEditorActionListener {
            textView, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE && textView.text.toString().isNotEmpty()){
                val fillerEntered = textView.text.toString().toFloat()
                editor.putFloat("filler_grade", fillerEntered / 100f)
                GradeCalculator.fillerGrade = fillerEntered / 100f
                editor.apply()
                textView.setText("")
                textView.setHint(String.format("%.0f%%", fillerEntered))
                hideKeyboard()
                viewModel.updateAll()
                true
            }
            else{
                false
            }
        }
        binding.settingsNightmodeSwitch.isChecked = sharedPreferences.getInt("theme", 0) == 1
        binding.settingsResetButton.setOnClickListener { deleteAll() }

        return binding.root
    }

    private fun deleteAll() {
        viewModel.deleteAll()
        findNavController().popBackStack(R.id.TermFragment, false)
    }

    fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.getWindowToken(), 0)
    }

}