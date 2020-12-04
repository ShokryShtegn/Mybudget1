package com.example.mybudget

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_add_debt.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Global {
    companion object {
        @JvmField
        //1 means data is synced and 0 means data is not synced
        val SYNCED_WITH_SERVER: Int = 1
        val NOT_SYNCED_WITH_SERVER: Int = 0

        // SERVER URLs
        val URL_ADD_GOAL: String = "http://127.0.0.1:8060/myProject/myBudgets/addGoal.php"
        val URL_UPDATE_GOAL_IS_STARRED = ""


        // Other
        var USER_ID = 0
        var convertedValue : Double = 0.0

        // = = = = = = = = = = = = = = = = = WALLET CONSTANTS = == = = = = = = = = = = = = = =
        var WALLET_NAME: String? = null

        // = = = = = = = = = = = = = = = = = GOAL CONSTANTS = == = = = = = = = = = = = = = =
        var GOAL: Boolean = true
        var GOAL_ID: Int = -1
        var GOAL_USER: Int = USER_ID
        var GOAL_WALLET: Int = -1
        var GOAL_NAME: String? = null
        var GOAL_AMOUNT: Double = 0.0
        var GOAL_CURRENCY: String = "EUR"
        @RequiresApi(Build.VERSION_CODES.O)
        var GOAL_TARGET_DATE: String = getCurrentDate()
        var GOAL_COLOR: Int = 0
        var GOAL_ACHIEVE: Int = 1
        var GOAL_STARRED: Int = 0
        var GOAL_STATUS: Int = 0

        // = = = = = = = = = = = = = = GOAL TRANSACTION CONSTANTS = == = = = = = = = = = = =
        var GOAL_TRANSACTION: Boolean = true
        var GOAL_TRANSACTION_ID: Int = -1
        var GOAL_TRANSACTION_GOAL_ID: Int = GOAL_ID
        var GOAL_TRANSACTION_TAYPE: Int = 0
        var GOAL_TRANSACTION_AMOUNT: Double = 0.0
        var GOAL_TRANSACTION_CURRENCY: String = GOAL_CURRENCY
        @RequiresApi(Build.VERSION_CODES.O)
        var GOAL_TRANSACTION_DATE: String = getCurrentDate()
        @RequiresApi(Build.VERSION_CODES.O)
        var GOAL_TRANSACTION_TIME: String = getCurrentTime()
        var GOAL_TRANSACTION_MEMO: String? = null
        var GOAL_TRANSACTION_STATUS: Int = 0

        // = = = = = = = = = = = = = = = = = Debt CONSTANTS = == = = = = = = = = = = = = = =
        var DEBT: Boolean = true
        var DEBT_ID: Int = -1
        var DEBT_USER_ID: Int = USER_ID
        var DEBT_TYPE: Int = 0
        var DEBT_NAME: String? = null
        var DEBT_DESCRIPTION: String? = null
        var DEBT_AMOUNT: Double = 0.0
        var DEBT_CURRENCY: String = "EUR"
        @RequiresApi(Build.VERSION_CODES.O)
        var DEBT_DATE: String = getCurrentDate()
        @RequiresApi(Build.VERSION_CODES.O)
        var DEBT_TIME: String = getCurrentTime()
        var DEBT_WALLET_ID: Int = GOAL_WALLET
        var DEBT_COLOR: Int = 0
        var DEBT_STATUS: Int = 0

        // = = = = = = = = = = = = =  = UP ACTIVITY CONSTANTS = = = = = = = = = = = = = = =
        var UP: String? = null

        public fun initUp() {
            UP = null
        }

        public fun initGoal() {
            GOAL = true
            GOAL_ID = -1
            GOAL_USER = USER_ID
            GOAL_WALLET = -1
            GOAL_NAME = null
            GOAL_AMOUNT = 0.0
            GOAL_CURRENCY = "EUR"
            @RequiresApi(Build.VERSION_CODES.O)
            GOAL_TARGET_DATE = getCurrentDate()
            GOAL_COLOR = 0
            GOAL_ACHIEVE = 0
            GOAL_STARRED = 0
            GOAL_STATUS = 0
        }

        public fun initGoalTransaction() {
            GOAL_TRANSACTION = true
            GOAL_TRANSACTION_ID = -1
            GOAL_TRANSACTION_GOAL_ID = GOAL_ID
            GOAL_TRANSACTION_TAYPE = 0
            GOAL_TRANSACTION_AMOUNT = 0.0
            GOAL_TRANSACTION_CURRENCY = GOAL_CURRENCY
            @RequiresApi(Build.VERSION_CODES.O)
            GOAL_TRANSACTION_DATE = getCurrentDate()
            @RequiresApi(Build.VERSION_CODES.O)
            GOAL_TRANSACTION_TIME = getCurrentTime()
            GOAL_TRANSACTION_MEMO = null
            GOAL_TRANSACTION_STATUS = 0
        }

        public fun initDebt(){
            DEBT = true
            DEBT_ID = -1
            DEBT_USER_ID = USER_ID
            DEBT_TYPE = 0
            DEBT_NAME = null
            DEBT_DESCRIPTION = null
            DEBT_AMOUNT = 0.0
            DEBT_CURRENCY = "EUR"
            @RequiresApi(Build.VERSION_CODES.O)
            DEBT_DATE = getCurrentDate()
            @RequiresApi(Build.VERSION_CODES.O)
            DEBT_TIME = getCurrentTime()
            DEBT_WALLET_ID = GOAL_WALLET
            DEBT_COLOR = 0
            DEBT_STATUS = 0
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun getCurrentDate(): String {
            val current_date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatted = current_date.format(formatter) as String
            return formatted
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun getCurrentTime(): String {
            val current_date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val formatted = current_date.format(formatter) as String
            return formatted
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun getDayOfWeek(date: String): String{
            val date: LocalDate = LocalDate.parse(date)
            val day: DayOfWeek = date.getDayOfWeek()
            return day.toString()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun getDayOfMonth(date: String): String{
            val mDate: LocalDate = LocalDate.parse(date)
            val day = mDate.dayOfMonth
            return day.toString()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun getMonthAndYear(date: String): String{
            val mDate: LocalDate = LocalDate.parse(date)
            val month = mDate.month
            val year = mDate.year
            return ""+ month.toString() + " " + year.toString()
        }

        public fun myColor(index: Int): Int {
            var result: Int = 0
            when (index) {
                0 -> result = R.color.RED
                1 -> result = R.color.PINK
                2 -> result = R.color.PURPLE
                3 -> result = R.color.DEEPPURPLE
                4 -> result = R.color.INDIGO
                5 -> result = R.color.BLUE
                6 -> result = R.color.LIGHTBLUE
                7 -> result = R.color.CYAN
                8 -> result = R.color.TEAL
                9 -> result = R.color.GREEN
                10 -> result = R.color.LIGHTGREEN
                11 -> result = R.color.LIME
                12 -> result = R.color.YELLOW
                13 -> result = R.color.AMBER
                14 -> result = R.color.ORANGE
                15 -> result = R.color.DEEPORANGE
                16 -> result = R.color.BROWN
                17 -> result = R.color.GRAY
                18 -> result = R.color.BLUEGREY
            }
            return result
        }

        public fun myDrawable(index: Int): Int {
            var result: Int = 0
            when (index) {
                0 -> result = R.drawable.red
                1 -> result = R.drawable.pink
                2 -> result = R.drawable.purple
                3 -> result = R.drawable.deep_purple
                4 -> result = R.drawable.indigo
                5 -> result = R.drawable.blue
                6 -> result = R.drawable.light_blue
                7 -> result = R.drawable.cyan
                8 -> result = R.drawable.teal
                9 -> result = R.drawable.green
                10 -> result = R.drawable.light_green
                11 -> result = R.drawable.lime
                12 -> result = R.drawable.yellow
                13 -> result = R.drawable.amber
                14 -> result = R.drawable.orange
                15 -> result = R.drawable.deep_orange
                16 -> result = R.drawable.brown
                17 -> result = R.drawable.gray
                18 -> result = R.drawable.blue_gray
            }
            return result
        }

        @RequiresApi(Build.VERSION_CODES.O)
        public fun daysLeft(date: String?): String {
            val current_date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formatted = current_date.format(formatter) as String
            val date1: Date
            val date2: Date
            val dates = SimpleDateFormat("yyyy-MM-dd")
            date1 = dates.parse(date)
            date2 = dates.parse(formatted)
            val difference: Long = Math.abs(date1.time - date2.time)
            val differenceDates = difference / (24 * 60 * 60 * 1000)
            val dayDifference = differenceDates.toString()
            return dayDifference
        }
    }
}
