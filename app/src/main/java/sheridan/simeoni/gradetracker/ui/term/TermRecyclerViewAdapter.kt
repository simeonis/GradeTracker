package sheridan.simeoni.gradetracker.ui.term

import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import sheridan.simeoni.gradetracker.R
import sheridan.simeoni.gradetracker.database.Term
import sheridan.simeoni.gradetracker.databinding.FragmentTermItemBinding
import sheridan.simeoni.gradetracker.model.*
import sheridan.simeoni.gradetracker.helper.DragRecyclerView

class TermRecyclerViewAdapter(private val context: Context, private val view: View) :
        RecyclerView.Adapter<TermRecyclerViewAdapter.ViewHolder>(),
        DragRecyclerView {

    var terms: MutableList<Term>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun swap(p1: Int, p2: Int) {
        val temp = terms!![p1]
        terms!!.remove(temp)
        terms!!.add(p2, temp)
        notifyItemMoved(p1, p2)
    }

    override fun delete(index: Int) {
        val term = terms!![index]
        val action = TermFragmentDirections.actionTermToDelete(term.id, term.termName)
        view.findNavController().navigate(action)
    }

    override fun update() {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(terms!![position])
    }

    override fun getItemCount(): Int = terms?.size ?: 0

    class ViewHolder private constructor(
            private val binding: FragmentTermItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(term: Term) {
            binding.termItemLabel.text = term.termName
            binding.termPercentLabel.text =
                    if(term.average == -1)
                        context.getString(R.string.term_average).plus(context.getString(R.string.blank))
                    else
                        context.getString(R.string.term_average).plus(String.format("%d%%", term.average))
            binding.termProgressLabel.text = context.getString(R.string.term_progress).plus(String.format("%d%%", term.progress))
            binding.termProgressBar.progress = term.progress
            binding.root.setOnClickListener {
                val action = TermFragmentDirections.actionTermToCourse(KeyEnvelope(term.termName, term.id))
                it.findNavController().navigate(action)
            }
            binding.root.setOnLongClickListener {
                it.isSelected = true
                true
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, context : Context): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentTermItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, context)
            }
        }
    }
}