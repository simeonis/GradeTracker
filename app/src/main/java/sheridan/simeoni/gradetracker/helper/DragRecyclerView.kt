package sheridan.simeoni.gradetracker.helper

interface DragRecyclerView {
    fun swap(position1 : Int, position2: Int)
    fun delete(position: Int)
}