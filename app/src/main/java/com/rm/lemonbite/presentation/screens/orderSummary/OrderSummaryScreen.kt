package com.rm.lemonbite.presentation.screens.orderSummary

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.rm.lemonbite.R
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.presentation.customComponents.TopMenuBar

@Composable
fun OrderSummaryScreen(
    modifier: Modifier = Modifier,
    orderSummaryViewModel: OrderSummaryViewModel = hiltViewModel(),
    navigateToTrackOrder: () -> Unit = {},
    onBackClick: () -> Unit = {},
    navigateToProfileScreen: () -> Unit = {},
) {
    val isCutlerySelected = remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    val cartItems = orderSummaryViewModel.cartItemDetails.collectAsStateWithLifecycle().value
    val totalPrice = orderSummaryViewModel.totalPrice.collectAsStateWithLifecycle().value

    val menuItems = orderSummaryViewModel.manuItem.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TopMenuBar(
            showBackButton = true,
            onCartClicked = {
            },
            onProfileIconClick = {
                navigateToProfileScreen()
            },
            onBackClick = {
                onBackClick()
            },
        )

        Column(
            modifier =
            Modifier
                .padding(dimensionResource(R.dimen.medium))
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.DeliveryDining,
                        contentDescription = "Dining Truck",
                        tint = colorResource(R.color.green),
                    )

                    Text(
                        text = "Delivery time : 20 mins",
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.small)),
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

            Box(
                modifier =
                Modifier
                    .padding(vertical = dimensionResource(R.dimen.medium_large))
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                    .border(
                        1.dp,
                        colorResource(R.color.green),
                        RoundedCornerShape(dimensionResource(R.dimen.small)),
                    ),
            ) {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "Cutlery",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                        )

                        Text(
                            text = "Help reduce plastic waste, only as for cutlery if you need it.",
                            fontFamily = FontFamily.Serif,
                            color = colorResource(R.color.gray),
                            fontSize = dimensionResource(R.dimen.small).value.sp,
                            modifier =
                            Modifier.padding(
                                top = dimensionResource(R.dimen.very_small),
                            ),
                        )
                    }

                    RadioButton(
                        selected = isCutlerySelected.value,
                        onClick = {
                            isCutlerySelected.value = !isCutlerySelected.value
                        },
                        colors =
                        RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.green),
                            unselectedColor = colorResource(R.color.green),
                        ),
                    )
                }
            }

            Text(
                text = "Order Summary",
                fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                fontFamily = FontFamily.Serif,
                color = colorResource(R.color.green),
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = "Items",
                fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.medium_small)),
            )

            cartItems.forEach { cartItem ->
                CartItem(
                    cartItem,
                    onCartItemRemoveClicked = {
                        orderSummaryViewModel.updateCartItemCount(cartItem, false)
                    },
                    onCartItemAddClicked = {
                        orderSummaryViewModel.updateCartItemCount(cartItem, true)
                    },
                    onRemoveItemClicked = {
                        orderSummaryViewModel.removeCartItem(cartItem)
                    },
                )
            }

            Text(
                text = "Add More Items To Your Order!",
                fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.large)),
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small)),
            ) {
                items(
                    items = menuItems,
                    key = { it.id },
                    contentType = { MenuItem },
                ) {
                    MoreItems(it)
                }
            }
        }

        Button(
            onClick = {
                orderSummaryViewModel.placeOrder {
                    showSuccess = true
                }
            },
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_large)),
            contentPadding = PaddingValues(dimensionResource(R.dimen.small)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.very_small)),
            colors =
            ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.yellow),
            ),
        ) {
            Text(
                text = "Checkout - $$totalPrice",
                fontSize = dimensionResource(R.dimen.medium).value.sp,
                color = colorResource(R.color.green),
                fontFamily = FontFamily.SansSerif,
            )
        }

        if (showSuccess) {
            SuccessPopUp {
                navigateToTrackOrder()
            }
        }
    }
}

@Composable
fun AddOns(name: String, price: String) {
    Text(
        text = "+ $name ($$price)",
        fontWeight = FontWeight.SemiBold,
        color = colorResource(R.color.gray),
        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.very_small)),
    )
}

