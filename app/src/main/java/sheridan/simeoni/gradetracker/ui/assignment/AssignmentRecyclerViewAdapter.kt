package sheridan.simeoni.gradetracker.ui.assignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentItemBinding
import sheridan.simeoni.gradetracker.helper.DragRecyclerView
import sheridan.simeoni.gradetracker.model.KeyEnvelope

class AssignmentRecyclerViewAdapter(private val context: Context, private val view: View) :
        RecyclerView.Adapter<AssignmentRecyclerViewAdapter.ViewHolder>(),
        DragRecyclerView {

    var assignments: MutableList<Assignment>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun swap(p1: Int, p2: Int) {
        val temp = assignments!![p1]
        assignments!!.remove(temp)
        assignments!!.add(p2, temp)
        notifyItemMoved(p1, p2)
    }

    override fun delete(index: Int) {
        val assignment = assignments!![index]
        val action = AssignmentFragmentDirections.actionAssignmentToDelete(assignment.id, assignment.assignmentName)
        view.findNavController().navigate(action)
    }

    override fun update() {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(assignments!![position])
    }

    override fun getItemCount(): Int = assignments?.size ?: 0

    class ViewHolder private constructor(
        private val binding: FragmentAssignmentItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(assignment: Assignment) {
            binding.assignmentItemNameLabel.text = assignment.assignmentName
            binding.assignmentItemGradeLabel.text =
                    if (assignment.points == -1)
                        context.getString(R.string.grade).plus(context.getString(R.string.blank))
                    else
                        context.getString(R.string.grade).plus(String.format("%.1f%%", (assignment.points/assignment.totalPoints.toFloat()) *100f))
            binding.assignmentItemWeightLabel.text = context.getString(R.string.assignment_weight).plus(String.format("%.1f%%", assignment.weight))
            binding.root.setOnClickListener {
                val action = AssignmentFragmentDirections.actionAssignmentToGrade(KeyEnvelope(assignment.assignmentName, assignment.id))
                it.findNavController().navigate(action)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, context: Context): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentAssignmentItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }
        }
    }
}