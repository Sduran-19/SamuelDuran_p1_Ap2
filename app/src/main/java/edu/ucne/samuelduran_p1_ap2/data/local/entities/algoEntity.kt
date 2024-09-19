package edu.ucne.samuelduran_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Algos")
data class algoEntity(
    @PrimaryKey
    val id: Int? = null,
    val algoId: Int,
)