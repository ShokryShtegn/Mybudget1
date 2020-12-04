package com.example.mybudget.models

import com.example.mybudget.Wallets
import java.lang.reflect.Array

class Item {
    private var itemDayOfMonth: String? = null
    private var itemDayOfWeek: String? = null
    private var itemMonthYear: String? = null
    private var itemTotalAmount: String? = null
    private var subItemList: MutableList<SubItem>? = ArrayList()

    constructor(itemDayOfMonth: String?, itemDayOfWeek: String?, itemMonthYear: String?, itemTotalAmount: String?, subItemList: MutableList<SubItem>) {
        this.setItemDayOfMonth(itemDayOfMonth)
        this.setItemDayOfWeek(itemDayOfWeek)
        this.setItemMonthYear(itemMonthYear)
        this.setItemTotalAmount(itemTotalAmount)
        this.setSubItemList(subItemList)
    }
    public fun getItemDayOfMonth(): String? {
        return this.itemDayOfMonth
    }
    public fun setItemDayOfMonth(itemDayOfMonth: String?) {
        this.itemDayOfMonth = itemDayOfMonth
    }
    public fun getItemDayOfWeek(): String? {
        return this.itemDayOfWeek
    }
    public fun setItemDayOfWeek(itemDayOfWeek: String?) {
        this.itemDayOfWeek = itemDayOfWeek
    }
    public fun getItemMonthYear(): String? {
        return this.itemMonthYear
    }
    public fun setItemMonthYear(itemMonthYear: String?) {
        this.itemMonthYear = itemMonthYear
    }
    public fun getSubItemTotalAmount(): String? {
        return this.itemTotalAmount
    }
    public fun setItemTotalAmount(itemTotalAmount: String?) {
        this.itemTotalAmount = itemTotalAmount
    }
    public fun getSubItemList(): MutableList<SubItem>? {
        return this.subItemList
    }
    public fun setSubItemList(subItemList: MutableList<SubItem>?) {
        this.subItemList = subItemList
    }
}
