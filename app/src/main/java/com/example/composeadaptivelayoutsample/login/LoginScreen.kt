package com.example.composeadaptivelayoutsample.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.composeadaptivelayoutsample.design_system.CustomButton
import com.example.composeadaptivelayoutsample.design_system.CustomLink
import com.example.composeadaptivelayoutsample.design_system.CustomTextField
import com.example.composeadaptivelayoutsample.util.DeviceConfiguration

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->
        /**
         * Define a root Modifier that is the same for every single device size. Reuse this.
         */
        val rootModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .clip(
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp
                )
            )
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        /**
         * Let the parent Composable (this one) get the current used window size.
         * And define here which layout to use for which size.
         * Then align the content accordingly before you call the child composables that should
         * be completely free of any size related information.
         */

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                Column(
                    modifier = rootModifier,
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                        LoginHeaderSection(
                            modifier = Modifier.fillMaxWidth()
                        )

                        LoginFormSection(
                            emailText = emailText,
                            passwordText = passwordText,
                            onEmailTextChange = {emailText = it},
                            onPasswordChange = {passwordText = it},
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                }
            }
            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                Row(
                    modifier = rootModifier
                        .windowInsetsPadding(WindowInsets.displayCutout)
                        .padding(
                            horizontal = 32.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    LoginHeaderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    LoginFormSection(
                        emailText = emailText,
                        onEmailTextChange = { emailText = it },
                        passwordText = passwordText,
                        onPasswordChange = { passwordText = it },
                        modifier = Modifier
                            .weight(1f) // TODO weight is important. tell this.
                            .verticalScroll(rememberScrollState())
                    )
                }
            }

            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                Column(
                    modifier = rootModifier
                        .verticalScroll(rememberScrollState())
                        .padding(top = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginHeaderSection(
                        modifier = Modifier
                            .widthIn(max = 540.dp),
                            alignment = Alignment.CenterHorizontally
                    )
                    LoginFormSection(
                        emailText = emailText,
                        passwordText = passwordText,
                        onEmailTextChange = {emailText = it},
                        onPasswordChange = {passwordText = it},
                        modifier = Modifier
                            .widthIn(max = 540.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginHeaderSection(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        horizontalAlignment = alignment
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Please login to continue.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


/**
 * Try to create reusable components.
 * Here for example we know that for every device size in the end we need to insert
 * this kind of UI.
 * This Composable does not have any size related information to handle.
 * This needs to be done in the calling Composable.
 */
@Composable
fun LoginFormSection(
    emailText:String,
    onEmailTextChange: (String) -> Unit,
    passwordText: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CustomTextField(
            text = emailText,
            onValueChange = onEmailTextChange,
            label = "Email",
            hint = "john.doe@gmail.com",
            isInputSecret = false,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        CustomTextField(
            text = passwordText,
            onValueChange = onPasswordChange,
            label = "Password",
            hint = "Password",
            isInputSecret = false,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        CustomButton(
            text = "Login",
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        CustomLink(
            text = "Don't have an account?",
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}