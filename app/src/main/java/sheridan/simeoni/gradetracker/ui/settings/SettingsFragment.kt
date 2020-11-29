package sheridan.simeoni.gradetracker.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

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

        binding.settingsNightmodeSwitch.isChecked = sharedPreferences.getInt("theme", 0) == 1
        binding.settingsResetButton.setOnClickListener { deleteAll() }

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

}