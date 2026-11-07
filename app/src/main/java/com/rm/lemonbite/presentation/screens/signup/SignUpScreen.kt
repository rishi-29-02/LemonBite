package com.rm.lemonbite.presentation.screens.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.rm.lemonbite.presentation.customComponents.CustomOutlinedTextField
import com.rm.lemonbite.presentation.customComponents.LemonBiteHeading

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onNextClicked: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val name = signUpViewModel.firstName.collectAsStateWithLifecycle().value
    val email = signUpViewModel.email.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    BoxWithConstraints (
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.large))
    ) {
        Column(
            modifier = Modifier
                .heightIn(maxHeight)
                .imePadding() // Make the EditText focused
                .verticalScroll(rememberScrollState()), // make the edittext scrollable
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                LemonBiteHeading()

                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 100.dp),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.small)),
                    colors = CardDefaults.cardColors(
                        colorResource(R.color.green)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.medium_large)).fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Let us get to know you",
                            fontSize = dimensionResource(R.dimen.medium_large).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.yellow),
                            fontFamily = FontFamily.Serif
                        )

                        Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.very_small)))

                        Text(
                            text = "Enter the details below to start ordering fresh " +
                                    "Mediterranean meals.",
                            fontSize = dimensionResource(R.dimen.medium_small).value.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(R.color.white),
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                CustomOutlinedTextField(
                    headline = "First Name",
                    placeholder = "Enter First Name",
                    enable = true,
                    value = name,
                    onValueChanged = { signUpViewModel.updateFirstName(it) }
                )

                Spacer(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.medium)))

                CustomOutlinedTextField(
                    headline = "Email",
                    placeholder = "Ex. rakesh09@gmail.com",
                    enable = true,
                    value = email,
                    onValueChanged = { signUpViewModel.updateEmail(it) }
                )
            }

            Button(
                onClick = {
                    if (!signUpViewModel.verifyName()) {
                        Toast.makeText(context, "First name invalid", Toast.LENGTH_SHORT).show()
                    } else if (!signUpViewModel.verifyEmail()) {
                        Toast.makeText(context, "Email invalid", Toast.LENGTH_SHORT).show()
                    } else {
                        signUpViewModel.saveUserData(
                            onSuccess = {
                                onNextClicked()
                            },
                            onError = {
                                Toast.makeText(context, "Failed to Save", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
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
}