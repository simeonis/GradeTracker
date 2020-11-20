package sheridan.simeoni.gradetracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.settingsNightmodeSwitch.setOnCheckedChangeListener {
                _, isChecked ->
            if (isChecked) {
                binding.settingsNightmodeIcon.setImageResource(R.drawable.ic_moon)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            else {
                binding.settingsNightmodeIcon.setImageResource(R.drawable.ic_sun)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        binding.settingsResetButton.setOnClickListener { deleteAll() }

        return binding.root
    }

    private fun deleteAll() {
        viewModel.deleteAll()
        findNavController().popBackStack(R.id.TermFragment, false)
    }

}