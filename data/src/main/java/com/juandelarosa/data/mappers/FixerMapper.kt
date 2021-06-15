package com.juandelarosa.data.mappers

import com.juandelarosa.domain.entities.Currency
import com.juandelarosa.domain.entities.Latest
import org.json.JSONObject

class FixerMapper {

    fun toCurrenciesList(symbols : JSONObject) : List<Currency>{
        val list = mutableListOf<Currency>()
        symbols.keys().forEach {
            val keyvalue: String = symbols.getString(it)
            list.add(Currency(it, keyvalue))
        }
        return list
    }

    fun toLatestList(symbols : JSONObject) : List<Latest>{
        val list = mutableListOf<Latest>()
        symbols.keys().forEach {
            val keyvalue: Double = symbols.getDouble(it)
            list.add(Latest(it, keyvalue))
        }
        return list
    }
}
