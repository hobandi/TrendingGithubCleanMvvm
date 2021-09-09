package pl.app.inteo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.app.inteo.base.BaseAdapter
import pl.app.inteo.databinding.ItemTrendingRepoBinding
import pl.app.inteo.presentation.model.RepositoryUiModel

class RepositoriesAdapter : BaseAdapter<RepositoryUiModel>() {

    private val diffCallback = object : DiffUtil.ItemCallback<RepositoryUiModel>() {
        override fun areItemsTheSame(oldItem: RepositoryUiModel, newItem: RepositoryUiModel): Boolean {
            return oldItem.repositoryName == newItem.repositoryName
        }

        override fun areContentsTheSame(oldItem: RepositoryUiModel, newItem: RepositoryUiModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    inner class RepositoryViewHolder(private val binding: ItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<RepositoryUiModel> {
        override fun bind(item: RepositoryUiModel) = binding.run {
            repository = item
            executePendingBindings()
            containerTrendingRepo.setOnClickListener {
                onItemClickListener?.let { itemClick ->
                    itemClick(item, binding.containerTrendingRepo)
                }
            }
        }
    }
}
