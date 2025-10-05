package com.phlox.tvwebbrowser.activity.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phlox.tvwebbrowser.R
import com.phlox.tvwebbrowser.databinding.ItemTabBinding
import com.phlox.tvwebbrowser.singleton.FaviconsPool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TabsAdapter(
    private val tabStates: List<TabInfo>,
    private val currentTabIndex: Int,
    private val onTabSelected: (Int) -> Unit,
    private val onTabClosed: (Int) -> Unit
) : RecyclerView.Adapter<TabsAdapter.TabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val binding = ItemTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.bind(tabStates[position], position == currentTabIndex, position)
    }

    override fun getItemCount(): Int = tabStates.size

    inner class TabViewHolder(private val binding: ItemTabBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTabSelected(position)
                }
            }
            
            binding.ibCloseTab.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTabClosed(position)
                }
            }
        }

        fun bind(tabState: TabInfo, isCurrent: Boolean, position: Int) {
            binding.tvTabTitle.text = tabState.title.ifEmpty { "New Tab" }
            binding.tvTabUrl.text = tabState.url.ifEmpty { "about:blank" }
            
            // Show current indicator
            binding.tvCurrentIndicator.visibility = if (isCurrent) View.VISIBLE else View.GONE
            
            // Set tab icon - load favicon using URL
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Try to get favicon from FaviconsPool using URL
                    val favicon = withContext(Dispatchers.IO) {
                        FaviconsPool.get(tabState.url)
                    }
                    if (favicon != null) {
                        binding.ivTabIcon.setImageBitmap(favicon)
                    } else {
                        binding.ivTabIcon.setImageResource(R.drawable.ic_home_grey_900_24dp)
                    }
                } catch (e: Exception) {
                    binding.ivTabIcon.setImageResource(R.drawable.ic_home_grey_900_24dp)
                }
            }
            
            // Set focus for current tab
            if (isCurrent) {
                binding.root.requestFocus()
            }
        }
    }
}
