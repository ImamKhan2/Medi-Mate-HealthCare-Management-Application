package com.example.healthcareapp.Dataclass

data class User(val username: String, val email: String,val password: String){
    constructor() : this( "", "","")
}
