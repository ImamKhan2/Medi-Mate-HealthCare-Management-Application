package com.example.healthcareapp.Dataclass

data class Order(
    val username: String,
    val fullname: String? = null,
    val address: String? = null,
    val contact: String? = null, // Assuming pincode is an integer
    val pincode: Int = 0,
    val date: String? = null,
    val time: String? = null,
    val price: Float? = null,
    val otype: String? = null,
    val orderName: String = "",
    val orderType: String = "",
    val orderAmount: Float = 0.0f,
    val orderDelivery: String = "",
    val orderDetails: String = ""
)


