package com.example.mybudget.tabel

import java.util.*

public class Goal{
    var id: Int = 0
    var user : String = ""
    var name: String = ""
    var amount: String = ""
    var currency: String = ""
    var targetDate: String = ""
    var color: String = ""
    var achive: Boolean = false

    constructor(){}
    constructor(user: String, name: String, amount: String, currency: String, targetDate: String, color: String, achive: Boolean){
        this.user = user
        this.name = name
        this.amount = amount
        this.currency = currency
        this.targetDate = targetDate
        this.color = color
        this.achive = achive
    }
}