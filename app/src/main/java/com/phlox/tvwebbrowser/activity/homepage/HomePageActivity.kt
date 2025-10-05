package com.phlox.tvwebbrowser.activity.homepage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.phlox.tvwebbrowser.databinding.ActivityHomepageBinding

class HomePageActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SELECTED_URL = "selected_url"
        
        private val HOMEPAGE_SITES = listOf(
            WebsiteItem("YouTube", "https://www.youtube.com", com.phlox.tvwebbrowser.R.drawable.youtube),
            WebsiteItem("Google News", "https://news.google.com", com.phlox.tvwebbrowser.R.drawable.google_news),
            WebsiteItem("Meta", "https://www.meta.com", com.phlox.tvwebbrowser.R.drawable.meta),
            WebsiteItem("Threads", "https://www.threads.net", com.phlox.tvwebbrowser.R.drawable.threads),
            WebsiteItem("Facebook", "https://www.facebook.com", com.phlox.tvwebbrowser.R.drawable.facebook),
            WebsiteItem("TechCrunch", "https://techcrunch.com", com.phlox.tvwebbrowser.R.drawable.techcrunch),
            WebsiteItem("The Verge", "https://www.theverge.com", com.phlox.tvwebbrowser.R.drawable.theverge),
            WebsiteItem("Reddit", "https://www.reddit.com", com.phlox.tvwebbrowser.R.drawable.reddit),
            WebsiteItem("ESPN", "https://www.espn.com", com.phlox.tvwebbrowser.R.drawable.espn)
        )
    }

    private lateinit var vb: ActivityHomepageBinding
    private lateinit var adapter: HomePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setupGrid()
    }

    private fun setupGrid() {
        vb.rvHomepage.layoutManager = GridLayoutManager(this, 3)
        
        adapter = HomePageAdapter(HOMEPAGE_SITES) { websiteItem ->
            // Return selected URL to MainActivity
            val resultIntent = Intent().apply {
                putExtra(EXTRA_SELECTED_URL, websiteItem.url)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        
        vb.rvHomepage.adapter = adapter

        // Auto-focus on first item
        vb.rvHomepage.post {
            vb.rvHomepage.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                // Just finish the activity, don't navigate anywhere
                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

data class WebsiteItem(
    val name: String,
    val url: String,
    val iconRes: Int
)
