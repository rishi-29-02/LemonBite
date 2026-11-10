package com.rm.lemonbite.presentation.customComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.rm.lemonbite.R

@Composable
fun CustomOutlinedTextField(
    headline: String,
    placeholder: String,
    value: String,
    enable: Boolean,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = headline,
            color = colorResource(R.color.green),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
        )

        Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.very_small)))

        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChanged(it)
            },
            enabled = enable,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontFamily = FontFamily.SansSerif,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape =
            RoundedCornerShape(
                dimensionResource(R.dimen.small),
            ),
            colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.green),
                unfocusedBorderColor = colorResource(R.color.green),
                focusedContainerColor = colorResource(R.color.white),
                unfocusedContainerColor = colorResource(R.color.white),
                disabledTextColor = colorResource(R.color.black),
                disabledBorderColor = colorResource(R.color.green),
            ),
            keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
        )
    }
}
