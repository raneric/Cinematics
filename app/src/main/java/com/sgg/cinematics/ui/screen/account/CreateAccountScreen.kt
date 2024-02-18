package com.sgg.cinematics.ui.screen.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.data.userModelLists
import com.sgg.cinematics.ui.commonui.CustomDropdownMenu
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onSecondary
import com.sgg.cinematics.ui.ui.theme.md_theme_light_secondary
import java.time.Month
import java.time.Year

@Composable
fun CreateAccountScreen( //userModel: UserModel,
        modifier: Modifier = Modifier,
        onCreateAccountClick: () -> Unit
) {
    val textFiledModifier = Modifier.fillMaxWidth()
    var selectedValue by remember {
        mutableStateOf("0")
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.icon_profile_24),
                contentDescription = "")
            Column {
                OutlinedTextField(value = "", modifier = textFiledModifier, label = {
                    Text(
                        text = stringResource(id = R.string.label_first_name),
                        style = MaterialTheme.typography.bodySmall)
                }, onValueChange = {})
                OutlinedTextField(value = "", modifier = textFiledModifier, label = {
                    Text(
                        text = stringResource(id = R.string.label_last_name),
                        style = MaterialTheme.typography.bodySmall)
                }, onValueChange = {})
                OutlinedTextField(value = "", modifier = textFiledModifier, label = {
                    Text(
                        text = stringResource(id = R.string.label_user_name),
                        style = MaterialTheme.typography.bodySmall)
                }, onValueChange = {})
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.icon_mail_24px),
                contentDescription = "")
            OutlinedTextField(value = "", modifier = textFiledModifier, label = {
                Text(
                    text = stringResource(id = R.string.label_user_mail),
                    style = MaterialTheme.typography.bodySmall)
            }, onValueChange = {})
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.icon_calendar_24px),
                contentDescription = "")
            CustomDropdownMenu(modifier = Modifier.weight(0.5f),
                               selectedValue = selectedValue,
                               optionList = DAY_OPTION_LIST,
                               textLabel = stringResource(id = R.string.label_day_of_birth),
                               onValueChange = {
                                   selectedValue = it
                               })
            CustomDropdownMenu(modifier = Modifier.weight(0.5f),
                               selectedValue = selectedValue,
                               optionList = MONTH_LIST,
                               textLabel = stringResource(id = R.string.label_month_of_birth),
                               onValueChange = {
                                   selectedValue = it
                               })
            CustomDropdownMenu(modifier = Modifier.weight(1f),
                               selectedValue = selectedValue,
                               optionList = YEAR_RANGE,
                               textLabel = stringResource(id = R.string.label_year_of_birth),
                               onValueChange = {
                                   selectedValue = it
                               })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.icon_home_pin_24px),
                contentDescription = "")
            OutlinedTextField(value = "", modifier = textFiledModifier, label = {
                Text(
                    text = stringResource(id = R.string.label_location),
                    style = MaterialTheme.typography.bodySmall)
            }, onValueChange = {})
        }
        Row {
            Spacer(modifier = Modifier.size(32.dp))
            Button(
                onClick = onCreateAccountClick,
                colors = ButtonDefaults.buttonColors(md_theme_light_secondary),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()) {
                Text(
                    color = md_theme_light_onSecondary,
                    style = MaterialTheme.typography.labelLarge,
                    text = stringResource(id = R.string.txt_create_account_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    CinematicsTheme {
        CreateAccountScreen(onCreateAccountClick = {})
    }
}

val DAY_OPTION_LIST = (1..31).map { it.toString() }

val MONTH_LIST = Month.values()
    .map { month ->
        month.name.lowercase()
            .replaceFirstChar { it.uppercase() }
    }

val YEAR_RANGE = (1905..Year.now().value).sortedDescending()
    .map { it.toString() }
