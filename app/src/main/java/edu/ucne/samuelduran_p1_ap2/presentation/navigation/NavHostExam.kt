package edu.ucne.samuelduran_p1_ap2.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registro_prioridades.presentation.algo.AlgoScreen
import edu.ucne.registro_prioridades.presentation.algo.DeleteAlgoScreen
import edu.ucne.registro_prioridades.presentation.algo.EditAlgoScreen
import edu.ucne.samuelduran_p1_ap2.NavigationItem
import edu.ucne.samuelduran_p1_ap2.presentation.algo.AlgoListScreen


@Composable
fun NavHostExam(
    items: List<NavigationItem>,
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable { mutableStateOf(0) }


    NavHost(
        navController = navHostController,
        startDestination = Screen.ListScreen.route
    ) {
        composable(Screen.ListScreen.route) {
            AlgoListScreen(
                drawerState = drawerState,
                scope = scope,
                createAlgo = {
                    navHostController.navigate(Screen.RegistroScreen.route)
                },
                onEditAlgo = { algoId ->
                    navHostController.navigate(Screen.EditAlgoScreen(algoId).route)
                },
                onDeleteAlgo = { algoId ->
                    navHostController.navigate(Screen.DeleteAlgoScreen(algoId).route)
                }
            )
        }

        composable(Screen.RegistroScreen.route) {
            AlgoScreen(
                goBack = { navHostController.navigateUp() }
            )
        }

        composable("edit_algo_screen/{algoId}") { backStackEntry ->
            val algoId =
                backStackEntry.arguments?.getString("algoId")?.toInt() ?: return@composable
            EditAlgoScreen(
                algoId = algoId,
                goBack = { navHostController.navigateUp() }
            )
        }

        composable("delete_algo_screen/{algoId}") { backStackEntry ->
            val algoId =
                backStackEntry.arguments?.getString("algoId")?.toInt() ?: return@composable
            DeleteAlgoScreen(
                algoId = algoId,
                goBack = { navHostController.navigateUp() }
            )
        }
    }
}


@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF2196F3))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

