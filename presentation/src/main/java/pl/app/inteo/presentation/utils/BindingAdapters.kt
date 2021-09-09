package pl.app.inteo.presentation.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import pl.app.inteo.presentation.extension.hide
import pl.app.inteo.presentation.extension.show

@BindingAdapter("setTextOrHideIfEmpty")
fun setTextOrHideIfEmpty(view: TextView, text: String?) {
    if (text.isNullOrEmpty()) {
        view.hide()
    } else {
        view.show()
    }
    view.text = text
}

@BindingAdapter("setTextOrHideIfEmpty")
fun setTextOrHideIfEmpty(view: TextView, value: Int?) {
    if (value == 0) {
        view.hide()
    } else {
        view.show()
    }
    view.text = value.toString()
}
