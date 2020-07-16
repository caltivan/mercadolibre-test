package com.test.mercadolibretest.view

import android.content.Intent
import android.nfc.tech.MifareClassic.get
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
import com.squareup.picasso.Picasso
import com.test.mercadolibretest.R
import com.test.mercadolibretest.databinding.ItemListContentBinding
import com.test.mercadolibretest.model.MercadoItem
import com.test.mercadolibretest.model.dummy.DummyContent
import com.test.mercadolibretest.util.MyAppExecutors
import com.test.mercadolibretest.viewmodel.MainSearchViewModel


class MercadoItemAdapter(
    private val parentActivity: MainSearchActivity,
    private val values: List<DummyContent.DummyItem>,
    private val twoPane: Boolean
) : RecyclerView.Adapter<MercadoItemAdapter.AdapterViewHolder>() {

    private val onClickListener: View.OnClickListener
    private val mViewModel: MainSearchViewModel =
        ViewModelProvider(parentActivity).get<MainSearchViewModel>(MainSearchViewModel::class.java)

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyContent.DummyItem
            if (twoPane) {
                val fragment = ItemDetailFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemListContentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_list_content, parent, false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = mViewModel.items.value!![position]
        holder.bind(mViewModel, item)

/*        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }*/
    }

    override fun getItemCount() = mViewModel.items.value!!.size

    class AdapterViewHolder(private val binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * We will use this function to bind instance of Movie to the row
         */
        fun bind(viewModel: MainSearchViewModel, item: MercadoItem) {
            binding.viewModel = viewModel
            binding.item = item
            binding.executePendingBindings()
            val itemThumbnail = binding.root.findViewById<ImageView>(R.id.itemThumbnail)

        }

        companion object {
            @BindingAdapter("imageUrl")
            @JvmStatic
            fun setImageUrl(view: ImageView, imageUrl: String) {

                Picasso.get().load(imageUrl).placeholder(R.drawable.item_placeholder)
                    .into(view, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            //set animations here

                        }

                        override fun onError(e: java.lang.Exception?) {
                            if (e != null) {
                                Log.i("PICASSO", e.message)
                                e.printStackTrace()
                            }
                        }
                    })
            }
        }
    }


}