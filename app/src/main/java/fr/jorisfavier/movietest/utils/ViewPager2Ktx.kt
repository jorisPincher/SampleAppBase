package fr.jorisfavier.movietest.utils

import androidx.viewpager2.widget.ViewPager2

/**
 * Move to the next item if we are not already at the max value
 */
fun ViewPager2.next(onEndReached: () -> Unit) {
    val adapter = adapter
    if (adapter != null) {
        if (currentItem < adapter.itemCount - 1) {
            currentItem += 1
        } else {
            onEndReached()
        }
    }
}

/**
 * Move to the previous item if we are not already at the first one
 */
fun ViewPager2.previous() {
    if (currentItem > 0) currentItem -= 1
}