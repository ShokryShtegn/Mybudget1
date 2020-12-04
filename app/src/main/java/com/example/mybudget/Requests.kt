package com.example.mybudget

class Requests {
    var id: Int = 0
    var request_user_id: Int = 0
    var emailFrom: String = ""
    var emailTo: String = ""
    var date: String = ""
    var acceptDate: String = ""
    var dismissDate: String = ""
    var accept: String = ""
    var dismiss: String = ""
    var later: String = ""

    constructor(){}
    constructor(emailFrom: String, emailTo: String, date: String, acceptDate: String, accept: String, dismiss: String, later: String, dismissDate: String){
        this.emailFrom = emailFrom
        this.emailTo = emailTo
        this.date = date
        this.acceptDate = acceptDate
        this.accept = accept
        this.dismiss = dismiss
        this.later = later
        this.dismissDate = dismissDate
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
    @JvmName("getEmailFrom1")
    public fun getEmailFrom(): String{
        return emailFrom
    }
    @JvmName("setEmailFrom1")
    public fun setEmailFrom(emailFrom: String){
        this.emailFrom = emailFrom
    }
    @JvmName("getEmailTo1")
    public fun getEmailTo(): String{
        return emailTo
    }
    @JvmName("setEmailTo1")
    public fun setEmailTo(emailTo: String){
        this.emailTo = emailTo
    }
    @JvmName("getDate1")
    public fun getDate(): String{
        return date
    }
    @JvmName("setDate1")
    public fun setDate(date: String){
        this.date = date
    }
    @JvmName("getAcceptDate1")
    public fun getAcceptDate(): String{
        return acceptDate
    }
    @JvmName("setAcceptDate1")
    public fun setAcceptDate(acceptDate: String){
        this.acceptDate = acceptDate
    }
    @JvmName("getAccept1")
    public fun getAccept(): String{
        return accept
    }
    @JvmName("setAccept1")
    public fun setAccept(accept: String){
        this.accept = accept
    }
    @JvmName("getDismiss1")
    public fun getDismiss(): String{
        return dismiss
    }
    @JvmName("setDismiss1")
    public fun setDismiss(dismiss: String){
        this.dismiss = dismiss
    }
    @JvmName("getLater1")
    public fun getLater(): String{
        return later
    }
    @JvmName("setLater1")
    public fun setLater(later: String){
        this.later = later
    }
    @JvmName("getDismissDate1")
    public fun getDismissDate(): String{
        return dismissDate
    }
    @JvmName("setDismissDate1")
    public fun setDismissDate(dismissDate: String){
        this.dismissDate = dismissDate
    }
}