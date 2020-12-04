package com.example.mybudget

import android.provider.ContactsContract

class Items {
    var id: Int = 0
    var item_User_ID: Int = 0
    var item_name : String = ""
    var item_amount: Int = 0
    var item_price: Float = 0.0f
    var item_desc: String = ""
    var item_avatar: String = ""
    var item_country: String = ""
    var item_status: String = ""
    var item_category:String = ""
    var item_Appropriate_Age: String = ""
    var item_dateAdded: String = ""
    var item_updateDate: String = ""
    constructor(){}
    constructor( item_name : String, item_amount: Int, item_price: Float, item_desc: String, item_avatar: String, item_country: String, item_status: String, item_category:String, item_Appropriate_Age: String, item_dateAdded: String, item_updateDate: String){
        this.item_name = item_name
        this.item_amount = item_amount
        this.item_price = item_price
        this.item_desc = item_desc
        this.item_avatar = item_avatar
        this.item_country = item_country
        this.item_status = item_status
        this.item_category = item_category
        this.item_Appropriate_Age = item_Appropriate_Age
        this.item_dateAdded = item_dateAdded
        this.item_updateDate = item_updateDate
    }
    //getter setter
    // ================================= ID =====================================================
    @JvmName("getId1")
    public fun getId(): Int{
        return id
    }
    @JvmName("setId1")
    public fun setId(id: Int){
        this.id = id
    }
    // ================================= Name =====================================================
    public fun getItemName(): String{
        return item_name
    }
    public fun setItemName(item_name: String){
        this.item_name = item_name
    }

    // ================================= Amount =====================================================
    public fun getItemAmount(): Int{
        return item_amount
    }
    public fun setItemAmount(item_amount: Int){
        this.item_amount = item_amount
    }

    // ================================= Price =====================================================
    public fun getItemPrice(): Float{
        return item_price
    }
    public fun setItemPrice(item_price: Float){
        this.item_price = item_price
    }

    // ================================= Description =====================================================
    public fun getItemDesc(): String{
        return item_desc
    }
    public fun setItemDesc(item_desc: String){
        this.item_desc = item_desc
    }

    // ================================= Photo =====================================================
    public fun getItemAvatar(): String{
        return item_avatar
    }
    public fun setItemAvatar(item_avatar: String){
        this.item_avatar = item_avatar
    }

    // ================================= Country =====================================================
    public fun getItemCountry(): String{
        return item_country
    }
    public fun setItemCountry(item_country: String){
        this.item_country = item_country
    }

    // ================================= Status =====================================================
    public fun getItemStatus(): String{
        return item_status
    }
    public fun setItemStatus(item_status: String){
        this.item_status = item_status
    }

    // ================================= Category =====================================================
    public fun getItemCategory(): String{
        return item_category
    }
    public fun setItemCategory(item_category: String){
        this.item_category = item_category
    }

    // ================================= AppropriateAge =====================================================
    public fun getItemAppropriateAge(): String{
        return item_Appropriate_Age
    }
    public fun setItemAppropriateAge(item_Appropriate_Age: String){
        this.item_Appropriate_Age = item_Appropriate_Age
    }
    // ================================= dateAdded =====================================================
    public fun getDateAdded(): String{
        return item_dateAdded
    }
    public fun setDateAdded(item_dateAdded: String){
        this.item_dateAdded = item_dateAdded
    }
    // ================================= updateDate =====================================================
    public fun getUpdateDate(): String{
        return item_updateDate
    }
    public fun setUpdateDate(item_updateDate: String){
        this.item_updateDate = item_updateDate
    }
}