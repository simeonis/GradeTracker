package sheridan.simeoni.gradetracker.ui.course

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.database.CourseStatus
import sheridan.simeoni.gradetracker.databinding.FragmentCourseItemBinding
import sheridan.simeoni.gradetracker.helper.DragRecyclerView
import sheridan.simeoni.gradetracker.model.*
import sheridan.simeoni.gradetracker.ui.term.TermViewModel

class CourseRecyclerViewAdapter(
        private val context: Context,
        private val view: View,
        private val viewModel: CourseViewModel) :
        RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>(),
        DragRecyclerView {

    var courses: MutableList<Course>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courses!![position])
    }

    override fun getItemCount(): Int = courses?.size ?: 0

    override fun swap(position1: Int, position2: Int) {
        courses!![position1].position = position2
        courses!![position2].position = position1

        val course1 = courses!![position1]
        courses!!.remove(course1)
        courses!!.add(position2, course1)
        notifyItemMoved(position1, position2)
    }

    override fun delete(position: Int) {
        val course = courses!![position]
        val action = CourseFragmentDirections.actionGlobalToConfirmation(course.id, course.courseName)
        view.findNavController().navigate(action)
    }

    override fun update() {
        viewModel.update(courses!!)
    }

    class ViewHolder private constructor(
            private val binding: FragmentCourseItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.courseItemLabel.text = course.courseCode
            binding.courseGradeLabel.text =
                    if(course.grade == -1.0f) context.getString(R.string.grade).plus(context.getString(R.string.blank))
                    else context.getString(R.string.grade).plus(String.format("%.1f%%", course.grade))
            binding.courseGradeTargetLabel.text = context.getString(R.string.course_target).plus(String.format("%.1f%%", course.targetGrade))
            binding.courseEditButton.setOnClickListener {
                val action = CourseFragmentDirections.actionCourseToCourseDialog(CourseStatus(true, course.toData()))
                it.findNavController().navigate(action)
            }
            binding.root.setOnClickListener {
                val action = CourseFragmentDirections.actionCourseToAssignment(KeyEnvelope(course.courseName, course.id))
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
                val binding = FragmentCourseItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }
        }
    }
}