package com.mes.cornettask.data.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mes.cornettask.R
import com.mes.cornettask.data.api.POSTER_BASE_URL

object ImageHelper {

    /**
     * load the image with placeholder image shown while loading
     * also, display an image in case of failed download
     **/
    fun loadImage(context: Context, movieImage: ImageView, pathUrl: String) {
        Glide.with(context)
            .load(POSTER_BASE_URL + pathUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .listener(object : RequestListener<Drawable> {

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    movieImage.setImageDrawable(resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    movieImage.setImageResource(R.drawable.placeholder)
                    return false
                }
            })
            .into(movieImage)
    }
}