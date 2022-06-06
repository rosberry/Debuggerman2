package com.rosberry.android.debuggerman2.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.Slider
import com.google.android.material.slider.Slider.OnChangeListener
import com.rosberry.android.debuggerman2.R
import com.rosberry.android.debuggerman2.entity.DebuggermanItem

class SliderDelegate : DebuggermanAdapterDelegate(R.layout.item_debuggerman_slider) {

    override fun createViewHolder(parent: ViewGroup): ViewHolder = ViewHolder(inflate(parent))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        if (holder is ViewHolder && item is DebuggermanItem.Slider) {
            (item as? DebuggermanItem.Slider)?.let(holder::bind)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val slider: Slider = itemView.findViewById(R.id.slider)
        private val value: TextView = itemView.findViewById(R.id.value)

        private lateinit var item: DebuggermanItem.Slider

        init {
            slider.addOnChangeListener(OnChangeListener { _, value, fromUser ->
                this.value.text = item.formatter(value)
                item.listener(value, fromUser)
            })
        }

        fun bind(item: DebuggermanItem.Slider) {
            this.item = item
            slider.value = item.initValue
            value.text = item.formatter(item.initValue)
        }
    }
}