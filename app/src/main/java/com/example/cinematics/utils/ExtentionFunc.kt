package com.example.cinematics.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.offset
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

val LocalDate.formatedDate: String
    get() {
        return this.format(formatter)
    }

fun Modifier.placeOutSide(negativeMargin: Dp): Unit {
    this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints.offset(vertical = negativeMargin.toPx()
                .toInt()))
        layout(0, 0) {
            placeable.placeRelative(0, 0)
        }
    }
}