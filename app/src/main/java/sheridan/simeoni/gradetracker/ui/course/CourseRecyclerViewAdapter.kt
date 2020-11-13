package sheridan.simeoni.gradetracker.ui.course

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Course
import sheridan.simeoni.gradetracker.databinding.FragmentCourseItemBinding
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.model.*
import sheridan.simeoni.gradetracker.ui.term.TermFragmentDirections

class CourseRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<CourseRecyclerViewAdapter.ViewHolder>() {

    var courses: List<Course>? = null
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

    class ViewHolder private constructor(
            private val binding: FragmentCourseItemBinding, private val context: Context ): RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.courseItemLabel.text = course.courseName
            binding.courseGradeLabel.text = if(course.grade == -1) context.getString(R.string.blank) else course.grade.toString()
            binding.courseGradeTargetLabel.text = course.targetGrade.toString()
            binding.root.setOnClickListener {
                val action = CourseFragmentDirections.actionCourseToAssignment(KeyEnvelope(course.courseName, course.id))
                it.findNavController().navigate(action)
            }
            binding.root.setOnLongClickListener {
                val action = CourseFragmentDirections.actionCourseToDelete(course.id, course.courseName)
                it.findNavController().navigate(action)
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