package com.example.mybudget

class Sellers {
    var id: Int = 0
    var email: String = ""
    var password: String = ""
    var userName: String = ""
    var age: Int = 0
    var image: String = ""

    constructor(){}
    constructor(userName: String, password: String, email: String, age: Int, image: String){
        this.userName = userName
        this.password = password
        this.email = email
        this.age = age
        this.image = image
    }
}