package com.example.mybudget.models

public class GoalTransaction{
    private var id: Int = 0
    private var goal : Int = 0
    private var type: Int = 0
    private var amount: Double = 0.0
    private var currency: String? = null
    private var date: String? = null
    private var time: String? = null
    private var memo: String? = null
    private var status: Int = 0
    constructor(){

    }
    constructor(
        goal: Int, type: Int, amount: Double, currency: String, date: String,
        time: String, memo: String, status: Int
    ){
        this.setGoal(goal)
        this.setType(type)
        this.setAmount(amount)
        this.setCurrency(currency)
        this.setDate(date)
        this.setTime(time)
        this.setMemo(memo)
        this.setStatus(status)
    }

    public fun getId():Int{
        return this.id
    }
    public fun setId(id: Int){
        this.id = id
    }
    public fun getGoal():Int{
        return this.goal
    }
    public fun setGoal(goal: Int){
        this.goal = goal
    }
    public fun getType():Int{
        return this.type
    }
    public fun setType(type: Int){
        this.type = type
    }
    public fun getAmount():Double{
        return this.amount
    }
    public fun setAmount(amount: Double){
        this.amount = amount
    }
    public fun getCurrency(): String?{
        return this.currency
    }
    public fun setCurrency(currency: String){
        this.currency = currency
    }
    public fun getDate(): String?{
        return this.date
    }
    public fun setDate(date: String){
        this.date = date
    }
    public fun getTime(): String?{
        return this.time
    }
    public fun setTime(time: String){
        this.time = time
    }
    public fun getMemo(): String?{
        return this.memo
    }
    public fun setMemo(memo: String){
        this.memo = memo
    }
    public fun getStatus():Int{
        return this.status
    }
    public fun setStatus(status: Int){
        this.status = status
    }
}