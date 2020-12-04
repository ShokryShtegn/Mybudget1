package com.example.mybudget.tabel

class MainIncome {
    var id: Int = 0
    var user_id: Int = 0
    var income : Int = 0
    var saving: Int = 0
    var first_date: String = ""
    var final_date: String = ""

    constructor(){}
    constructor( income: Int, saving: Int, first_date: String, final_date: String){
        this.income = income
        this.saving = saving
        this.first_date = first_date
        this.final_date = final_date
    }
}