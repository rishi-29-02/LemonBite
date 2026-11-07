package com.rm.lemonbite.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.rm.lemonbite.R
import com.rm.lemonbite.domain.models.MenuItem
import com.rm.lemonbite.presentation.customComponents.TopMenuBar
import com.rm.lemonbite.presentation.utils.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToCartScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    onItemSelected: (Int) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {

    val courses = homeScreenViewModel.coursePrefsList.collectAsStateWithLifecycle().value.toList()
    val selectedCourse = homeScreenViewModel.selectedCourses.collectAsStateWithLifecycle().value
    val menuUiState = homeScreenViewModel.menuUiState.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopMenuBar(
            showBackButton = false,
            onCartClicked = {
                navigateToCartScreen()
            },
            onProfileIconClick = {
                navigateToProfileScreen()
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = colorResource(R.color.green)
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.large),
                            top = dimensionResource(R.dimen.large),
                            end = dimensionResource(R.dimen.large),
                        )
                    ,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.app_name),
                            fontSize = dimensionResource(R.dimen.large).value.sp,
                            fontFamily = FontFamily.Serif,
                            color = colorResource(R.color.yellow),
                            fontWeight = FontWeight.SemiBold,
                        )

                        Text(
                            text = stringResource(R.string.chicago),
                            fontSize = dimensionResource(R.dimen.medium).value.sp,
                            fontFamily = FontFamily.Serif,
                            color = colorResource(R.color.white),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.small))
                        )

                        Text(
                            text = "We are a family-owned " +
                                    "Mediterranean restaurant, " +
                                    "focused on traditional recipes" +
                                    " served with a modern twist.",
                            fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                            fontFamily = FontFamily.SansSerif,
                            color = colorResource(R.color.white),
                        )
                    }

                    Spacer(modifier = Modifier.padding(start = dimensionResource(R.dimen.very_small)))

                    Image(
                        painter = painterResource(
                            R.drawable.welcome_hero
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    dimensionResource(R.dimen.small)
                                )
                            )
                            .size(150.dp)
                    )
                }

                SearchTextField()
            }
        }

        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.medium_small))
        ) {
            courses.forEach {
                CourseChip(it, it in selectedCourse) { course ->
                    if (course in selectedCourse) {
                        homeScreenViewModel.removeFromSelected(course)
                    } else {
                        homeScreenViewModel.addToSelected(course)
                    }
                }
            }
        }

        when (menuUiState) {
             is UiState.Loading -> {
                 Box(
                     modifier = Modifier.fillMaxWidth().padding(32.dp),
                     contentAlignment = Alignment.Center
                 ) {
                     CircularProgressIndicator(color = colorResource(R.color.green))
                 }
            }

            is UiState.Success -> {
                fun key(name: String) = name.trim().lowercase().trimEnd('s')

                val grouped = menuUiState.data.groupBy { key(it.category) }

                val sections: List<String> = when {
                    courses.isEmpty() -> grouped.keys.toList()                 // nothing saved: show everything
                    selectedCourse.isEmpty() -> courses
                    else -> courses.filter { it in selectedCourse }            // keeps the normal order
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.medium_large),
                            start = dimensionResource(R.dimen.medium_large),
                            end = dimensionResource(R.dimen.medium_large)
                        )
                ) {
                    sections.forEach { section ->
                        val dishes = grouped[key(section)].orEmpty()

                        if (dishes.isNotEmpty()) {
                            item(key = "header_$section") {
                                Text(
                                    text = section.replaceFirstChar { it.uppercase() },
                                    fontFamily = FontFamily.Serif,
                                    fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(colorResource(R.color.background))
                                        .padding(bottom = dimensionResource(R.dimen.small))
                                )
                            }

                            items(dishes, key = { it.id }) { dish ->
                                CourseCard(dish) {
                                    onItemSelected(it)
                                }
                            }
                        }
                    }
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Couldn't load the menu")
                }
            }
        }
    }
}

@Composable
fun SearchTextField() {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.large))
        ,
        value = "",
        onValueChange = {

        },
        placeholder = {
            Text(
                stringResource(R.string.search_menu_dishes_ingredients),
                color = Color.Gray,
                fontFamily = FontFamily.SansSerif
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.green),
            unfocusedBorderColor = colorResource(R.color.green),
            focusedContainerColor = colorResource(R.color.bright_gray),
            unfocusedContainerColor = colorResource(R.color.bright_gray)
        ),
        shape = RoundedCornerShape(
            dimensionResource(R.dimen.medium)
        )
    )
}

@Composable
fun CourseChip(
    course: String,
    isSelected: Boolean,
    onChipClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = dimensionResource(R.dimen.very_small))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.large)))
            .background(color = colorResource(R.color.green))
            .clickable(
                onClick = {
                    onChipClicked(course)
                }
            )
    ) {
        Text(
            text = course,
            fontSize = dimensionResource(R.dimen.medium_small).value.sp,
            color = if (isSelected) colorResource(R.color.yellow) else colorResource(R.color.white),
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.medium_large),
                vertical = dimensionResource(R.dimen.small)),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun CourseCard(
    item: MenuItem,
    onItemClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.small))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.small)))
            .background(color = colorResource(R.color.white))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(dimensionResource(R.dimen.small))
            )
            .clickable(
                onClick = {
                    onItemClicked(item.id)
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.medium))
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                onError = { Log.e("CoilError", "${item.image} -> ${it.result.throwable}") },
                modifier = Modifier
                    .size(80.dp)
                    .clip(
                        RoundedCornerShape(
                            dimensionResource(R.dimen.small)
                        )
                    )
            )

            Column(
                modifier = Modifier.weight(1f)
                    .padding(horizontal = dimensionResource(R.dimen.small))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif,
                        fontSize = dimensionResource(R.dimen.medium).value.sp,
                    )

                    Text(
                        text = "$${item.price}",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = dimensionResource(R.dimen.medium).value.sp,
                        color = colorResource(R.color.green)
                    )
                }

                Text(
                    text = item.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.very_small))
                )
            }
        }
    }
}