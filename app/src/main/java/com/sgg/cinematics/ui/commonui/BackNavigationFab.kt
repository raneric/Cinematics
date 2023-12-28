package com.sgg.cinematics.ui.commonui

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R

@Composable
fun BackNavigationFab(
        modifier: Modifier = Modifier,
        onNavigateBack: () -> Unit
) {
    FloatingActionButton(
        onClick = onNavigateBack,
        elevation = FloatingActionButtonDefaults.elevation(12.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape,
        modifier = modifier.offset(
            x = dimensionResource(id = R.dimen.low_dp),
            y = dimensionResource(
                id = R.dimen.high_dp))) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back_24),
            contentDescription = stringResource(id = R.string.content_descrip_back_fab))
    }
}