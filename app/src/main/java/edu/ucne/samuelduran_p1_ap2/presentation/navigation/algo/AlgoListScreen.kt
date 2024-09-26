@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.samuelduran_p1_ap2.presentation.algo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.samuelduran_p1_ap2.data.local.entities.AlgoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AlgoListScreen(
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: AlgoViewModel = hiltViewModel(),
    createAlgo: () -> Unit,
    onEditAlgo: (Int) -> Unit,
    onDeleteAlgo: (Int) -> Unit


) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AlgoListBodyScreen(
        uiState = uiState,
        createAlgo = createAlgo,
        onEditAlgo = onEditAlgo,
        onDeleteAlgo = onDeleteAlgo,
        drawerState = drawerState,
        scope = scope
    )

}

@Composable
fun AlgoListBodyScreen(
    uiState: AlgoViewModel.UiState,
    drawerState: DrawerState,
    scope: CoroutineScope,
    createAlgo: () -> Unit,
    onEditAlgo: (Int) -> Unit,
    onDeleteAlgo: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Ventas",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createAlgo,
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Algo")
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.algos) { algo ->
                    AlgoRow(algo, onEditAlgo, onDeleteAlgo)
                }
            }
        }
    }
}



@Composable
fun AlgoRow(
    algo: AlgoEntity,
    onEditAlgo: (Int) -> Unit,
    onDeleteAlgo: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Datos del Cliente: ${algo.DatosDelCLiente}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Galones: ${algo.galones ?: 0f}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                Text(
                    text = "Descuento por galón: ${algo.descuentoPorGalon ?: 0f}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )

                Text(
                    text = "Precio: ${algo.precio ?: 0f}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
                Text(
                    text = "Total descontado: ${algo.totalDescontado ?: 0f}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )

                Text(
                    text = "Total: ${algo.total ?: 0f}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { onEditAlgo(algo.algoId!!) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = { onDeleteAlgo(algo.algoId!!) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}



