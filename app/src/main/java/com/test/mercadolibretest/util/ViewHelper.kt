package com.test.mercadolibretest.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.test.mercadolibretest.R

/**
 * Class to define the static adapters used by the binding and its view
 */
class ViewHelper {
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