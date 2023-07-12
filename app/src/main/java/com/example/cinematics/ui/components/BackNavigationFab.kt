package com.example.cinematics.ui.components

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
import com.example.cinematics.R

@Composable
fun BackNavigationFab(modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        elevation = FloatingActionButtonDefaults.elevation(12.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = CircleShape,
        modifier = modifier.offset(x = dimensionResource(id = R.dimen.low_dp),
                                   y = dimensionResource(
                                       id = R.dimen.high_dp))) {
        Icon(painter = painterResource(id = R.drawable.arrow_back_24),
             contentDescription = stringResource(id = R.string.content_descrip_back_fab))
    }
}