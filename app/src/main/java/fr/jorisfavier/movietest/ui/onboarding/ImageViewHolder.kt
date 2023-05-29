package fr.jorisfavier.movietest.ui.onboarding

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import fr.jorisfavier.movietest.R

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.image)

    fun bind(imageRes: Int) {
        imageView.setImageResource(imageRes)
    }
}