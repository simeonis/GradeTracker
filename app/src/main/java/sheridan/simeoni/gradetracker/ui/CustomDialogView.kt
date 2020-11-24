package sheridan.simeoni.gradetracker.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

open class CustomDialogView : View {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ){
        init(context, attrs, defStyleAttr)
    }

    private fun init(
            context: Context,
            attrs: AttributeSet?, defStyle: Int
    ){

    }

    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // Some colors for the face background, eyes and mouth.
    private var dialogBackColor = Color.BLACK
    // View size in pixels
    private var size = 500

    protected val dialogOutline = Path()

    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        //canvas.clipPath(dialogOutline)
        super.onDraw(canvas)
        drawOutline(canvas)
        canvas.clipPath(dialogOutline);
    }

    private fun drawOutline(canvas: Canvas) {

        dialogOutline.moveTo(size * 0.1f,  size * 0.2f)
        dialogOutline.lineTo(size *2.1f, size * 0.2f)
        dialogOutline.lineTo(size *2.1f, size * 1.0f)
        dialogOutline.lineTo(size * 0.1f, size * 1.0f)
        dialogOutline.addCircle(size *1.6f, size * 1.0f, 100.0F, Path.Direction.CW)
        dialogOutline.addCircle(size *0.6f, size * 1.0f, 100.0F, Path.Direction.CW)

        paint.color = dialogBackColor
        paint.style = Paint.Style.FILL

        canvas.drawPath(dialogOutline, paint)
    }


}