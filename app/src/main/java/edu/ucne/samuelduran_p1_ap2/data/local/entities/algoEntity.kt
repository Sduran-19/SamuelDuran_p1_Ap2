
package edu.ucne.samuelduran_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Algos")
data class AlgoEntity(
    @PrimaryKey
    val algoId: Int? = null,
    val galones: Int? = null,
    val descuentoPorGalon: Int? = null,
    val totalDescontado: Int? = null,
    val total: Int? = null

)