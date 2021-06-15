package com.juandelarosa.foreigncurrency.app

import android.app.Application
import com.juandelarosa.domain.repositories.FixerRepository
import com.juandelarosa.domain.usecases.GetCurrenciesUseCase
import com.juandelarosa.domain.usecases.GetLatestUseCase


class FixerCurrenciesApp: Application() {
    private val repository: FixerRepository
        get() = ServiceLocator.provideRepository(this)

    val getCurrencies: GetCurrenciesUseCase
        get() = GetCurrenciesUseCase(repository)

    val getLatest: GetLatestUseCase
        get() = GetLatestUseCase(repository)
}