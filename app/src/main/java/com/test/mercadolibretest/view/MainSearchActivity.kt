package com.test.mercadolibretest.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.test.mercadolibretest.R
import com.test.mercadolibretest.databinding.ActivitySearchItemListBinding
import com.test.mercadolibretest.provider.SearchSuggestionProvider
import com.test.mercadolibretest.view.adapter.MercadoItemAdapter
import com.test.mercadolibretest.viewmodel.MainSearchViewModel


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainSearchActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var pianoViewModel: MainSearchViewModel
    private lateinit var adapter: MercadoItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySearchItemListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search_item_list)

        pianoViewModel = ViewModelProvider(this).get(MainSearchViewModel::class.java)
        binding.viewModel = pianoViewModel

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title


        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(findViewById(R.id.item_list))

        // Search bar intent handling
        handleSearchIntent(intent)

        pianoViewModel.items.observeForever {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    private fun handleSearchIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow

            //Saving queries of suggestion
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(
                    this,
                    SearchSuggestionProvider.AUTHORITY,
                    SearchSuggestionProvider.MODE
                ).saveRecentQuery(query, null)
                pianoViewModel.startItemSearch(query)
                adapter.notifyDataSetChanged()

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false // Do not iconify the widget; expand it by default
            isSubmitButtonEnabled = true
            isQueryRefinementEnabled = true

        }

        return true
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = MercadoItemAdapter(
            this,
            twoPane
        )
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, 1)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

}