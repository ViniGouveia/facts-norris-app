package dev.vinigouveia.factsnorris.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.vinigouveia.factsnorris.databinding.LastSearchItemBinding

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

class LastSearchesAdapter :
    ListAdapter<String, LastSearchesAdapter.ViewHolder>(DIFF_CALLBACK) {

    lateinit var onItemClick: (String) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LastSearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: LastSearchesAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: LastSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.root.also {
                it.text = item
                it.setOnClickListener { onItemClick(item) }
            }
        }
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }
}
