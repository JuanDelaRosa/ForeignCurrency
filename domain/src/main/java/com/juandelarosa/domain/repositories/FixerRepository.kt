package com.juandelarosa.domain.repositories
import com.juandelarosa.domain.common.Result
import com.juandelarosa.domain.entities.Currency
import com.juandelarosa.domain.entities.Latest

interface FixerRepository {
    suspend fun getCurrencies(): Result<List<Currency>>
    suspend fun getLatest(base : String): Result<List<Latest>>
}