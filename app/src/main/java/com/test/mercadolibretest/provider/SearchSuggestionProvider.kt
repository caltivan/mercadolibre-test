package com.test.mercadolibretest.provider

import android.content.SearchRecentSuggestionsProvider

/**
 * Provider creation to store the suggestion search of the end user using the searcher
 */
class SearchSuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.test.mercadolibretest.provider.SearchSuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES or DATABASE_MODE_2LINES
    }
}