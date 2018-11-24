package com.canhhh.kynt.analogfilter.event


import com.zomato.photofilters.imageprocessors.Filter



interface ThumbnailsAdapterListener {
    fun onFilterSelected(filter: Filter)
}
