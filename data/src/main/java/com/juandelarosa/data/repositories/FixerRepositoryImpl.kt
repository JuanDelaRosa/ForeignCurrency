package com.juandelarosa.data.repositories

import com.juandelarosa.domain.common.Result
import com.juandelarosa.domain.entities.Currency
import com.juandelarosa.domain.entities.Latest
import com.juandelarosa.domain.repositories.FixerRepository

class FixerRepositoryImpl(
    private val remoteDataSource: FixerRemoteDataSourceImpl
) : FixerRepository {
    override suspend fun getCurrencies(): Result<List<Currency>> {
       return remoteDataSource.getCurrencies()
    }
    override suspend fun getLatest(base : String): Result<List<Latest>> {
        return remoteDataSource.getLatest(base)
    }
}
