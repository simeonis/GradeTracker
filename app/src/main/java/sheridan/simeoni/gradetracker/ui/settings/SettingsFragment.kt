package sheridan.simeoni.gradetracker.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentSettingsBinding
import sheridan.simeoni.gradetracker.helper.KeyboardManager
import sheridan.simeoni.gradetracker.model.GradeCalculator
import sheridan.simeoni.gradetracker.ui.dialog.ConfirmationDialog
import sheridan.simeoni.gradetracker.ui.term.TermFragmentDirections

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.settingsGreetingLabel.text = getString(R.string.greeting, sharedPreferences.getString("Name", " "))
        binding.settingsFillerGradeInput.hint = String.format("%.0f%%", sharedPreferences.getFloat("filler_grade", 1f) * 100f)

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
            _, event, _ ->
            if(event == EditorInfo.IME_ACTION_DONE) {
                updateAll()
                true
            } else{ false }
        }
        binding.settingsNightmodeSwitch.isChecked = sharedPreferences.getInt("theme", 0) == 1
        binding.settingsResetButton.setOnClickListener {
            val action = SettingsFragmentDirections.actionGlobalToConfirmation(0L, "everything")
            it.findNavController().navigate(action)
        }

        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Long>(ConfirmationDialog.CONFIRMATION_RESULT)?.observe(viewLifecycleOwner)
        {
            if (it >= 0) deleteAll()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                findNavController().navigate(R.id.action_global_to_about)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAll() {
        viewModel.deleteAll()
        findNavController().popBackStack(R.id.TermFragment, false)
    }

    private fun updateAll() {
        val fillerInput = binding.settingsFillerGradeInput
        val fillerEntered = fillerInput.text.toString().toFloat()
        editor.putFloat("filler_grade", fillerEntered / 100f)
        GradeCalculator.fillerGrade = fillerEntered / 100f
        editor.apply()
        fillerInput.setText("")
        fillerInput.hint = String.format("%.0f%%", fillerEntered)
        viewModel.updateAll()
        KeyboardManager.hideKeyboard(requireActivity())
    }
}