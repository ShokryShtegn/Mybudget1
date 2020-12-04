package com.example.mybudget.models

import com.example.mybudget.R
import kotlinx.android.synthetic.main.activity_ongoing_goal_info.view.*

class SubItem {
    private var subItemImage = 0
    private var subItemName: Int = 0
    private var subItemAmount: String? = null
    private var subItemTime: String? = null
    private var subItemMemo: String? = null

    constructor()
    constructor(subItemImage: Int, subItemName: Int, subItemAmount: String, subItemTime: String?, subItemMemo: String?) {
        if(subItemImage == 0){
            this.setSubItemImage(R.drawable.ic_withdraw_white)
        } else{
            this.setSubItemImage(R.drawable.ic_deposit_white)
        }
        if(subItemName == 0){
            this.setSubItemName("Withdraw")
        } else{
            this.setSubItemName("Deposit")
        }
        this.setSubItemAmount(subItemAmount)
        this.setSubItemTime(subItemTime)
        this.setSubItemMemo(subItemMemo)
    }
    public fun getSubItemImage(): Int {
        return subItemImage
    }
    public fun setSubItemImage(subItemImage: Int) {
        this.subItemImage = subItemImage
    }
    public fun getSubItemName(): String? {
        if(this.subItemName == 1){
            return "Withdraw"
        }
        return "Deposit"
    }
    public fun setSubItemName(subItemName: String?) {
        if(subItemName.equals("Withdraw")){
            this.subItemName = 1
        } else{
            this.subItemName = 0
        }
    }
    public fun getSubItemAmount(): String? {
        return this.subItemAmount
    }
    public fun setSubItemAmount(subItemAmount: String?) {
        if(getSubItemName().equals("Withdraw")){
            this.subItemAmount = "- " + subItemAmount
        } else{
            this.subItemAmount = subItemAmount
        }
    }
    public fun getSubItemTime(): String? {
        return this.subItemTime
    }
    public fun setSubItemTime(subItemTime: String?) {
        this.subItemTime = subItemTime
    }
    public fun getSubItemMemo(): String? {
        return this.subItemMemo
    }
    public fun setSubItemMemo(subItemMemo: String?) {
        this.subItemMemo = subItemMemo
    }
}