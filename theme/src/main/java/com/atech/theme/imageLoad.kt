package com.atech.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


fun ImageView.loadCircular(url: String?) = Glide.with(this)
    .load(url)
    .transition(
        withCrossFade(
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        )
    )
    .placeholder(R.drawable.ic_downloading)
    .error(R.drawable.ic_running_error)
    .transform(CircleCrop())
    .into(this)

fun ImageView.loadImage(url: String?) = Glide.with(this)
    .load(url)
    .transition(
        withCrossFade(
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        )
    )
    .placeholder(R.drawable.ic_downloading)
    .error(R.drawable.ic_running_error)
    .into(this)


fun ImageView.loadImage(url: String?, callback: (Bitmap?) -> Unit) = Glide.with(this)
    .load(url)
    .transition(
        withCrossFade(
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        )
    )
    .placeholder(R.drawable.ic_downloading)
    .error(R.drawable.ic_running_error)
    .into(object : CustomTarget<Drawable?>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable?>?
        ) {
            callback(resource.toBitmap())
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })

@OptIn(DelicateCoroutinesApi::class)
fun String.getBitMap(context: Context): Deferred<Bitmap?> = GlobalScope.async(Dispatchers.IO) {
    Glide.with(context)
        .asBitmap()
        .load(this@getBitMap)
        .submit()
        .get()
}

fun String.loadImageBitMap(
    parentView: View,
    @DrawableRes errorImage: Int,
    customAction: ((Bitmap?) -> Unit)? = null,
) {
    Glide.with(parentView).asBitmap().load(this@loadImageBitMap).error(errorImage)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean = false


            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                customAction?.invoke(resource)
                return false
            }

        }).submit(100, 100)
}
