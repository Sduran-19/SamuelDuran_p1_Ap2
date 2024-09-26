
package edu.ucne.samuelduran_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Algos")
data class AlgoEntity(
    @PrimaryKey
    val algoId: Int? = null,
    val DatosDelCLiente: String,
    val galones: Float? = null,
    val descuentoPorGalon: Float? = null,
    val precio: Float? = null,
    val totalDescontado: Float? = null,
    val total: Float? = null

)