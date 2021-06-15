package com.juandelarosa.domain.usecases

import com.juandelarosa.domain.repositories.FixerRepository

class GetLatestUseCase(private val repository: FixerRepository) {
    suspend operator fun invoke(base : String) = repository.getLatest(base)
}