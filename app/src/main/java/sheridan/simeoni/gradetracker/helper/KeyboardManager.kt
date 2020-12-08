package sheridan.simeoni.gradetracker.helper

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

class KeyboardManager {
    companion object {
        fun hideKeyboard(activity: FragmentActivity) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus?.windowToken, 0)
        }
    }
}