package com.sina.core.common.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCaseCoroutine<in PARAMS, RESPONSE>(
//    private val errorHandler: ErrorHandler,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: PARAMS): InteractState<RESPONSE> {
        return try {
            withContext(coroutineDispatcher) {
                execute(params).let {
                    InteractState.Success(it)
                }
            }
        } catch (t: Exception) {
//            Timber.e(t.stackTraceToString())
            InteractState.Error(t)
        }
    }

    protected abstract suspend fun execute(params: PARAMS): RESPONSE
}