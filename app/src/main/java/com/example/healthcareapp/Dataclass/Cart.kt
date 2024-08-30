package com.example.healthcareapp.Dataclass

/*data class Cart(
    val username: String? = null,
    val product: String? = null,
    val price: Float? = null,
    val otype: String? = null
)*/

data class Cart(val username: String, val product: String, val otype: String, val price: Float){
    constructor() : this( "", "","", 0.0f)
}