package sheridan.simeoni.gradetracker.helper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragManageAdapter(adapter: DragRecyclerView, dragDirections: Int, swipeDirections: Int) :
        ItemTouchHelper.SimpleCallback(dragDirections, swipeDirections) {

    var myAdapter = adapter

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        myAdapter.swap(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        myAdapter.delete(viewHolder.adapterPosition)
    }
}