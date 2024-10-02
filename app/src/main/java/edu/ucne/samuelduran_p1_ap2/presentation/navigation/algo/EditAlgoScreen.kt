@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.samuelduran_p1_ap2.presentation.algo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.samuelduran_p1_ap2.presentation.algo.AlgoViewModel

@Composable
fun EditAlgoScreen(
    viewModel: AlgoViewModel = hiltViewModel(),
    algoId: Int,
    goBack: () -> Unit
) {
    LaunchedEffect(algoId) {
        viewModel.selectedAlgo(algoId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EditAlgoBodyScreen(
        uiState = uiState,
        onDatosDelClienteChange = viewModel::onDatosDelClienteChange,
        onGalonesChange = viewModel::onGalonesChange,
        onDescuentoPorGalonChange = viewModel::onDescuentoPorGalonChange,
        updateTotal = viewModel::updateTotal,
        save = {
            viewModel.save()
            goBack()
        },
        goBack = goBack
    )
}

@Composable
fun EditAlgoBodyScreen(
    uiState: AlgoViewModel.UiState,
    onDatosDelClienteChange: (String) -> Unit,
    onGalonesChange: (Float) -> Unit,
    onDescuentoPorGalonChange: (Float) -> Unit,
    updateTotal: () -> Unit,
    save: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Editar Venta",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = save) {
                        Icon(Icons.Filled.Check, contentDescription = "Guardar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            uiState.errorMessages?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            uiState.successMessage?.let { successMessage ->
                Text(
                    text = successMessage,
                    color = Color.Green,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Datos del Cliente") },
                value = uiState.DatosDelCLiente,
                onValueChange = onDatosDelClienteChange,
                isError = uiState.DatosDelCLiente.isBlank()
            )
            if (uiState.DatosDelCLiente.isBlank()) {
                Text(
                    text = "Este campo es obligatorio",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Galones") },
                value = uiState.galones?.toString() ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { newValue ->
                    val galones = newValue.toFloatOrNull() ?: 0f
                    onGalonesChange(galones)
                },
                isError = uiState.galones == null || uiState.galones <= 0
            )
            if (uiState.galones == null || uiState.galones <= 0) {
                Text(
                    text = "Los galones deben ser mayores que cero",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Descuento por Galón") },
                value = uiState.descuentoPorGalon?.toString() ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { newValue ->
                    val descuento = newValue.toFloatOrNull() ?: 0f
                    onDescuentoPorGalonChange(descuento)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Total Descontado") },
                value = uiState.totalDescontado?.toString() ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { },
                enabled = false
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Total") },
                value = uiState.total?.toString() ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { save() }
                ) {
                    Text(text = "Guardar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { goBack() }
                ) {
                    Text(text = "Cancelar")
                }
            }
        }
    }
}
