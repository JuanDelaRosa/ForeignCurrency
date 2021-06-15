package com.juandelarosa.foreigncurrency.app

import android.content.Context
import com.juandelarosa.data.api.NetworkModule
import com.juandelarosa.data.mappers.FixerMapper
import com.juandelarosa.data.repositories.FixerRemoteDataSourceImpl
import com.juandelarosa.data.repositories.FixerRepositoryImpl
import com.juandelarosa.domain.repositories.FixerRepository
import com.juandelarosa.foreigncurrency.BuildConfig

object ServiceLocator {

    private val networkModule by lazy {
        NetworkModule()
    }

    @Volatile
    var repository: FixerRepository? = null

    fun provideRepository(context: Context): FixerRepository {
        synchronized(this) {
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): FixerRepository {
        val newRepo =
            FixerRepositoryImpl(
                FixerRemoteDataSourceImpl(
                    networkModule.createAPI("http://data.fixer.io/api/", BuildConfig.FixerAPIKey),
                    FixerMapper()
                )
            )
        repository = newRepo
        return newRepo
    }
}