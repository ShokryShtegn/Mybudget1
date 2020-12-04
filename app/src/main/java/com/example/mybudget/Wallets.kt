package com.example.mybudget

public class Wallets {
    var id: Int = 0
    var wallet_User_ID: Int = 0
    var wallet_name : String = ""
    var wallet_type: String = ""
    var currency: String = ""
    var balance: String = ""
    var include_flag: Boolean = false

    constructor(){}
    constructor( wallet_name: String, wallet_type: String, currency: String, balance: String, include_flag: Boolean){
        this.wallet_name = wallet_name
        this.wallet_type = wallet_type
        this.currency = currency
        this.balance = balance
        this.include_flag = include_flag
    }
}