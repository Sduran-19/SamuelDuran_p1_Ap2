package edu.ucne.samuelduran_p1_ap2.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.samuelduran_p1_ap2.data.local.database.Parcial1Db
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providePrimerParcialDb(@ApplicationContext appContext: Context)=
        Room.databaseBuilder(
            appContext,
            Parcial1Db::class.java,
            "PrimerParcial.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideAlgoDao(parcial1DataBase: Parcial1Db) = parcial1DataBase.algoDao()
}