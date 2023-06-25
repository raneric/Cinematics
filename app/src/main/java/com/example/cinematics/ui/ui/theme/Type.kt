package com.example.cinematics.ui.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cinematics.R

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.chivo_bold)),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.chivo_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.rubik_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
val ratingTypo = TextStyle(
    fontFamily = FontFamily(Font(R.font.rubik_one_regular)),
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)
val genreTypo = TextStyle(
    fontFamily = FontFamily(Font(R.font.chivo_regular)),
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)
val customButtonTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.rubik_medium)),
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val userRatingText = TextStyle(
    fontFamily = FontFamily(Font(R.font.rubik_regular)),
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)