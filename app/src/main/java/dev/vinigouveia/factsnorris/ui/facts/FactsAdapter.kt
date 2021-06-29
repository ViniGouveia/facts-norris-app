package dev.vinigouveia.factsnorris.ui.facts

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.vinigouveia.factsnorris.databinding.FactsItemBinding
import dev.vinigouveia.factsnorris.shared.data.FactDisplay

/**
 * @author Vinicius Gouveia on 29/06/2021
 */
class FactsAdapter : ListAdapter<FactDisplay, FactsAdapter.ViewHolder>(DIFF_CALLBACK) {

    lateinit var onItemClick: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            FactsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: FactsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FactDisplay) {
            binding.apply {
                factCard.setOnClickListener { onItemClick(item.id) }
                factDescription.let {
                    it.text = item.description
                    it.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        itemView.resources.getDimension(item.fontSize)
                    )
                }
                factCategory.text = item.category
            }
        }
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FactDisplay>() {
            override fun areItemsTheSame(
                oldItem: FactDisplay,
                newItem: FactDisplay
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: FactDisplay,
                newItem: FactDisplay
            ) = oldItem == newItem
        }
    }
}
