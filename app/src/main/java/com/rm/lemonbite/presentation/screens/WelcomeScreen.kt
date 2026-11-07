package com.rm.lemonbite.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rm.lemonbite.R
import com.rm.lemonbite.presentation.customComponents.LemonBiteHeading

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onGetStartedClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium_large))
    ) {
        LemonBiteHeading()

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painterResource(
                    R.drawable.welcome_hero
                ),
                contentDescription = null,
                modifier = modifier
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
            )

            Text(
                text = stringResource(R.string.welcome_to) + " " + stringResource(R.string.app_name),
                fontSize = dimensionResource(R.dimen.large).value.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.green),
                modifier = Modifier.padding(
                    dimensionResource(R.dimen.small)
                ),
                fontFamily = FontFamily.SansSerif
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Fresh Mediterranean favorites, family recipes, and" +
                        " warm hospitality—made for every table.",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold
            )
        }

        Button(
            onClick = {
                onGetStartedClicked()
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(dimensionResource(R.dimen.small)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.very_small)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.green)
            )
        ) {
            Text(
                text = stringResource(R.string.get_started),
                fontSize = dimensionResource(R.dimen.medium).value.sp,
                color = colorResource(R.color.yellow),
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}