package com.phlox.tvwebbrowser.activity.tabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.phlox.tvwebbrowser.databinding.ActivityTabsBinding

class TabsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SELECTED_TAB_INDEX = "selected_tab_index"
        const val EXTRA_CURRENT_TAB_INDEX = "current_tab_index"
        const val EXTRA_TAB_STATES = "tab_states"
        const val EXTRA_ADD_NEW_TAB = "add_new_tab"
    }

    private lateinit var vb: ActivityTabsBinding
    private lateinit var tabsAdapter: TabsAdapter
    private var currentTabIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityTabsBinding.inflate(layoutInflater)
        setContentView(vb.root)

        currentTabIndex = intent.getIntExtra(EXTRA_CURRENT_TAB_INDEX, 0)
        val tabStates = intent.getParcelableArrayListExtra<TabInfo>(EXTRA_TAB_STATES) ?: arrayListOf()

        setupRecyclerView(tabStates)
        setupAddTabButton()
    }

    private fun setupAddTabButton() {
        vb.btnAddTab.setOnClickListener {
            // Return signal to add new tab
            val resultIntent = Intent().apply {
                putExtra(EXTRA_ADD_NEW_TAB, true)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun setupRecyclerView(tabStates: ArrayList<TabInfo>) {
        vb.rvTabs.layoutManager = LinearLayoutManager(this)
        
        tabsAdapter = TabsAdapter(tabStates, currentTabIndex) { tabIndex ->
            // Return selected tab index
            val resultIntent = Intent().apply {
                putExtra(EXTRA_SELECTED_TAB_INDEX, tabIndex)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
        
        vb.rvTabs.adapter = tabsAdapter

        // Auto-focus on current tab
        vb.rvTabs.post {
            vb.rvTabs.scrollToPosition(currentTabIndex)
            vb.rvTabs.findViewHolderForAdapterPosition(currentTabIndex)?.itemView?.requestFocus()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
