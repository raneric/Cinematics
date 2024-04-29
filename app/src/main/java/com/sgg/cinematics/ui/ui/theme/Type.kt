package com.sgg.cinematics.ui.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sgg.cinematics.R

val Typography = Typography(
        titleLarge = TextStyle(
                fontFamily = FontFamily(Font(R.font.chivo_bold)),
                fontWeight = FontWeight.Bold,
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
                lineHeight = 20.sp,
                letterSpacing = 0.5.sp
        ),
        labelMedium = TextStyle(
                fontFamily = FontFamily(Font(R.font.chivo_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
                fontFamily = FontFamily(Font(R.font.chivo_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
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

val userProfileTitle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Bold,
        color = user_profile_txt_color,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
)

val userProfileContent = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Normal,
        color = user_profile_txt_color,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
)

val userBioText = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.Normal,
        color = user_profile_txt_color,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
)