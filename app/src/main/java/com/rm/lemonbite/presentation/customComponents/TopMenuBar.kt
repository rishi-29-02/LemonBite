package com.rm.lemonbite.presentation.customComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rm.lemonbite.R

@Composable
fun TopMenuBar(
    showBackButton: Boolean,
    onCartClicked: () -> Unit,
    onProfileIconClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.small)),
    ) {
        if (showBackButton) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Button",
                modifier =
                Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = dimensionResource(R.dimen.very_small))
                    .clickable { onBackClick() },
            )
        }

        Text(
            text = stringResource(R.string.app_name),
            fontSize = dimensionResource(R.dimen.large).value.sp,
            fontFamily = FontFamily.Serif,
            color = colorResource(R.color.green),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center),
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Notifications",
                modifier =
                Modifier
                    .padding(horizontal = dimensionResource(R.dimen.very_small))
                    .clickable(
                        onClick = {
                            onCartClicked()
                        },
                    ),
            )

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier =
                Modifier
                    .padding(horizontal = dimensionResource(R.dimen.very_small))
                    .clickable(
                        onClick = {
                            onProfileIconClick()
                        },
                    ),
            )
        }
    }
}
