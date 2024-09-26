package edu.ucne.samuelduran_p1_ap2.presentation.algo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.samuelduran_p1_ap2.data.local.entities.AlgoEntity
import edu.ucne.samuelduran_p1_ap2.data.local.repository.AlgoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlgoViewModel @Inject constructor(
    private val algoRepository: AlgoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getAlgos()
    }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value
            when {
                state.DatosDelCLiente.isBlank() -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            errorMessages = "Los datos del cliente no pueden estar vacíos",
                            successMessage = null
                        )
                    }
                }

                state.galones == null || state.galones <= 0 -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            errorMessages = "Los galones deben ser mayores que cero",
                            successMessage = null
                        )
                    }
                }

                else -> {
                    try {
                        val exists = algoRepository.findByDatosDelCLiente(state.DatosDelCLiente)
                        if (exists != null && exists.algoId != state.algoId) {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    errorMessages = "Ya existe un algo con estos datos del cliente",
                                    successMessage = null
                                )
                            }
                        } else {
                            val isEditing = state.algoId != 0

                            algoRepository.save(state.toEntity())

                            _uiState.update { currentState ->
                                currentState.copy(
                                    successMessage = if (isEditing) {
                                        "Venta editada exitosamente"
                                    } else {
                                        "Venta guardada exitosamente"
                                    },
                                    errorMessages = null
                                )
                            }

                            nuevo()

                            delay(3000)
                            _uiState.update { currentState ->
                                currentState.copy(successMessage = null, errorMessages = null)
                            }
                        }
                    } catch (e: Exception) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                errorMessages = "Error al guardar el algo",
                                successMessage = null
                            )
                        }

                        delay(3000)
                        _uiState.update { currentState ->
                            currentState.copy(successMessage = null, errorMessages = null)
                        }
                    }
                }
            }
        }
    }



    fun nuevo() {
        _uiState.update { currentState ->
            currentState.copy(
                algoId = null,
                DatosDelCLiente = "",
                galones = null,
                descuentoPorGalon = null,
                precio = null,
                totalDescontado = null,
                total = null,
                errorMessages = null,
                successMessage = null
            )
        }
    }

    fun selectedAlgo(algoId: Int) {
        viewModelScope.launch {
            if (algoId > 0) {
                val algo = algoRepository.getAlgo(algoId)
                _uiState.update { currentState ->
                    currentState.copy(
                        algoId = algo?.algoId,
                        DatosDelCLiente = algo?.DatosDelCLiente ?: "",
                        galones = algo?.galones,
                        descuentoPorGalon = algo?.descuentoPorGalon,
                        precio = algo?.precio,
                        totalDescontado = algo?.totalDescontado,
                        total = algo?.total,
                        errorMessages = null,
                        successMessage = null
                    )
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            try {
                val entityToDelete = _uiState.value.toEntity()
                algoRepository.delete(entityToDelete)

                _uiState.update { currentState ->
                    currentState.copy(successMessage = "Algo eliminado exitosamente", errorMessages = null)
                }

                nuevo()
            } catch (e: Exception) {
                Log.e("AlgoViewModel", "Error al eliminar el algo", e)
                _uiState.update { currentState ->
                    currentState.copy(errorMessages = "Error al eliminar el algo", successMessage = null)
                }
            }
        }
    }

    fun getAlgos() {
        viewModelScope.launch {
            algoRepository.getAlgos().collect { algos ->
                _uiState.update { currentState ->
                    currentState.copy(algos = algos)
                }
            }
        }
    }

    fun onDatosDelClienteChange(datosDelCliente: String) {
        _uiState.update { currentState ->
            currentState.copy(DatosDelCLiente = datosDelCliente, errorMessages = null, successMessage = null)
        }
    }

    fun onGalonesChange(galones: Float?) {
        _uiState.update { currentState ->
            currentState.copy(galones = galones, errorMessages = null, successMessage = null)
        }
        updateTotal()
    }

    fun onPrecioChange(precio: Float?) {
        _uiState.update { currentState ->
            currentState.copy(precio = precio, errorMessages = null, successMessage = null)
        }
        updateTotal()
    }

    fun onDescuentoPorGalonChange(descuentoPorGalon: Float?) {
        _uiState.update { currentState ->
            currentState.copy(descuentoPorGalon = descuentoPorGalon, errorMessages = null, successMessage = null)
        }
        updateTotal()
    }

    fun onTotalDescontadoChange(totalDescontado: Float?) {
        _uiState.update { currentState ->
            currentState.copy(totalDescontado = totalDescontado, errorMessages = null, successMessage = null)
        }
    }

    fun updateTotal() {
        _uiState.update { currentState ->
            val totalDescontado = (currentState.descuentoPorGalon ?: 0f) * (currentState.galones ?: 0f)

            val total = (currentState.galones ?: 0f) * (currentState.precio ?: 0f) - totalDescontado

            currentState.copy(
                totalDescontado = totalDescontado,
                total = total
            )
        }
    }



    data class UiState(
        val algoId: Int? = null,
        val DatosDelCLiente: String = "",
        val galones: Float? = null,
        val descuentoPorGalon: Float? = null,
        val precio: Float? = null,
        val totalDescontado: Float? = null,
        val total: Float? = null,
        val errorMessages: String? = null,
        val successMessage: String? = null,
        val algos: List<AlgoEntity> = emptyList()
    )

    fun UiState.toEntity() = AlgoEntity(
        algoId = algoId,
        DatosDelCLiente = DatosDelCLiente,
        galones = galones,
        descuentoPorGalon = descuentoPorGalon,
        precio = precio,
        totalDescontado = totalDescontado,
        total = total
    )
}