@Composable
fun MoreItems(menuItem: MenuItem) {
    Box(
        modifier =
        Modifier
            .width(180.dp)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
            .border(
                width = 1.dp,
                color = colorResource(R.color.gray),
                shape = RoundedCornerShape(dimensionResource(R.dimen.small)),
            ).background(colorResource(R.color.white)),
    ) {
        Column(
            modifier =
            Modifier.padding(
                dimensionResource(R.dimen.small),
            ),
        ) {
            AsyncImage(
                model = menuItem.image,
                contentDescription = "food_Icon",
                contentScale = ContentScale.Crop,
                modifier =
                Modifier
                    .width(150.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.small))),
            )

            Column {
                Text(
                    text = menuItem.title,
                    fontSize = dimensionResource(R.dimen.medium).value.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.small)),
                )

                Text(
                    text = menuItem.description,
                    fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                    color = colorResource(R.color.gray),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.very_small)),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "$${menuItem.price}",
                        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                        fontFamily = FontFamily.SansSerif,
                        color = colorResource(R.color.green),
                        fontWeight = FontWeight.Bold,
                    )

                    Button(
                        colors =
                        ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.green),
                        ),
                        onClick = {
                        },
                    ) {
                        Text(
                            text = "+ Add",
                            fontFamily = FontFamily.SansSerif,
                            color = colorResource(R.color.yellow),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SuccessPopUp(onFinished: () -> Unit = {}) {
    val progress = remember { Animatable(0f) }
    val currentOnFinished by rememberUpdatedState(onFinished)

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing),
        )
        currentOnFinished()
    }

    Dialog(
        onDismissRequest = {
        },
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(R.dimen.medium)))
                .background(colorResource(R.color.white)),
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.large)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier =
                    Modifier
                        .clip(CircleShape)
                        .background(colorResource(R.color.green))
                        .padding(dimensionResource(R.dimen.very_small)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Remove",
                        modifier = Modifier.padding(dimensionResource(R.dimen.very_small)),
                        tint = colorResource(R.color.white),
                    )
                }

                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.very_small)))

                Text(
                    text = "Success!",
                    fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.green),
                )

                Text(
                    text = "Your order will be with you shortly.",
                    fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.small)),
                )

                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.very_small)))

                Box(
                    modifier =
                    Modifier
                        .clip(CircleShape)
                        .background(colorResource(R.color.light_gray))
                        .padding(dimensionResource(R.dimen.very_small)),
                ) {
                    Text(
                        text = "Order ID: #12345",
                        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.very_small),
                        ),
                    )
                }

                Box(
                    modifier =
                    Modifier
                        .padding(vertical = dimensionResource(R.dimen.small))
                        .clip(CircleShape)
                        .background(colorResource(R.color.orange))
                        .padding(dimensionResource(R.dimen.very_small)),
                ) {
                    Text(
                        text = "Estimated Arrival: 20 mins",
                        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.very_small),
                        ),
                    )
                }

                Text(
                    text = "Thank you for your business.",
                    fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = colorResource(R.color.green),
                )
            }

            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(colorResource(R.color.light_gray)),
            ) {
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth(progress.value)
                        .fillMaxHeight()
                        .background(colorResource(R.color.green)),
                )
            }
        }
    }
}

@Composable
fun CartItem(
    cartItem: MenuItem,
    onCartItemRemoveClicked: (MenuItem) -> Unit,
    onCartItemAddClicked: (MenuItem) -> Unit,
    onRemoveItemClicked: (MenuItem) -> Unit,
) {
    Box(
        modifier =
        Modifier
            .padding(top = dimensionResource(R.dimen.very_small))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
            .border(
                1.dp,
                colorResource(R.color.gray),
                RoundedCornerShape(dimensionResource(R.dimen.small)),
            ),
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.small),
                    start = dimensionResource(R.dimen.small),
                    end = dimensionResource(R.dimen.small),
                ),
        ) {
            AsyncImage(
                model = cartItem.image,
                contentDescription = "food_Icon",
                contentScale = ContentScale.Crop,
                modifier =
                Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.small))),
            )

            Column(
                modifier =
                Modifier
                    .weight(1f)
                    .padding(start = dimensionResource(R.dimen.small)),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${cartItem.qtySelected} x ${cartItem.title}",
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                    )

                    Text(
                        text = "$${cartItem.totalItemPrice}",
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.green),
                        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                    )
                }

                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.very_small)))

                cartItem.addons.forEach {
                    AddOns(it.name, "${it.price}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier =
                        Modifier.padding(
                            vertical = dimensionResource(R.dimen.very_small),
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    colorResource(R.color.gray),
                                    shape = CircleShape,
                                ).background(colorResource(R.color.bright_gray))
                                .clickable(
                                    onClick = {
                                        if (cartItem.qtySelected > 1) {
                                            onCartItemRemoveClicked(cartItem)
                                        }
                                    },
                                ),
                        ) {
                            Box(
                                modifier = Modifier.padding(dimensionResource(R.dimen.very_small)),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Remove",
                                    modifier = Modifier.padding(1.dp),
                                    tint = colorResource(R.color.gray),
                                )
                            }
                        }

                        Text(
                            text = "${cartItem.qtySelected}",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(R.dimen.medium_large),
                            ),
                        )

                        Box(
                            modifier =
                            Modifier
                                .clip(CircleShape)
                                .background(colorResource(R.color.green))
                                .border(
                                    width = 1.dp,
                                    colorResource(R.color.green),
                                    shape = CircleShape,
                                ).clickable(
                                    onClick = {
                                        onCartItemAddClicked(cartItem)
                                    },
                                ),
                        ) {
                            Box(
                                modifier = Modifier.padding(dimensionResource(R.dimen.very_small)),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Remove",
                                    modifier = Modifier.padding(1.dp),
                                    tint = colorResource(R.color.white),
                                )
                            }
                        }
                    }

                    Text(
                        text = "Remove",
                        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = colorResource(R.color.orange),
                        modifier =
                        Modifier
                            .padding(end = dimensionResource(R.dimen.very_small))
                            .clickable(
                                onClick = {
                                    onRemoveItemClicked(cartItem)
                                },
                            ),
                    )
                }
            }
        }
    }
}
