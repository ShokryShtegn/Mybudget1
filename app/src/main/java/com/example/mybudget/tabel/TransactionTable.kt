package com.example.mybudget.tabel

class TransactionTable {
    var id: Int = 0
    var user_id: Int = 0
    var type : String = ""
    var category: String = ""
    var amount: Int = 0
    var note: String = ""
    var date: String = ""
    var wallet: String = ""

    /*var event_id: Int = 0
    var photos: String = ""*/

    constructor(){}
    constructor( type: String , category: String, amount: Int, note: String, date: String, wallet: String/*photos: String*/){
        this.type = type
        this.category = category
        this.amount = amount
        this.note = note
        this.date = date
        this.wallet = wallet

        /*this.event_id = event_id
        this.photos = photos*/
    }
}