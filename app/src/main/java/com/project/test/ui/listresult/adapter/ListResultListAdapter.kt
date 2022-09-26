package com.project.test.ui.listresult.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.project.test.R
import com.project.test.data.models.Entry


/**
 * Class adapter for the recycler view
 */
class ListResultListAdapter(private val context: Context, data: List<Entry>) :
    RecyclerView.Adapter<ListResultListAdapter.ViewHolder>() {
    private val mData: List<Entry> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var imageClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_listresult, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = mData[position]

        holder.image.transitionName = entry.media.m

        Glide.with(context)
            .load(entry.media.m)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_placeholder)
            .into(holder.image)

        setListenersOnImageView(holder.image, position, imageClickListener)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    /**
     * Sets up the clickListener
     */
    private fun setListenersOnImageView(
        imgView: ImageView,
        position: Int,
        clickListener: ItemClickListener?
    ) {
        clickListener?.let { listener ->
            imgView.setOnClickListener {
                listener.onItemClick(it, position, imgView)
            }
        }
    }

    fun setClickListeners(imageClickListener: ItemClickListener) {
        this.imageClickListener = imageClickListener
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: ImageView

        init {
            image = itemView.findViewById(R.id.image)
        }
    }

    /**
     * Interface for the clickListener. This is so we can implement the listener in the fragment side
     * and just call it on the adapter side.
     */
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int, imageView: ImageView)
    }
}