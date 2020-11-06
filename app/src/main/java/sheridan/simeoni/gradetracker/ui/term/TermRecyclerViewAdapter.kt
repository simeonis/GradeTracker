package sheridan.simeoni.gradetracker.ui.term

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
/**
 *GradeTracker
createdbyseth*
studentID:991599894
 *on2020-11-06
 */
class TermRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<TermRecyclerViewAdapter.ViewHolder>() {

    var history: List<String>? = null
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_term_item, parent, false)
        history = listOf("HI", "Hi", "Hi")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val savedRoll = history!![position]
        holder.idView.text =  "HI"
        holder.contentView.text = "HI There"
    }

    override fun getItemCount(): Int = history?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.term_percent_label)
        val contentView: TextView = view.findViewById(R.id.term_progress_label)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}