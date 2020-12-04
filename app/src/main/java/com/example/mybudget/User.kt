package com.example.mybudget

public class User{
    var id: Int = 0
    var email: String = ""
    var password: String = ""
    var userName: String = ""
    var userType: String = ""
    var age: Int = 0
    var image: String = ""
    var relation: String = ""
    var relationEmail: String = ""
    constructor(){}
    constructor(userName: String, password: String, email: String, userType: String, age: Int, image: String, relation: String, relationEmail: String){
        this.userName = userName
        this.password = password
        this.email = email
        this.userType = userType
        this.age = age
        this.image = image
        this.relation = relation
        this.relationEmail = relationEmail
    }
}