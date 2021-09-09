package pl.app.inteo.base

import android.content.Context
import pl.app.inteo.R
import java.net.UnknownHostException

internal object ExceptionHandler {

    fun parseToStringMessage(context: Context, throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> context.getString(R.string.error_check_internet_connection)
            else -> context.getString(R.string.error_oops_error_occured)
        }
    }
}
