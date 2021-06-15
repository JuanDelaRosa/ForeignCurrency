package com.juandelarosa.domain.entities

data class Latest(
    val symbol : String,
    val value : Double
) {

    fun getCurrencyWithSymbol(): String {
        val cur1 = java.util.Currency.getInstance(symbol)
        val s: String = cur1.symbol
        return "$s $value"
    }
}