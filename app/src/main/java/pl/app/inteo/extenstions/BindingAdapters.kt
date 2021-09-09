package pl.app.inteo.extenstions

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel

@BindingAdapter("setTextRepositoriesTypeUiModel")
fun setTextRepositoriesTypeUiModel(view: TextView, value: RepositoriesTypeUiModel?) = value?.run {
    view.text = view.context.getString(getStarsSinceLabel())
}

@BindingAdapter("loadImageUrl")
fun loadImageUrl(image: AppCompatImageView, url: String?) {
    Glide.with(image).load(url).into(image)
}