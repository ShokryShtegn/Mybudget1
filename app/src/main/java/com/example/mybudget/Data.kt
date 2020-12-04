package com.example.mybudget


class Data {
    var id: Int = 0
    var income: Float = 0.0f
    var saving: Float = 0.0f

    constructor(){}
    constructor(income: Float, saving: Float){
        this.income = income
        this.saving = saving
    }

}