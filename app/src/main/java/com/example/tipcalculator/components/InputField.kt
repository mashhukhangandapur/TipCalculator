package com.example.tipcalculator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState : MutableState<String>,
    label : String,
    enabled : Boolean,
    isSingleLine : Boolean,
    keyboardType : KeyboardType = KeyboardType.Number,
    imeAction : ImeAction = ImeAction.Next,
    onAction : KeyboardActions = KeyboardActions.Default
    ){

        OutlinedTextField(
            value = valueState.value,
            onValueChange = { valueState.value = it },
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            label = { Text(text = label) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.AttachMoney,
                    contentDescription = "Refresh Icon"
                )
            },
            singleLine = isSingleLine,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = onAction
        )
    }