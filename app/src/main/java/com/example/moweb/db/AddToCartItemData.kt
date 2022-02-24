package com.example.moweb.db


class AddToCartItemData {
    var id: Int = 0
    var product_id: String? = null
    var qty: String? = null


    constructor(
        product_id: String,
        qty: String
    ) {
        this.product_id = product_id
        this.qty = qty


    }
}