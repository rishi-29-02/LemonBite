package com.rm.lemonbite.presentation.screens.preferences

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rm.lemonbite.R
import com.rm.lemonbite.presentation.customComponents.LemonBiteHeading

@Composable
fun PreferencesScreen(
    modifier: Modifier = Modifier,
    onNextClicked: () -> Unit,
    preferencesViewModel: PreferencesViewModel = hiltViewModel()
) {
    val courses = preferencesViewModel.courses
    val selected = preferencesViewModel.selected.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.medium))
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            LemonBiteHeading()

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp, bottom = 30.dp),
                shape = RoundedCornerShape(dimensionResource(R.dimen.small)),
                colors = CardDefaults.cardColors(
                    colorResource(R.color.green)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.medium_large))
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "What do you like ?",
                        fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.yellow),
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.very_small)))

                    Text(
                        text = "Choose the courses you enjoy and we'll \n" +
                                "personalize your menu.",
                        fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.white),
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                }
            }

            CoursesList(
                courses,
                selected,
                removeCourse = {
                    preferencesViewModel.removeCourse(it)
                },
                addCourse = {
                    preferencesViewModel.addCourse(it)
                }
            )
        }

        Button(
            onClick = {
                preferencesViewModel.setOnboardingData(
                    onSuccess = {
                        onNextClicked()
                    },
                    onError = {
                        Toast.makeText(context, "Failed to save prefs", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(dimensionResource(R.dimen.small)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.very_small)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.green)
            )
        ) {
            Text(
                text = stringResource(R.string.next),
                fontSize = dimensionResource(R.dimen.medium).value.sp,
                color = colorResource(R.color.yellow),
                fontFamily = FontFamily.SansSerif
            )
        }

    }
}

@Composable
fun CoursesList(
    courses: List<String>,
    selected: Set<String>,
    removeCourse: (String) -> Unit,
    addCourse: (String) -> Unit
) {
    courses.forEach { course ->
        CourseItem(
            course,
            course in selected
        ) { course ->
            if (course in selected) removeCourse(course)
            else addCourse(course)
        }
    }
}

@Composable
fun CourseItem(
    course: String,
    selected: Boolean,
    onCourseClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.very_small))
            .clip(
                shape = RoundedCornerShape(
                    dimensionResource(R.dimen.medium)
                )
            )
            .background(if (selected) colorResource(R.color.green) else Color.White)
            .border(
                width = 1.5.dp,
                color = if (selected) colorResource(R.color.green) else Color.Gray,
                shape = RoundedCornerShape(
                    dimensionResource(R.dimen.medium)
                )
            )
            .clickable(
                onClick = {
                    onCourseClicked(course)
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = course,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.medium))
                .weight(1f),
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(R.dimen.medium_small).value.sp,
            color = if (selected) colorResource(R.color.white) else colorResource(R.color.black)
        )

        Box(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.small))
                .clip(CircleShape)
                .background(if (selected) colorResource(R.color.yellow) else Color.LightGray)
                .border(
                    width = 1.dp,
                    color = if (selected) colorResource(R.color.green) else Color.Gray,
                    shape = CircleShape
                )
                .size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check Course",
                    tint = colorResource(R.color.green),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}