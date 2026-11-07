package com.rm.lemonbite.presentation.screens.trackOrder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rm.lemonbite.R
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.domain.models.OrderDetails
import com.rm.lemonbite.presentation.customComponents.TopMenuBar

@Composable
fun TrackOrderScreen(
    modifier: Modifier = Modifier,
    navigateToCartScreen: () -> Unit,
    onBackClicked: () -> Unit = {},
    navigateToProfileScreen: () -> Unit = {},
    trackOrderViewModel: TrackOrderViewModel = hiltViewModel()
) {

    val orderList = trackOrderViewModel.orderList.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopMenuBar(
            showBackButton = true,
            onCartClicked = {
                navigateToCartScreen()
            },
            onProfileIconClick = {
                navigateToProfileScreen()
            }, onBackClick = {
                onBackClicked()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium))
        ) {
            Row(
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.medium),
                    bottom = dimensionResource(R.dimen.very_small)
                ),
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
                text = "Cutlery Requested: Yes",
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.small))
            )

            Text(
                text = "Order Summary",
                fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                fontFamily = FontFamily.Serif,
                color = colorResource(R.color.green),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.small))
            )

            orderList.forEach { orderDetails ->
                Box(
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.small))
                        .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
                        .border(
                            1.dp, colorResource(R.color.gray),
                            RoundedCornerShape(dimensionResource(R.dimen.small))
                        )
                        .background(colorResource(R.color.white))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.medium))
                    ) {
                        orderDetails.orderedItems.forEach { orderDetail ->
                            OrderDetail(orderDetail)
                        }

                        Spacer(
                            modifier = Modifier
                                .padding(vertical = dimensionResource(R.dimen.very_small))
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(colorResource(R.color.gray))
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = dimensionResource(R.dimen.very_small)),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Fees",
                                fontFamily = FontFamily.SansSerif,
                                color = colorResource(R.color.gray)
                            )
                            Text(
                                text = "$1.00",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif,
                                color = colorResource(R.color.green)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif,
                            )
                            Text(
                                text = "$${orderDetails.totalPrice + 1}",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif,
                                color = colorResource(R.color.green)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderDetail(menuItem: MenuItem) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${menuItem.qtySelected} x ${menuItem.title}",
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
            )

            Text(
                text = "$${menuItem.price * menuItem.qtySelected}",
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = colorResource(R.color.green)
            )
        }

        menuItem.addons.forEach {
            Addons(
                qty = menuItem.qtySelected,
                addOn = it.name,
                price = menuItem.qtySelected * it.price
            )
        }
    }
}

@Composable
fun Addons(
    qty: Int,
    addOn: String,
    price: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.very_small)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "+$qty $addOn",
            fontFamily = FontFamily.SansSerif,
            color = colorResource(R.color.gray)
        )

        Text(
            text = "$$price",
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,
            color = colorResource(R.color.green)
        )
    }
}