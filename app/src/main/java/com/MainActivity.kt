package com

import PASSWORD_ONE
import PASSWORD_THREE
import PASSWORD_TWO
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lovetimer.ui.theme.LoveTimerTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoveTimerTheme {
                // A surface container using the 'background' color from the theme
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState, snackbar = {
                            Snackbar(
                                snackbarData = it,
                                containerColor = Color.Red,
                            )
                        })
                    },
                ) {
                    MyContent(
                        onClick = { password ->
                            scope.launch {
                                if (checkPassword(password)) {
                                    Intent(applicationContext, TimerActivity::class.java).also {
                                        startActivity(it)
                                    }
                                } else {
                                    snackbarHostState.showSnackbar(
                                        message = "Wrong Password",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun MyContent(onClick: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var text by remember { mutableStateOf("") }
            MyTextField(text = text, onChanged = { text = it }, onDone = { onClick(text) })
            Spacer(modifier = Modifier.height(10.dp))
            MyOutlinedButton(
                onClick = {
                    onClick(text)
                },
            )
        }
    }
}

private fun checkPassword(password: String): Boolean {
    return password == PASSWORD_ONE || password == PASSWORD_TWO || password == PASSWORD_THREE;
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(text: String, onChanged: (String) -> Unit, onDone: () -> Unit) {
    var hidePassword by remember { mutableStateOf(true) }
    OutlinedTextField(value = text,
        shape = RoundedCornerShape(10.dp),
        onValueChange = onChanged,
        visualTransformation = if(hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = if(hidePassword) KeyboardType.Password else KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions {
            onDone()
        },
        trailingIcon = {
            Icon(modifier = Modifier
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    hidePassword = !hidePassword
                }
                .padding(3.dp),
                imageVector = if (hidePassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                contentDescription = null)
        },
        label = { Text("Password") })
}

@Composable
fun MyOutlinedButton(onClick: () -> Unit) {
    Button(onClick = onClick, shape = RoundedCornerShape(10.dp)) {
        Text("GO")
    }
}