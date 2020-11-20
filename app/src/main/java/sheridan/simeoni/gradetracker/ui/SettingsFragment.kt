package sheridan.simeoni.gradetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        
        binding.nightmodeSwitch.setOnCheckedChangeListener {
            button, isChecked ->
            if (isChecked) {
                binding.nightmodeIcon.setImageResource(R.drawable.ic_moon)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            else {
                binding.nightmodeIcon.setImageResource(R.drawable.ic_sun)
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        return binding.root
    }
}