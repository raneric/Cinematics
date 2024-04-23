package com.sgg.cinematics.ui.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * This is a custom shape for the user profile background, the edge of the rectangle is cut
 * at the size of the fab
 *
 * @property fabSize
 * @property cutPosition
 */
class ProfileBackgroundShape(
        private val fabSize: Float,
        private val cutPosition: ShapeCutPosition
) : Shape {

    private val startAngle = (15 * PI) / 180
    private val endAngle = (50 * PI) / 180

    private val fabR
        get() = fabSize / 2

    override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
    ): Outline {

        return when (cutPosition) {
            ShapeCutPosition.TOP_RIGHT    -> createTopRightCut(size)
            ShapeCutPosition.BOTTOM_RIGHT -> createBottomRightCut(size)
        }
    }

    private fun createBottomRightCut(size: Size): Outline {

        val arcOffsetX = size.width - (abs(fabR * cos(endAngle)) + fabR)
        val arcOffsetY = size.height - (fabR - abs(sin(startAngle) * fabR))

        return Outline.Generic(Path().apply {
            reset()
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            arcTo(
                    rect = Rect(
                            offset = Offset(arcOffsetX.toFloat(), arcOffsetY.toFloat()),
                            size = Size(fabSize, fabSize)),
                    startAngleDegrees = -50f,
                    sweepAngleDegrees = -115f,
                    forceMoveTo = false)
            lineTo(0f, size.height)
            close()
        })
    }

    private fun createTopRightCut(size: Size): Outline {

        val arcOffsetX = size.width - (abs(fabR * cos(endAngle)) + fabR)
        val arcOffsetY = fabR + abs(sin(startAngle) * fabR)

        return Outline.Generic(Path().apply {
            reset()
            lineTo(size.width, 0f)
            arcTo(
                    rect = Rect(
                            offset = Offset(arcOffsetX.toFloat(), -arcOffsetY.toFloat()),
                            size = Size(fabSize, fabSize)),
                    startAngleDegrees = 165f,
                    sweepAngleDegrees = -115f,
                    forceMoveTo = false)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        })
    }
}

enum class ShapeCutPosition {
    TOP_RIGHT, BOTTOM_RIGHT
}