package com.phlox.tvwebbrowser.activity.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phlox.tvwebbrowser.R
import com.phlox.tvwebbrowser.databinding.ItemHomepageBinding

class HomePageAdapter(
    private val websites: List<WebsiteItem>,
    private val onWebsiteSelected: (WebsiteItem) -> Unit
) : RecyclerView.Adapter<HomePageAdapter.WebsiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val binding = ItemHomepageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WebsiteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        holder.bind(websites[position])
    }

    override fun getItemCount(): Int = websites.size

    inner class WebsiteViewHolder(private val binding: ItemHomepageBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onWebsiteSelected(websites[position])
                }
            }
        }

        fun bind(websiteItem: WebsiteItem) {
            binding.tvWebsiteName.text = websiteItem.name
            
            // Set the specific icon for each website
            binding.ivWebsiteIcon.setImageResource(websiteItem.iconRes)
        }
    }
}
