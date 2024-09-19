package edu.ucne.samuelduran_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.samuelduran_p1_ap2.data.local.dao.algoDao
import edu.ucne.samuelduran_p1_ap2.data.local.entities.algoEntity

@Database(
    entities = [
        algoEntity::class,
    ],

    version = 1,
    exportSchema = false
)
abstract class Parcial1Db : RoomDatabase() {
    abstract fun algoDao() : algoDao

}