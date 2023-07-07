package com.football.pl_fixture.ui.bindingadapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.football.pl_fixture.R
import com.like.LikeButton

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
@BindingAdapter("isFavourite")
fun setFavourite(img: ImageView, isFavourite: Boolean? = false) {
    isFavourite?.let { if (isFavourite) {
        Glide.with(  img.context)
            .load(R.drawable.ic_favorites_active)
            .placeholder(
                ContextCompat.getDrawable(
                    img.context,
                    R.drawable.ic_launcher_foreground
                )
            )
            .into(img)
    } else {

        Glide.with(  img.context)
            .load(R.drawable.ic_favorites_grey)
            .placeholder(
                ContextCompat.getDrawable(
                    img.context,
                    R.drawable.ic_launcher_foreground
                )
            )
            .into(img)
    } }
}
@BindingAdapter("isFavourite")
fun setFavourite(likeButton: LikeButton, isFavourite: Boolean? = false) {
    isFavourite?.let { likeButton.isLiked = it }
}
