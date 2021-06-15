package com.juandelarosa.domain.usecases

import com.juandelarosa.domain.repositories.FixerRepository

class GetCurrenciesUseCase(private val repository: FixerRepository) {
    suspend operator fun invoke() = repository.getCurrencies()
}