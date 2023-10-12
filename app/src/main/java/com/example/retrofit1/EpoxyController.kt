package com.example.retrofit1

import com.airbnb.epoxy.TypedEpoxyController

class EpoxyController: TypedEpoxyController<List<UserItem>>() {
    override fun buildModels(data: List<UserItem>?) {
        if (data != null) {
            data.forEach{
                ItemEpoxyModel(it)
                    .id(it.id)
                    .addTo(this)
            }
        }
    }
}