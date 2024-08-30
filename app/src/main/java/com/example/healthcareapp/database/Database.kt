package com.example.healthcareapp.database

import android.content.Context
import com.example.healthcareapp.Dataclass.Cart
import com.example.healthcareapp.Dataclass.Order
import com.example.healthcareapp.Dataclass.User
import com.google.firebase.database.*

class Database(context: Context?) {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val usersReference: DatabaseReference = firebaseDatabase.getReference("users")
    private val cartsReference: DatabaseReference = firebaseDatabase.getReference("carts")
    private val ordersReference: DatabaseReference = firebaseDatabase.getReference("orders")

    fun registerActivity(username: String, email: String, password: String, callback: (Boolean) -> Unit) {
        val user = User(username, email, password)
        usersReference.child(username).setValue(user).addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    fun loginActivity(username: String, password: String, callback: (Boolean) -> Unit) {
        usersReference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                callback(user != null && user.password == password)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }

    fun addCart(username: String, product: String, price: Float, otype: String) {
        val cartRef = cartsReference.push()
        val cartData = mapOf(
            "username" to username,
            "product" to product,
            "price" to price,
            "otype" to otype
        )
        cartRef.setValue(cartData)
    }

    fun checkCart(username: String, product: String, callback: (Boolean) -> Unit) {
        cartsReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var result = false
                    for (cartSnapshot in snapshot.children) {
                        val cart = cartSnapshot.getValue(Cart::class.java)
                        if (cart?.product == product) {
                            result = true
                            break
                        }
                    }
                    callback(result)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false)
                }
            })
    }

    fun removeCart(username: String, product: String) {
        cartsReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children) {
                        val cart = cartSnapshot.getValue(Cart::class.java)
                        if (cart?.product == product) {
                            cartSnapshot.ref.removeValue()
                            break
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }

    fun getCartData(username: String, callback: (List<String>) -> Unit) {
        val cartData: MutableList<String> = mutableListOf()

        cartsReference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.child("product").getValue(String::class.java)
                    val price = snapshot.child("price").getValue(Float::class.java)
                    if (product != null && price != null) {
                        cartData.add("$product$$price")
                    }
                }
                callback(cartData)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    fun addOrder(username: String, fullname: String, address: String, contact: String, pincode: Int, date: String, time: String, price: Float, otype: String, orderName: String, orderType: String, orderAmount: Float, orderDelivery: String, orderDetails: String) {
        val order = Order(username, fullname, address, contact, pincode, date, time, price, otype, orderName, orderType, orderAmount, orderDelivery, orderDetails)
        val orderKey = ordersReference.push().key // Generate a new key for the order
        orderKey?.let {
            ordersReference.child(it).setValue(order)
        }
    }

    fun getOrderData(username: String, callback: (List<Order>) -> Unit) {
        ordersReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orders = mutableListOf<Order>()
                for (snapshot in dataSnapshot.children) {
                    val order = snapshot.getValue(Order::class.java)
                    order?.let { orders.add(it) }
                }
                callback(orders)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}



