package com.example.mybudget.models

public class Goal{
    private var id: Int = 0
    private var user : Int = 0
    private var wallet: Int = 0
    private var name: String? = null
    private var amount: Double = 0.0
    private var currency: String? = null
    private var targetDate: String? = null
    private var color: Int = 0
    private var achieve: Int = 0
    private var starred: Int = 0
    private var status: Int = 0
    constructor(){

    }
    constructor(
        id: Int, user: Int, wallet: Int, name: String, amount: Double, currency: String, targetDate: String,
        color: Int, achieve: Int, starred: Int, status: Int
    ){
        this.setId(id)
        this.setUser(user)
        this.setWallet(wallet)
        this.setName(name)
        this.setAmount(amount)
        this.setCurrency(currency)
        this.setTargetDate(targetDate)
        this.setColor(color)
        this.setAchieve(achieve)
        this.setStarred(starred)
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
    @JvmName("getName1")
    public fun getName() : String? {
        return this.name
    }
    @JvmName("setName1")
    public fun setName(name: String){
        this.name = name
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
    public fun setCurrency(currency: String){
        this.currency = currency
    }
    @JvmName("getTargetDate1")
    public fun getTargetDate() : String?{
        return this.targetDate
    }
    @JvmName("setTargetDate1")
    public fun setTargetDate(targetDate: String){
        this.targetDate= targetDate
    }
    @JvmName("getColor1")
    public fun getColor() : Int{
        return this.color
    }
    @JvmName("setColor1")
    public fun setColor(color: Int){
        this.color = color
    }
    @JvmName("getAchieve1")
    public fun getAchieve(): Int{
        return this.achieve
    }
    @JvmName("setAchieve1")
    public fun setAchieve(achieve: Int){
        this.achieve = achieve
    }
    @JvmName("getStarred1")
    public fun getStarred() : Int{
        return this.starred
    }
    @JvmName("setStarred1")
    public fun setStarred(starred: Int){
        this.starred = starred
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