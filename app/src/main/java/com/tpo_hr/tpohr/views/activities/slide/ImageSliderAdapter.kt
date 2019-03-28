package com.tpo_hr.tpohr.views.activities.slide

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.tpo_hr.tpohr.R

class ImageSliderAdapter(private val context: Context) : PagerAdapter() {

    private var mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val listImages = arrayListOf(R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3)

    override fun isViewFromObject(view: View, obj: Any) = view == obj as LinearLayout

    override fun getCount(): Int  = listImages.count()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        Glide.with(context).load(listImages[position]).into(imageView)

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}