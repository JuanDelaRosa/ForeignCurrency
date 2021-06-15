package com.juandelarosa.foreigncurrency.ui

import androidx.lifecycle.*
import com.juandelarosa.domain.common.Result
import com.juandelarosa.domain.entities.Latest
import com.juandelarosa.foreigncurrency.app.FixerCurrenciesApp
import kotlinx.coroutines.launch

class ConvertionActivityViewModel(private val app: FixerCurrenciesApp) : ViewModel() {

    private val _base = MutableLiveData<String>()
    val base = _base

    private val _currencies = MutableLiveData<List<Latest>>()
    val currencies = _currencies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun prepateUI(currency: String) {
        base.postValue(currency)
        viewModelScope.launch {
            when(val result = app.getLatest(currency)){
                is Result.Success ->{
                    currencies.postValue(result.data)
                }
                is Result.Error ->{
                    _error.postValue(result.exception.message)
                }
            }
        }
    }


    class ConvertionActivityViewModelFactory(private val app: FixerCurrenciesApp) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ConvertionActivityViewModel(app) as T
        }
    }
}