package pl.app.inteo.presentation.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineContextProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}

class CoroutineContextProviderImp constructor() : CoroutineContextProvider {
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
}
