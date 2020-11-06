package sheridan.simeoni.gradetracker.ui.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R

class AssignmentRecyclerViewAdapter : RecyclerView.Adapter<AssignmentRecyclerViewAdapter.ViewHolder>() {

    var assignments: List<String>? = listOf("Quiz 1", "Quiz 2", "Quiz 3", "A1", "A2", "A3", "Midterm", "Final")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_assignment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = assignments!![position]
        holder.nameView.text = item
        holder.gradeView.text = "-/40"
        holder.weightView.text = "20%"
    }

    override fun getItemCount(): Int = assignments?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.assignment_item_name_label)
        val gradeView: TextView = view.findViewById(R.id.assignment_item_grade_label)
        val weightView: TextView = view.findViewById(R.id.assignment_item_weight_label)
    }
}