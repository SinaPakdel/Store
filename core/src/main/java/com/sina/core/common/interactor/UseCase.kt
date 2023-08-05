package com.sina.core.common.interactor

abstract class UseCase<PARAMS, RESPONSE> {

    suspend operator fun invoke(params: PARAMS): InteractState<RESPONSE> {
        return try {
            execute(params).let {
                InteractState.Success(it)
            }
        } catch (e: Exception) {
            InteractState.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: PARAMS): RESPONSE
}
