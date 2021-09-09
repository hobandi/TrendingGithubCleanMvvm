package pl.app.inteo.presentation.utils

import androidx.annotation.StringRes
import pl.app.inteo.R
import java.net.UnknownHostException

internal object ExceptionHandler {

    @StringRes
    fun parseToStringResMessage(throwable: Throwable): Int {
        return when (throwable) {
            is UnknownHostException -> R.string.error_check_internet_connection
            else -> R.string.error_oops_error_occured
        }
    }
}
