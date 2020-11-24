package sheridan.simeoni.gradetracker.ui

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi


class OutlineDialog : CustomDialogView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        updateOutlineProvider()
    }

    //@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun updateOutlineProvider() {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                view.let {
                    if (!dialogOutline.isEmpty)
                        outline?.setRect(1,5,5,0)
                }
            }
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateOutlineProvider()
    }


}
