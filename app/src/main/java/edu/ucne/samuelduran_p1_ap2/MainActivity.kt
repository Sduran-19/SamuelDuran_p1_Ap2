package edu.ucne.samuelduran_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.samuelduran_p1_ap2.presentation.navigation.NavHostExam
import edu.ucne.samuelduran_p1_ap2.ui.theme.SamuelDuran_p1_Ap2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SamuelDuran_p1_Ap2Theme {
                val navHost = rememberNavController()
                val items = NavigationItems()
                NavHostExam(navHostController = navHost, items = items)

            }
        }
    }

}

fun NavigationItems() : List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "Algos",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,

            )
    )
}
