package com.juandelarosa.data.repositories

import com.juandelarosa.data.api.FixerExceptions
import com.juandelarosa.data.api.FixerService
import com.juandelarosa.data.mappers.FixerMapper
import com.juandelarosa.domain.common.Result
import com.juandelarosa.domain.entities.Currency
import com.juandelarosa.domain.entities.Latest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class FixerRemoteDataSourceImpl(private val service: FixerService, private val mapper: FixerMapper) : FixerRemoteDataSource {
    override suspend fun getCurrencies(): Result<List<Currency>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getCurrencies()
                if (response.isSuccessful) {
                    val json = JSONObject(response.body())
                     if(json.has("success") && json.getBoolean("success")){
                         val symbols = json.getJSONObject("symbols")
                         return@withContext Result.Success(mapper.toCurrenciesList(symbols))
                     }else {
                         val errorType = json.getJSONObject("error").getString("type")
                         return@withContext Result.Error(FixerExceptions.getException(errorType))
                     }
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun getLatest(base : String): Result<List<Latest>>  =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getLatest(base = base)
                if (response.isSuccessful) {
                    val json = JSONObject(response.body())
                    if(json.has("success") && json.getBoolean("success")){
                        val symbols = json.getJSONObject("rates")
                        return@withContext Result.Success(mapper.toLatestList(symbols))
                    }else {
                        val errorType = json.getJSONObject("error").getString("type")
                        return@withContext Result.Error(FixerExceptions.getException(errorType))
                    }
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}