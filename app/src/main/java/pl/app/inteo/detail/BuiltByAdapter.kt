package pl.app.inteo.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.app.inteo.base.BaseAdapter
import pl.app.inteo.databinding.ItemDetailBuildByBinding
import pl.app.inteo.presentation.extension.browse
import pl.app.inteo.presentation.model.BuiltByUiModel

class BuiltByAdapter : BaseAdapter<BuiltByUiModel>() {

    private val diffCallback = object : DiffUtil.ItemCallback<BuiltByUiModel>() {
        override fun areItemsTheSame(oldItem: BuiltByUiModel, newItem: BuiltByUiModel): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: BuiltByUiModel, newItem: BuiltByUiModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemDetailBuildByBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildByViewHolder(binding)
    }

    inner class BuildByViewHolder(private val binding: ItemDetailBuildByBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<BuiltByUiModel> {
        override fun bind(item: BuiltByUiModel) = binding.run {
            builtByModel = item
            executePendingBindings()
            root.setOnClickListener { it?.context?.browse(item?.url) }
        }
    }
}
