package com.test.mercadolibretest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.gson.Gson
import com.test.mercadolibretest.R
import com.test.mercadolibretest.databinding.ItemDetailBinding
import com.test.mercadolibretest.databinding.ItemListContentBinding
import com.test.mercadolibretest.model.MercadoItem

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MainSearchActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: MercadoItem? = null
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                val itemGson = it.getString(ARG_ITEM_ID)
                item = gson.fromJson(itemGson, MercadoItem::class.java)
                // activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = item?.title
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ItemDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_detail, container, false)
        binding.item = item
        val rootView = binding.root//inflater.inflate(R.layout.item_detail, container, false)
        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.findViewById<TextView>(R.id.item_detail).text = it.price.toString()
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_json"
    }
}