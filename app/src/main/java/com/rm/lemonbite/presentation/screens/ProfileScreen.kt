package com.rm.lemonbite.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rm.lemonbite.R
import com.rm.lemonbite.presentation.customComponents.CustomOutlinedTextField
import com.rm.lemonbite.presentation.customComponents.TopMenuBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateToCartScreen: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopMenuBar(
            showBackButton = true,
            onCartClicked = {
                navigateToCartScreen()
            },
            onBackClick = {
                onBackClick()
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(R.dimen.medium)
                )
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Personal Information",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(R.dimen.medium_large).value.sp
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.small)))

            Text(
                text = "Avatar",
                fontFamily = FontFamily.SansSerif,
                color = colorResource(R.color.green),
                fontSize = dimensionResource(R.dimen.medium).value.sp
            )

            Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.small)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.green))
                        .padding(dimensionResource(R.dimen.large))
                ) {
                    Text(
                        text = "AV",
                        fontFamily = FontFamily.SansSerif,
                        color = colorResource(R.color.white),
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.large).value.sp
                    )
                }

                Row {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                            .background(
                                colorResource(R.color.bright_gray)
                            )
                    ) {
                        Text(
                            text = "Change",
                            fontFamily = FontFamily.SansSerif,
                            color = colorResource(R.color.green),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensionResource(R.dimen.medium).value.sp,
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(R.dimen.medium),
                                vertical = dimensionResource(R.dimen.small)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.padding(start = dimensionResource(R.dimen.small)))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                            .border(
                                width = 1.dp,
                                colorResource(R.color.green),
                                RoundedCornerShape(dimensionResource(R.dimen.small))
                            )
                    ) {
                        Text(
                            text = "Remove",
                            fontFamily = FontFamily.SansSerif,
                            color = colorResource(R.color.gray),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimensionResource(R.dimen.medium).value.sp,
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(R.dimen.medium),
                                vertical = dimensionResource(R.dimen.small)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.small)))

            CustomOutlinedTextField(
                headline = "First Name",
                placeholder = "",
                enable = false,
                value = "Test",
                onValueChanged = { }
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.very_small)))

            CustomOutlinedTextField(
                headline = "Last Name",
                placeholder = "",
                enable = false,
                value = "User",
                onValueChanged = { }
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.very_small)))

            CustomOutlinedTextField(
                headline = "Email",
                placeholder = "",
                enable = false,
                value = "rakesh09@gmail.com",
                onValueChanged = { }
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.very_small)))

            CustomOutlinedTextField(
                headline = "Phone No.",
                placeholder = "",
                enable = false,
                value = "+91 - 8249661234",
                onValueChanged = { }
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.small)))

            Text(
                text = "Email Notifications",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(R.dimen.medium_large).value.sp
            )

            val notificationsList = remember {
                mutableStateMapOf(
                    "Order Status" to false,
                    "Password Changes" to false,
                    "Special Offers" to false,
                    "Newsletter" to false
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.very_small))
                    .clip(
                        RoundedCornerShape(
                            dimensionResource(R.dimen.small)
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.light_gray),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.small))
                    )
            ) {
                notificationsList.forEach { (notification, checked) ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = {
                                    notificationsList[notification] = !checked
                                },
                                colors = CheckboxDefaults.colors(
                                    uncheckedColor = colorResource(R.color.gray),
                                    checkedColor = colorResource(R.color.green),
                                    checkmarkColor = colorResource(R.color.yellow)
                                ),
                            )

                            Text(
                                text = notification
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(colorResource(R.color.light_gray))
                        )
                    }
                }

            }
        }

        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.medium_large),
                    end = dimensionResource(R.dimen.medium_large),
                    bottom = dimensionResource(R.dimen.medium_large),
                    top = dimensionResource(R.dimen.small),
                ),
            contentPadding = PaddingValues(dimensionResource(R.dimen.small)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.very_small)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.green)
            )
        ) {
            Text(
                text = "Log out",
                fontSize = dimensionResource(R.dimen.medium).value.sp,
                color = colorResource(R.color.yellow),
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}