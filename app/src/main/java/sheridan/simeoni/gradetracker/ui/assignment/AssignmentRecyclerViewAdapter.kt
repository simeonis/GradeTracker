package sheridan.simeoni.gradetracker.ui.assignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Assignment
import sheridan.simeoni.gradetracker.database.AssignmentStatus
import sheridan.simeoni.gradetracker.databinding.FragmentAssignmentItemBinding
import sheridan.simeoni.gradetracker.helper.DragRecyclerView
import sheridan.simeoni.gradetracker.model.GradeCalculator
import sheridan.simeoni.gradetracker.model.KeyEnvelope

class AssignmentRecyclerViewAdapter(
        private val context: Context,
        private val view: View,
        private val viewModel: AssignmentViewModel) :
        RecyclerView.Adapter<AssignmentRecyclerViewAdapter.ViewHolder>(),
        DragRecyclerView {

    var assignments: MutableList<Assignment>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(assignments!![position])
    }

    override fun getItemCount(): Int = assignments?.size ?: 0

    override fun swap(position1: Int, position2: Int) {
        assignments!![position1].position = position2
        assignments!![position2].position = position1

        val term1 = assignments!![position1]
        assignments!!.remove(term1)
        assignments!!.add(position2, term1)
        notifyItemMoved(position1, position2)
    }

    override fun delete(position: Int) {
        val assignment = assignments!![position]
        val action = AssignmentFragmentDirections.actionGlobalToConfirmation(assignment.id, assignment.assignmentName)
        view.findNavController().navigate(action)
    }

    override fun update() {
        viewModel.update(assignments!!)
    }

    class ViewHolder private constructor(
        private val binding: FragmentAssignmentItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(assignment: Assignment) {
            binding.assignmentItemNameLabel.text = assignment.assignmentName
            binding.assignmentItemGradeLabel.apply {
                var message = context.getString(R.string.grade)
                if (assignment.points == -1) {
                    message += (String.format("%.1f%%", GradeCalculator.fillerGrade * 100))
                    this.alpha = 0.25f
                } else {
                    message += (String.format("%.1f%%", (assignment.points / assignment.totalPoints.toFloat()) * 100f))
                    this.alpha = 1f
                }
                this.text = message
            }
            binding.assignmentItemWeightLabel.text = context.getString(R.string.assignment_weight).plus(String.format("%.1f%%", assignment.weight))
            binding.assignmentEditButton.setOnClickListener {
                val action = AssignmentFragmentDirections.actionAssignmentToAssignmentDialog(AssignmentStatus(true, assignment.toData()))
                it.findNavController().navigate(action)
            }
            binding.root.setOnClickListener {
                val action = AssignmentFragmentDirections.actionAssignmentToGrade(KeyEnvelope(assignment.assignmentName, assignment.id))
                it.findNavController().navigate(action)
            }
            binding.root.setOnLongClickListener {
                it.isSelected = true
                true
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