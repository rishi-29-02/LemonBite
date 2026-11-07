package com.rm.lemonbite.presentation.screens.dishDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.rm.lemonbite.R
import com.rm.lemonbite.presentation.customComponents.TopMenuBar
import com.rm.lemonbite.presentation.utils.UiState
import java.util.Locale

@Composable
fun DishDetailsScreen(
    modifier: Modifier = Modifier,
    navigateToCartScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    onAddToCartClicked: () -> Unit = {},
    onBackClick: () -> Unit,
    detailViewModel: DishDetailViewModel = hiltViewModel()
) {
    val itemUiState = detailViewModel.itemUiState.collectAsStateWithLifecycle().value
    val totalAmount = detailViewModel.totalAmount.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopMenuBar(
            showBackButton = true,
            onCartClicked = {
                navigateToCartScreen()
            },
            onProfileIconClick = {
                navigateToProfileScreen()
            },
            onBackClick = {
                onBackClick()
            }
        )

        when(itemUiState) {
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                val item = itemUiState.data
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                        .padding(
                            top = dimensionResource(R.dimen.large),
                            start = dimensionResource(R.dimen.medium_large),
                            end = dimensionResource(R.dimen.medium_large),
                        )
                ) {
                    AsyncImage(
                        model = item.image,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                    )

                    Column(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.medium))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.title,
                                fontSize = dimensionResource(R.dimen.large).value.sp,
                                fontFamily = FontFamily.Serif,
                                color = colorResource(R.color.green),
                                fontWeight = FontWeight.SemiBold,
                            )


                            Text(
                                text = "$${item.price}",
                                fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif,
                            )
                        }

                        Text(
                            text = item.description,
                            fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.padding( top =
                                dimensionResource(R.dimen.small)
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeliveryDining,
                                contentDescription = "Dining Truck",
                                tint = colorResource(R.color.green)
                            )

                            Text(
                                text = "Delivery time : 20 mins",
                                fontFamily = FontFamily.SansSerif,
                                modifier = Modifier.padding(start = dimensionResource(R.dimen.small))
                            )
                        }

                        Text(
                            text = "Change",
                            textDecoration = TextDecoration.Underline,
                            fontFamily = FontFamily.SansSerif,
                            color = colorResource(R.color.green),
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.small)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Quantity",
                            fontFamily = FontFamily.SansSerif,
                            color = colorResource(R.color.green),
                            fontWeight = FontWeight.Bold,
                            fontSize = dimensionResource(R.dimen.medium_large).value.sp
                        )

                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = colorResource(R.color.light_gray),
                                    CircleShape
                                )
                            ,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    vertical = 6.dp,
                                    horizontal = 8.dp
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(
                                            width = 1.dp,
                                            colorResource(R.color.light_gray),
                                            shape = CircleShape
                                        ).clickable(
                                            onClick = {
                                                detailViewModel.removeQty()
                                            }
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Remove",
                                        modifier = Modifier.padding(1.dp)
                                    )
                                }

                                Text(
                                    text = "${item.qtySelected}",
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.medium_large))
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(colorResource(R.color.green))
                                        .border(
                                            width = 1.dp,
                                            colorResource(R.color.green),
                                            shape = CircleShape
                                        ).clickable(
                                            onClick = {
                                                detailViewModel.addQty()
                                            }
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Remove",
                                        modifier = Modifier.padding(1.dp),
                                        tint = colorResource(R.color.white)
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "Add Ons",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.green),
                        fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                    )

                    val addons = item.addons

                    addons.forEach { addOn ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimensionResource(R.dimen.small))
                                .border(
                                    1.dp,
                                    colorResource(R.color.light_gray),
                                    RoundedCornerShape(dimensionResource(R.dimen.small))
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        end = dimensionResource(R.dimen.small)
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Checkbox(
                                        checked = addOn.isSelected,
                                        onCheckedChange = { checked ->
                                            detailViewModel.updateAddOns(addOn, checked)
                                        },
                                        colors = CheckboxDefaults.colors(
                                            uncheckedColor = colorResource(R.color.gray),
                                            checkedColor = colorResource(R.color.green),
                                            checkmarkColor = colorResource(R.color.yellow)
                                        )
                                    )

                                    Text(
                                        text = addOn.name,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                }

                                Text(
                                    text = "+ $${addOn.price}",
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.green),
                                    fontSize = dimensionResource(R.dimen.medium).value.sp,
                                )
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {

            }
        }

        Button(
            onClick = {
                detailViewModel.addToCart() {
                    onAddToCartClicked()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_large)),
            contentPadding = PaddingValues(dimensionResource(R.dimen.small)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.very_small)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.green)
            )
        ) {
            Text(
                text = "Add to Cart - $" +
                        String.format(Locale.getDefault(), "%.2f", totalAmount),
                fontSize = dimensionResource(R.dimen.medium).value.sp,
                color = colorResource(R.color.yellow),
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}