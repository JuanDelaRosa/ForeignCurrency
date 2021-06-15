package com.juandelarosa.data.api

object FixerExceptions {
    fun getException(exception : String):Exception{
        return when(exception){
            "base_currency_access_restricted" ->
                Exception("Currency not available in the free version")
            "https_access_restricted" ->
                Exception("Your current Subscription Plan does not support HTTPS Encryption")
            "invalid_base_currency" ->
                Exception("Currency not available")
            else -> Exception("Unrecognized exception")
        }
    }
}