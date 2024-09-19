package edu.ucne.samuelduran_p1_ap2.presentation.navigation

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHostExam(
    navHostController: NavHostController

){
    NavHost(navController = navHostController, startDestination = Screen.ListScreen) {

        composable<Screen.ListScreen>{
            Scaffold(){innerPadding ->
                OutlinedButton(onClick = {navHostController.navigate(Screen.RegistroScreen(0))}, modifier = Modifier.padding(innerPadding)) {
                    Text(text = "Registro")
                }
            }
        }

        composable<Screen.RegistroScreen>{
            Scaffold() { innerPadding ->
                Text(text = "Registro en otra pantalla", modifier = Modifier.padding(innerPadding))
            }

            }
        }

    }
