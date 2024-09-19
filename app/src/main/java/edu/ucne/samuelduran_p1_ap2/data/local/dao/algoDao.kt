package edu.ucne.samuelduran_p1_ap2.data.local.dao

import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.samuelduran_p1_ap2.data.local.entities.algoEntity

interface algoDao {
    @Upsert
    suspend fun save(algo: algoEntity)

    @Query("""
        select * from algos where algoId=id
        limit 1
    """)

    suspend fun find (id: Int): algoEntity
}