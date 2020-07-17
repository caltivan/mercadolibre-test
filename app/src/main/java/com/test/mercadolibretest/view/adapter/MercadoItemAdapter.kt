package com.test.mercadolibretest.view.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.test.mercadolibretest.R
import com.test.mercadolibretest.databinding.ItemListContentBinding
import com.test.mercadolibretest.model.MercadoItem
import com.test.mercadolibretest.view.ItemDetailActivity
import com.test.mercadolibretest.view.ItemDetailFragment
import com.test.mercadolibretest.view.MainSearchActivity
import com.test.mercadolibretest.viewmodel.MainSearchViewModel

/**
 * Adapter to recycleview which will contain all the list of items from the API
 */
class MercadoItemAdapter(
    private val parentActivity: MainSearchActivity,
    private val twoPane: Boolean
) : RecyclerView.Adapter<MercadoItemAdapter.AdapterViewHolder>() {

    private val onClickListener: View.OnClickListener
    private val mViewModel: MainSearchViewModel =
        ViewModelProvider(parentActivity).get<MainSearchViewModel>(MainSearchViewModel::class.java)
    private val gson = Gson()

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as MercadoItem
            if (twoPane) {
                val fragment = ItemDetailFragment()
                    .apply {
                        arguments = Bundle().apply {
                            // It will convert the Item to JSON String and passed to item detail fragment
                            val itemGson = gson.toJson(item)
                            putString(ItemDetailFragment.ARG_ITEM_ID, itemGson)
                        }
                    }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    // It will convert the Item to JSON String and passed to item detail fragment
                    val itemGson = gson.toJson(item)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, itemGson)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListContentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_list_content, parent, false)
        return AdapterViewHolder(
            binding
        )
    }

    /**
     * It will send the item to bind with the view holder and add the on click action to each item
     */
    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = mViewModel.items.value!![position]
        holder.bind(mViewModel, item)
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = mViewModel.items.value!!.size

    class AdapterViewHolder(private val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * We will use this function to bind instance of Movie to the row, it will bind the view model and the item object to the view
         */
        fun bind(viewModel: MainSearchViewModel, item: MercadoItem) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings()
        }

    }


}