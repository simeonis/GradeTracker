package sheridan.simeoni.gradetracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences : SharedPreferences = getSharedPreferences("My_Prefs", Context.MODE_PRIVATE)
        if(!sharedPreferences.contains("theme")){
            val editor = sharedPreferences.edit()
            editor.putInt("theme", 0)
            editor.apply()
        }
        val theme = sharedPreferences.getInt("theme", 1)
        if (theme == 1) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val logo = findViewById<ImageView>(R.id.grade_tracker_logo)
        logo.alpha = 0f
        logo.animate().setDuration(1500).alpha(1f).withEndAction {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}