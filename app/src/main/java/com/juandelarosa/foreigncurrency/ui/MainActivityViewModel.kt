package com.juandelarosa.foreigncurrency.ui

import androidx.lifecycle.*
import com.juandelarosa.domain.common.Result
import com.juandelarosa.domain.entities.Currency
import com.juandelarosa.foreigncurrency.app.FixerCurrenciesApp
import kotlinx.coroutines.launch

class MainActivityViewModel(private val app: FixerCurrenciesApp) : ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies = _currencies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCurrencies() {
        viewModelScope.launch {
            when(val result = app.getCurrencies.invoke()){
                is Result.Success ->{
                    currencies.postValue(result.data)
                }
                is Result.Error ->{
                    _error.postValue(result.exception.message)
                }
            }
        }
    }

    class MainActivityViewModelFactory(private val app: FixerCurrenciesApp) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(app) as T
        }
    }
}