package com.sgg.cinematics.ui.commonui

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
        modifier: Modifier = Modifier,
        selectedValue: String,
        optionList: List<String>,
        textLabel: String,
        onValueChange: (String) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
            modifier = modifier,
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }) {
        OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                value = selectedValue,
                label = {
                    Text(text = textLabel,
                         style = MaterialTheme.typography.bodyLarge)
                },
                readOnly = true,
                onValueChange = {},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
        )
        ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                }) {
            optionList.forEach { value ->
                DropdownMenuItem(text = { Text(text = value) },
                                 onClick = {
                                     onValueChange(value)
                                     isExpanded = false
                                 })
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 250, heightDp = 200)
@Composable
fun CustomDropdownMenuPreview() {
    val optionList = (1..31).map { it.toString() }

    var selectedValue by remember {
        mutableStateOf("0")
    }

    CinematicsTheme {
        CustomDropdownMenu(selectedValue = selectedValue,
                           optionList = optionList,
                           textLabel = stringResource(id = R.string.label_day_of_birth),
                           onValueChange = {
                               selectedValue = it
                           })
    }
}