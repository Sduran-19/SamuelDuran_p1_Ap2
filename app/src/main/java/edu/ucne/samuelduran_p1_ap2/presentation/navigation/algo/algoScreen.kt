@file:OptIn(ExperimentalMaterial3Api::class)

package edu.ucne.registro_prioridades.presentation.algo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.samuelduran_p1_ap2.presentation.algo.AlgoViewModel

@Composable
fun AlgoScreen(viewModel: AlgoViewModel = hiltViewModel(), goBack: () -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.value.galones, uiState.value.descuentoPorGalon, uiState.value.precio) {
        viewModel.updateTotal()
    }

    AlgoBodyScreen(
        uiState = uiState.value,
        onDatosDelClienteChange = viewModel::onDatosDelClienteChange,
        onGalonesChange = viewModel::onGalonesChange,
        onDescuentoPorGalonChange = viewModel::onDescuentoPorGalonChange,
        onPrecioChange = viewModel::onPrecioChange,
        onTotalDescontadoChange = viewModel::onTotalDescontadoChange,
        onSaveClick = viewModel::save,
        goBack = goBack
    )
}

@Composable
fun AlgoBodyScreen(
    uiState: AlgoViewModel.UiState,
    onDatosDelClienteChange: (String) -> Unit,
    onGalonesChange: (Float?) -> Unit,
    onDescuentoPorGalonChange: (Float?) -> Unit,
    onPrecioChange: (Float?) -> Unit,
    onTotalDescontadoChange: (Float?) -> Unit,
    onSaveClick: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro de Ventas") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
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
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { newValue ->
                    val galones = newValue.toFloatOrNull()
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
                label = { Text(text = "Descuento por galón") },
                value = uiState.descuentoPorGalon?.toString() ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { newValue ->
                    val descuento = newValue.toFloatOrNull()
                    onDescuentoPorGalonChange(descuento)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Precio") },
                value = uiState.precio?.toString() ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { newValue ->
                    val precio = newValue.toFloatOrNull()
                    onPrecioChange(precio)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Total descontado") },
                value = uiState.totalDescontado?.toString() ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { newValue ->
                    val totalDescontado = newValue.toFloatOrNull()
                    onTotalDescontadoChange(totalDescontado)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total: ${uiState.total?.toString() ?: "0.0"}",
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Guardar",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Guardar", color = Color.White)
            }
        }
    }
}
