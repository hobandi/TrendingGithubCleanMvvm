package pl.app.inteo.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.app.inteo.base.BaseAdapter
import pl.app.inteo.databinding.ItemSettingListBinding
import pl.app.inteo.domain.models.SettingType
import pl.app.inteo.domain.models.Settings
import pl.app.inteo.presentation.extension.hide
import pl.app.inteo.presentation.extension.show

class SettingsAdapter : BaseAdapter<Settings>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Settings>() {
        override fun areItemsTheSame(oldItem: Settings, newItem: Settings): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Settings, newItem: Settings): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemSettingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingsViewHolder(binding)
    }

    inner class SettingsViewHolder(
        private val binding: ItemSettingListBinding,
    ) :
        RecyclerView.ViewHolder(binding.root), Binder<Settings> {
        override fun bind(item: Settings) {
            binding.apply {
                textViewSettingName.text = item.settingLabel
                when (item.type) {
                    SettingType.TEXT -> {
                        textViewValue.apply {
                            text = item.settingValue
                            show()
                            setOnClickListener {
                                setClickListener(item, this)
                            }
                        }

                        switchValue.hide()
                    }
                    SettingType.EMPTY -> {
                        root.setOnClickListener {
                            setClickListener(item, root)
                        }
                    }
                    SettingType.SWITCH -> {
                        switchValue.apply {
                            show()
                            isChecked = item.selectedValue
                            setOnCheckedChangeListener { _, isChecked ->
                                item.selectedValue = isChecked
                                setClickListener(item, this)
                            }
                        }
                        textViewValue.hide()
                    }
                }
            }
        }

        private fun setClickListener(settings: Settings, view: View) {
            onItemClickListener?.let { itemClick ->
                itemClick(settings, view)
            }
        }
    }
}
