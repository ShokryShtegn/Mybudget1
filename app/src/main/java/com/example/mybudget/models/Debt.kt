package com.example.mybudget.models

public class Debt{
    private var id: Int = 0
    private var user: Int = 0
    private var wallet: Int = 0
    private var type: Int = 0
    private var name: String? = null
    private var description: String? = null
    private var amount: Double = 0.0
    private var currency: String? = null
    private var date: String? = null
    private var time: String? = null
    private var color: Int = 0
    private var status: Int = 0

    constructor()
    constructor(
        id: Int, user: Int, wallet: Int, type: Int, name: String?, description: String?, amount: Double, currency: String?, date: String?,
        time: String?, color: Int, status: Int
    ){
        this.setId(id)
        this.setUser(user)
        this.setWallet(wallet)
        this.setType(type)
        this.setName(name)
        this.setDescription(description)
        this.setAmount(amount)
        this.setCurrency(currency)
        this.setDate(date)
        this.setTime(time)
        this.setColor(color)
        this.setStatus(status)
    }
    @JvmName("getId1")
    public fun getId() : Int{
        return this.id
    }
    @JvmName("setId1")
    public fun setId(id: Int){
        this.id = id
    }
    @JvmName("getUser1")
    public fun getUser(): Int{
        return this.user
    }
    @JvmName("setUser1")
    public fun setUser(user: Int) {
        this.user = user
    }
    public fun getWallet(): Int{
        return  this.wallet
    }
    public fun setWallet(wallet: Int){
        this.wallet = wallet
    }
    public fun getType(): Int{
        return  this.type
    }
    public fun setType(type: Int){
        this.type = type
    }
    @JvmName("getName1")
    public fun getName() : String? {
        return this.name
    }
    @JvmName("setName1")
    public fun setName(name: String?){
        this.name = name
    }
    public fun getDescription() : String? {
        return this.name
    }
    public fun setDescription(description: String?){
        this.description = description
    }
    @JvmName("getAmount1")
    public fun getAmount() : Double{
        return this.amount
    }
    @JvmName("setAmount1")
    public fun setAmount(amount: Double){
        this.amount = amount
    }
    @JvmName("getCurrency1")
    public fun getCurrency() : String?{
        return this.currency
    }
    @JvmName("setCurrency1")
    public fun setCurrency(currency: String?){
        this.currency = currency
    }
    @JvmName("getDate1")
    public fun getDate() : String?{
        return this.date
    }
    @JvmName("setDate1")
    public fun setDate(date: String?){
        this.date= date
    }
    public fun getTime() : String?{
        return this.time
    }
    public fun setTime(time: String?){
        this.time = time
    }
    @JvmName("getColor1")
    public fun getColor() : Int{
        return this.color
    }
    @JvmName("setColor1")
    public fun setColor(color: Int){
        this.color = color
    }
    @JvmName("getStatus1")
    public fun getStatus() : Int{
        return this.status
    }
    @JvmName("setStatus1")
    public fun setStatus(status: Int){
        this.status = status
    }
}