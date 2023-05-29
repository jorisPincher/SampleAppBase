package fr.jorisfavier.movietest.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.jorisfavier.movietest.R

class OnboardingAdapter : RecyclerView.Adapter<ImageViewHolder>() {

    private val images = listOf(
        R.drawable.ic_movie,
        R.drawable.ic_zen,
        R.drawable.ic_rocket,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageRes = images[position]
        holder.bind(imageRes)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}