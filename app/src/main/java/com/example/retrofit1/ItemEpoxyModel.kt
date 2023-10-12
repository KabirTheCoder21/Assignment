package com.example.retrofit1

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class ItemEpoxyModel(private val userItem: UserItem) : EpoxyModelWithHolder<ItemEpoxyModel.Holder>() {

    class Holder : EpoxyHolder() {
        lateinit var itemView: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
        }

        fun bindData(userItem: UserItem) {
            itemView.findViewById<TextView>(R.id.name).text = userItem.name
            itemView.findViewById<TextView>(R.id.username).text = userItem.username
            itemView.findViewById<TextView>(R.id.email).text = userItem.email
            itemView.findViewById<TextView>(R.id.phone).text = userItem.phone

            itemView.findViewById<TextView>(R.id.city).text = userItem.address.city
            itemView.findViewById<TextView>(R.id.street).text = userItem.address.street
            itemView.findViewById<TextView>(R.id.lat).text = userItem.address.geo.lat
            itemView.findViewById<TextView>(R.id.lng).text = userItem.address.geo.lng
        }
    }

    override fun bind(holder: Holder) {
        holder.bindData(userItem)
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item // Create this XML layout
    }

    override fun createNewHolder(parent: ViewParent): Holder {
        return Holder()
    }
}
