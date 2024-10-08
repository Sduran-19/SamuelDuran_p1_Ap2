
package edu.ucne.samuelduran_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.samuelduran_p1_ap2.data.local.entities.AlgoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlgoDao {
    @Upsert
    suspend fun save(algo: AlgoEntity)

    @Query("Select * from Algos where algoId = :id")
    suspend fun find(id: Int): AlgoEntity

    @Delete
    suspend fun delete(algo: AlgoEntity)

    @Query("Select * from Algos")
    fun getAll(): Flow<List<AlgoEntity>>

    @Query("SELECT * FROM Algos WHERE DatosDelCLiente = :DatosDelCLiente LIMIT 1")
    suspend fun findByDatosDelCLiente(DatosDelCLiente: String): AlgoEntity?
}