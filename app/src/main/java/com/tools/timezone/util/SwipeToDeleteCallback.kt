package com.tools.timezone.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.R
import com.tools.timezone.util.NumberUtil.coerceAtMost
import com.tools.timezone.util.NumberUtil.parseAlphaFloat2Int
import kotlin.math.abs

abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.Callback() {
    private val clearPaint: Paint = Paint()
    private val background: ColorDrawable = ColorDrawable()
    private val backgroundColor: Int = Color.parseColor("#b80f0a")
    private val deleteDrawable: Drawable?
    private val intrinsicWidth: Int
    private val intrinsicHeight: Int

    init {
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        deleteDrawable = ContextCompat.getDrawable(context, R.drawable.clear_24)
        intrinsicWidth = deleteDrawable!!.intrinsicWidth
        intrinsicHeight = deleteDrawable.intrinsicHeight
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        viewHolder1: RecyclerView.ViewHolder
    ) = false

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        var diffX = dX
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height
        val isCancelled = diffX == 0f && !isCurrentlyActive
        if (isCancelled) {
            clearCanvas(
                c = c,
                left = itemView.right + diffX,
                top = itemView.top.toFloat(),
                right = itemView.right.toFloat(),
                bottom = itemView.bottom.toFloat()
            )
            super.onChildDraw(
                /* c = */ c,
                /* recyclerView = */ recyclerView,
                /* viewHolder = */ viewHolder,
                /* dX = */ diffX,
                /* dY = */ dY,
                /* actionState = */ actionState,
                /* isCurrentlyActive = */ false
            )
            return
        }
        val itemWidth = (itemView.right - itemView.left).toFloat()
        diffX = coerceAtMost(diffX, itemWidth * SWIPE_THRESHOLD)
        background.color = backgroundColor
        background.setBounds(
            /* left = */ itemView.right + diffX.toInt(),
            /* top = */ itemView.top,
            /* right = */ itemView.right,
            /* bottom = */ itemView.bottom
        )
        background.draw(c)
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = DELETE_MARGIN
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight
        val alphaF = abs(diffX / itemWidth / SWIPE_THRESHOLD)
        val alphaI = parseAlphaFloat2Int(alphaF)
        deleteDrawable!!.alpha = alphaI * 255
        deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteDrawable.draw(c)
        super.onChildDraw(
            /* c = */ c,
            /* recyclerView = */ recyclerView,
            /* viewHolder = */ viewHolder,
            /* dX = */ diffX,
            /* dY = */ dY,
            /* actionState = */ actionState,
            /* isCurrentlyActive = */ isCurrentlyActive
        )
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, clearPaint)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = SWIPE_THRESHOLD

    companion object {
        private const val SWIPE_THRESHOLD = 0.3f
        private const val DELETE_MARGIN = 10
    }
}