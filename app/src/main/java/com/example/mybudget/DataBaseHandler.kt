package com.example.mybudget

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import com.example.mybudget.tabel.MainIncome
import com.example.mybudget.tabel.TransactionTable

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "Users"
val COL_ID = "id"
val Col_UserName = "userName"
val COL_PASSWORD = "pass"
val COL_EMAIL = "email"
val COL_UserType = "userType"
val COL_Age = "age"
val COL_IMAGE = "imageUser"
val COL_RELATION = "relation"
val COL_RELATION_EMAIL = "relationEmail"

// = = = = = = = = = Transaction = = = = = = = = =
val TABLE_NAME3 = "transactions"
val COL_Transaction_ID = "id"
val COL_Transaction_User_ID = "user_id"
val COL_Transaction_Type = "trans_type"
val COL_Transaction_Category = "category"
val COL_Transaction_Amount = "trans_amount"
val COL_Transaction_Note = "note"
val COL_Transaction_Date = "date"

// = = = = = = = = =   Wallet    = = = = = = = = =
val TABLE_NAME4 = "wallets"
val COL_Wallet_ID = "id"
val COL_Wallet_User_ID = "user_id"
val COL_Wallet_Name = "wallet_name"
val COL_Wallet_Type = "wallet_type"
val COL_Wallet_Currency = "currency"
val COL_Wallet_Balance = "balance"
val COL_Wallet_Include_Flag = "include_flag"

// = = = = = = = = =    Seller    = = = = = = = = =
val TABLE_NAME5 = "Sellers"
val COL_Seller_ID = "id"
val COL_Seller_Name = "sellerName"
val COL_Seller_EMAIL = "email"
val COL_Seller_PASSWORD = "sellerPass"
val COL_Seller_AGE = "age"
val COL_Seller_Image = "imageSeller"
// = = = = = = = = =    Items    = = = = = = = = =
val TABLE_NAME6 = "Items"
val COL_Item_ID = "id"
val COL_Item_User_ID = "user_id"
val COL_Item_Name = "name"
val COL_Item_Amount = "amount"
val COL_Item_Price = "price"
val COL_Item_Description = "description"
val COL_Item_Avatar = "avatar"
val COL_Item_Country = "country"
val COL_Item_Status = "status"
val COL_Item_Category = "category"
val COL_Item_Appropriate_Age = "AppropriateAge"
val COL_Item_DateAdded = "currentDate"
val COL_Item_UpdateDate = "updateDate"

val COL_Item_Tags = "tags"

// = = = = = = = = = MainIncome = = = = = = = = =
val TABLE_NAME7 = "MainIncome"
val COL_MainIncome_ID = "id"
val COL_MainIncome_User_ID = "user_id"
val COL_MainIncome_Income = "income"
val COL_MainIncome_Saving = "saving"
val COL_MainIncome_From_Date = "from_date"
val COL_MainIncome_To_Date = "to_date"

// = = = = = = = = = Requests = = = = = = = = =
val TABLE_NAME8 = "Requests"
val COL_REQUEST_ID = "request_id"
val COL_REQUEST_USER_ID = "user_id"
val COL_REQUEST_EMAIL_FROM = "email_from"
val COL_REQUEST_EMAIL_TO = "email_to"
val COL_REQUEST_Date = "date"
val COL_REQUEST_ACCEPTED_DATE = "update_date"
val COL_REQUEST_DISMISS_DATE = "update_date"
val COL_REQUEST_ACCEPT = "accept"
val COL_REQUEST_DISMISS = "dismiss"
val COL_REQUEST_LATER = "later"

// = = = = = = = = = Dismiss Requests = = = = = = = = =
val TABLE_NAME9 = "dismissRequests"
val COL_DISMISS_ID = "request_id"
val COL_DISMISS_USER_ID = "user_id"
val COL_DISMISS_EMAIL_FROM = "email_from"
val COL_DISMISS_EMAIL_TO = "email_to"
val COL_DISMISS_Date = "date"

class DataBaseHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    // = = = = = = = = = Constants For Database : Goal Table = = = = = = = = =

    val TABLE_GOAl = "goal"
    val COL_GOAL_ID = "id"
    val COl_GOAL_USER_ID = "u_id"
    val COl_GOAL_WALLET_ID = "w_id"
    val COL_GOAL_NAME = "g_name"
    val COL_GOAL_AMOUNT = "g_amount"
    val COL_GOAL_CURRENCY = "g_currency"
    val COL_GOAL_TARGET_DATE = "g_target_date"
    val COL_GOAL_COLOR = "g_color"
    val COL_GOAL_ACHIEVE = "g_achieve"
    val COL_GOAL_STARRED = "g_starred"
    val COL_GOAL_STATUS = "g_status"

    // = = = = = = Constants For Database : Goal Transaction Table = = = = = =
    val TABLE_GOAL_TRANSACTION = "goal_transaction"
    val COL_GOAL_TRANSACTION_ID = "id"
    val COL_GOAL_TRANSACTION_GOAL_ID = "g_id"
    val COL_GOAL_TRANSACTION_TYPE = "g_t_type"
    val COL_GOAL_TRANSACTION_AMOUNT = "g_t_amount"
    val COL_GOAL_TRANSACTION_CURRENCY = "g_t_currency"
    val COL_GOAL_TRANSACTION_DATE = "g_t_date"
    val COL_GOAL_TRANSACTION_TIME = "g_t_time"
    val COL_GOAL_TRANSACTION_MEMO = "g_t_memo"
    val COL_GOAL_TRANSACTION_STATUS = "g_t_status"

    // = = = = = = = = = Constants For Database : Debt Table = = = = = = = = =
    val TABLE_DEBT = "debt"
    val COL_DEBT_ID = "id"
    val COl_DEBT_USER_ID = "u_id"
    val COL_DEBT_TYPE = "d_type"
    val COL_DEBT_NAME = "d_name"
    val COL_DEBT_DESCRIPTION = "d_description"
    val COL_DEBT_AMOUNT = "d_amount"
    val COL_DEBT_CURRENCY = "d_currency"
    val COL_DEBT_DATE = "d_date"
    val COL_DEBT_TIME= "d_time"
    val COL_DEBT_WALLET_ID = "w_id"
    val COL_DEBT_COLOR = "d_color"
    val COL_DEBT_STATUS = "d_status"

    // = = = = = = = = = = = = =Database Version = = = = = = = = = = = = = = =
    val DB_VERSION = 1

    // = = = = = = = = = = = = =Status Constants = = = = = = = = = = = = = = =
    val SYNC_STATUS_OK: Int =  0
    val SYNC_STATUS_Faild: Int =  1
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = " CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col_UserName + " VARCHAR(256) UNIQUE, " +
                COL_EMAIL + " VARCHAR(256), " +
                COL_PASSWORD + " VARCHAR(256), " +
                COL_UserType + " VARCHAR(256), " +
                COL_Age + " INTEGER, " +
                COL_IMAGE + " VARCHAR(256), " +
                COL_RELATION + " VARCHAR(256), " +
                COL_RELATION_EMAIL + " VARCHAR(256) " + ")";

                db?.execSQL(createTable)

        // Create transactionTable
        val createTransaction = " CREATE TABLE " + TABLE_NAME3 + "(" +
                COL_Transaction_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_Transaction_User_ID + " INT(10), " +
                COL_Transaction_Type + " VARCHAR(64), " +
                COL_Transaction_Category + " VARCHAR(256), " +
                COL_Transaction_Amount + " INT(10), " +
                COL_Transaction_Note + " VARCHAR(256), " +
                COL_Transaction_Date + " VARCHAR(256), " +
                // COL_Transaction_wallet + " VARCHAR(256), " +
                " CONSTRAINT fk_Users FOREIGN KEY ("+COL_Transaction_User_ID+") REFERENCES "+TABLE_NAME+"("+COL_ID+") ON DELETE CASCADE);"
        db?.execSQL(createTransaction)
        //COL_Transaction_Photos + " VARCHAR(1024), " +

        // Create Wallet Table
        val walletTable = " CREATE TABLE " + TABLE_NAME4 + "(" +
            COL_Wallet_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_Wallet_User_ID + " INT(10), " +
            COL_Wallet_Name + " VARCHAR(256), " +
            COL_Wallet_Type + " VARCHAR(128), " +
            COL_Wallet_Currency + " VARCHAR(128), " +
            COL_Wallet_Balance + " INT(10), " +
            COL_Wallet_Include_Flag + " BOOLEAN, "  +
                " CONSTRAINT fk_Users FOREIGN KEY ("+COL_Wallet_User_ID+") REFERENCES "+TABLE_NAME+"("+COL_ID+") ON DELETE CASCADE);"
        db?.execSQL(walletTable)

        // Create Seller table
        val createSeller = " CREATE TABLE " + TABLE_NAME5 + "(" +
                COL_Seller_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_Seller_Name + " VARCHAR(256) UNIQUE, " +
                COL_Seller_EMAIL + " VARCHAR(256), " +
                COL_Seller_PASSWORD + " VARCHAR(256), " +
                COL_Seller_AGE + " INTEGER, " +
                COL_Seller_Image + " VARCHAR(256) " + ")";

        db?.execSQL(createSeller)

        // Create Items Table
        val createItems = " CREATE TABLE " + TABLE_NAME6 + "(" +
                COL_Item_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_Item_User_ID + " INTEGER, " +
                COL_Item_Name + " VARCHAR(256), " +
                COL_Item_Amount + " INTEGER, " +
                COL_Item_Price + " FLOAT, " +
                COL_Item_Description + " VARCHAR(256), " +
                COL_Item_Avatar + " VARCHAR(256), " +
                COL_Item_Country + " VARCHAR(256), " +
                COL_Item_Status + " VARCHAR(256), " +
                COL_Item_Category + " VARCHAR(256), " +
                COL_Item_Appropriate_Age + " VARCHAR(256), " +
                COL_Item_DateAdded + " VARCHAR(256), " +
                COL_Item_UpdateDate + " VARCHAR(256), " +
                " CONSTRAINT fk_Sellers FOREIGN KEY ("+COL_Item_User_ID+") REFERENCES "+TABLE_NAME5+"("+COL_Seller_ID+") ON DELETE CASCADE);"
        db?.execSQL(createItems)

        // Create MainIncome Table
        val MainIncomeTable = " CREATE TABLE " + TABLE_NAME7 + "(" +
                COL_MainIncome_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MainIncome_User_ID + " INT(10), " +
                COL_MainIncome_Income + " INT(10), " +
                COL_MainIncome_Saving + " INT(10), " +
                COL_MainIncome_From_Date + " VARCHAR(128), " +
                COL_MainIncome_To_Date + " VARCHAR(128), " +
                " CONSTRAINT fk_Users FOREIGN KEY ("+COL_MainIncome_User_ID +") REFERENCES "+TABLE_NAME+"("+COL_ID+") ON DELETE CASCADE);"
        db?.execSQL(MainIncomeTable)

        // = = = = = = = = = = = =Create Debt Table = = = = = = = = = = = = =

        var sql = "CREATE TABLE " + TABLE_GOAl + "(" +
                COL_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COl_GOAL_USER_ID + " INTEGER NOT NULL, " +
                COl_GOAL_WALLET_ID + " INTEGER NOT NULL, " +
                COL_GOAL_NAME + " INTEGER NOT NULL, " +
                COL_GOAL_AMOUNT + " DOUBLE NOT NULL, " +
                COL_GOAL_CURRENCY + " VARCHAR NOT NULL, " +
                COL_GOAL_TARGET_DATE + " VARCHAR NOT NULL, " +
                COL_GOAL_COLOR + " INTEGER NOT NULL, " +
                COL_GOAL_ACHIEVE + " INTEGER NOT NULL, " +
                COL_GOAL_STARRED + " INTEGER NOT NULL, " +
                COL_GOAL_STATUS + " INTEGER NOT NULL, " +
                " CONSTRAINT goal_ibfk_1 FOREIGN KEY (" + COl_GOAL_USER_ID + ") REFERENCES "+ TABLE_NAME + "(" + COL_ID + "), " +
                " CONSTRAINT goal_ibfk_2 FOREIGN KEY (" + COl_GOAL_WALLET_ID + ") REFERENCES " + TABLE_NAME4 + "(" + COL_Wallet_ID + ") ON DELETE CASCADE);"
        db?.execSQL(sql)

        sql = "CREATE TABLE " + TABLE_GOAL_TRANSACTION + "(" +
                COL_GOAL_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COL_GOAL_TRANSACTION_GOAL_ID + " INTEGER NOT NULL, " +
                COL_GOAL_TRANSACTION_TYPE + " INTEGER NOT NULL, " +
                COL_GOAL_TRANSACTION_AMOUNT + " DOUBLE NOT NULL, " +
                COL_GOAL_TRANSACTION_CURRENCY + " VARCHAR NOT NULL, " +
                COL_GOAL_TRANSACTION_DATE + " VARCHAR NOT NULL, " +
                COL_GOAL_TRANSACTION_TIME + " VARCHAR NOT NULL, " +
                COL_GOAL_TRANSACTION_MEMO + " VARCHAR, " +
                COL_GOAL_TRANSACTION_STATUS + " INTEGER NOT NULL, " +
                " CONSTRAINT fk_deposit_goal FOREIGN KEY ("+COL_GOAL_TRANSACTION_GOAL_ID+") REFERENCES "+TABLE_GOAl+"("+COL_GOAL_ID+") ON DELETE CASCADE);"
        db?.execSQL(sql)

        sql = "CREATE TABLE " + TABLE_DEBT + "(" +
                COL_DEBT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COl_DEBT_USER_ID + " INTEGER NOT NULL, " +
                COL_DEBT_TYPE + " INTEGER NOT NULL, " +
                COL_DEBT_NAME + " VARCHAR NOT NULL, " +
                COL_DEBT_DESCRIPTION + " VARCHAR NOT NULL, " +
                COL_DEBT_AMOUNT + " DOUBLE NOT NULL, " +
                COL_DEBT_CURRENCY + " VARCHAR NOT NULL, " +
                COL_DEBT_DATE + " VARCHAR NOT NULL, " +
                COL_DEBT_TIME + " VARCHAR NOT NULL, " +
                COL_DEBT_WALLET_ID + " INTEGER NOT NULL, " +
                COL_DEBT_COLOR + " INTEGER NOT NULL, " +
                COL_DEBT_STATUS + " INTEGER NOT NULL, " +
                " CONSTRAINT debt_ibfk_1 FOREIGN KEY (" + COl_DEBT_USER_ID + ") REFERENCES "+ TABLE_NAME + "(" + COL_ID + "), " +
                " CONSTRAINT debt_ibfk_2 FOREIGN KEY (" + COL_DEBT_WALLET_ID + ") REFERENCES " + TABLE_NAME4 + "(" + COL_Wallet_ID + ") ON DELETE CASCADE);"
        db?.execSQL(sql)

        // Create Request Table
        val requestTable = " CREATE TABLE " + TABLE_NAME8 + "(" +
                COL_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_REQUEST_USER_ID + " INT(10), " +
                COL_REQUEST_EMAIL_FROM + " VARCHAR(256), " +
                COL_REQUEST_EMAIL_TO + " VARCHAR(256), " +
                COL_REQUEST_Date + " VARCHAR(256), " +
                COL_REQUEST_ACCEPTED_DATE + " VARCHAR(256), " +
                COL_REQUEST_ACCEPT + " VARCHAR(256), " +
                COL_REQUEST_DISMISS + " VARCHAR(256), " +
                COL_REQUEST_LATER + " VARCHAR(256), " +
                " CONSTRAINT fk_Users FOREIGN KEY ("+COL_REQUEST_USER_ID+") REFERENCES "+TABLE_NAME+"("+COL_ID+") ON DELETE CASCADE);"
        db?.execSQL(requestTable)

        // Create Dismiss Table
        val dismissTable = " CREATE TABLE " + TABLE_NAME9 + "(" +
                COL_DISMISS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DISMISS_USER_ID + " INT(10), " +
                COL_DISMISS_EMAIL_FROM + " VARCHAR(256), " +
                COL_DISMISS_EMAIL_TO + " VARCHAR(256), " +
                COL_DISMISS_Date + " VARCHAR(256), " +
                " CONSTRAINT fk_Users FOREIGN KEY ("+COL_DISMISS_USER_ID+") REFERENCES "+TABLE_NAME+"("+COL_ID+") ON DELETE CASCADE);"
        db?.execSQL(dismissTable)

    }


//FOREIGN KEY ("+COL_Wallet_User_ID+") REFERENCES "+TABLE_NAME+"("+COL_ID+") ON DELETE CASCADE

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p1 != p2) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME3")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME4")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME5")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME6")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME7")
            var sql = "DROP TABLE IF EXISTS " + TABLE_GOAl
            db!!.execSQL(sql)
            sql = "DROP TABLE IF EXISTS " + TABLE_GOAL_TRANSACTION
            db!!.execSQL(sql)
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME9")

            onCreate(db)
        }
    }
    // ====================================================================================================== Users ================================================================================================================

    // ****************************************************************************************************** Insert User ***************************************************************************************************************
    fun insertData(user: User){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(Col_UserName, user.userName)
        cv.put(COL_PASSWORD, user.password)
        cv.put(COL_EMAIL, user.email)
        cv.put(COL_UserType, user.userType)
        cv.put(COL_Age, user.age)
        cv.put(COL_IMAGE, user.image)
        cv.put(COL_RELATION, user.relation)
        cv.put(COL_RELATION_EMAIL, user.relationEmail)

        var result = db.insert(TABLE_NAME, null, cv)
        if(result == -1.toLong()){
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        }
    }

    /*fun returnDbEmailValue(user: User ): String{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM Users WHERE email = '${user.email}'", null).toString()
    }
    fun returnDbPassValue(user: User ): Int{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM TABLE_NAME WHERE pass = '${user.password}'", null).count
    }*/

    // ****************************************************************************************************** Read User ***************************************************************************************************************

    fun readData(): MutableList<User>{
        var list: MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.userName = result.getString(result.getColumnIndex(Col_UserName)).toString()
                user.email = result.getString(result.getColumnIndex(COL_EMAIL)).toString()
                user.password = result.getString(result.getColumnIndex(COL_PASSWORD)).toString()
                user.userType = result.getString(result.getColumnIndex(COL_UserType)).toString()
                user.age = result.getString(result.getColumnIndex(COL_Age)).toInt()
                user.image = result.getString(result.getColumnIndex(COL_IMAGE)).toString()
                user.relation = result.getString(result.getColumnIndex(COL_RELATION)).toString()
                user.relationEmail = result.getString(result.getColumnIndex(COL_RELATION_EMAIL)).toString()

                list.add(user)

            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // ****************************************************************************************************** Update User ***************************************************************************************************************

    fun updateData(user: User){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_PASSWORD, user.password)
                db.update(TABLE_NAME, cv, "$COL_EMAIL= ?", arrayOf(user.email))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
    // ****************************************************************************************************** Update User ***************************************************************************************************************

    fun updateImageUser(user: User){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_IMAGE, user.image)
                db.update(TABLE_NAME, cv, "$COL_EMAIL= ?", arrayOf(user.email))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
    // ****************************************************************************************************** Update User ***************************************************************************************************************

    fun updateRelationUser(user: User){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_RELATION, user.relation)
                cv.put(COL_RELATION_EMAIL, user.relationEmail)

                db.update(TABLE_NAME, cv, "$COL_EMAIL= ?", arrayOf(user.email))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
    /*fun deleteData(){ // delete the data with ID = 1
        val db = this.writableDatabase

        db.delete(TABLE_NAME, COL_ID+"=?", arrayOf(1.toString()))
        db.close()
    }*/

    // ****************************************************************************************************** Delete User ***************************************************************************************************************

    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)

        db.close()
    }

    // ****************************************************************************************************** Insert Request ***************************************************************************************************************

    fun insertRequest(request: Requests, id: Int){
        var idOfUser: Int = id
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_REQUEST_USER_ID, id)
        contentValues.put(COL_REQUEST_EMAIL_FROM, request.emailFrom)
        contentValues.put(COL_REQUEST_EMAIL_TO, request.emailTo)
        contentValues.put(COL_REQUEST_Date, request.date)
        contentValues.put(COL_REQUEST_ACCEPTED_DATE, request.acceptDate)
        contentValues.put(COL_REQUEST_ACCEPT, request.accept)
        contentValues.put(COL_REQUEST_DISMISS, request.dismiss)
        contentValues.put(COL_REQUEST_LATER, request.later)
        contentValues.put(COL_REQUEST_DISMISS_DATE, request.dismissDate)

        Toast.makeText(context, "request:  $id", Toast.LENGTH_SHORT).show()

        val result = database.insert(TABLE_NAME8, null, contentValues)

        if (result == 0.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }

    // ****************************************************************************************************** Read Requests ***************************************************************************************************************

    fun readRequest(UID: Int): MutableList<Requests>{
        var list: MutableList<Requests> = ArrayList()
        var UserWID = UID
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query = "SELECT * FROM $TABLE_NAME INNER JOIN $TABLE_NAME8 ON $TABLE_NAME.$COL_ID = $TABLE_NAME8.$COL_REQUEST_USER_ID WHERE $TABLE_NAME8.$COL_REQUEST_USER_ID = $UID"

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var request = Requests()
                //Toast.makeText(context, "USER22:  $COL_Wallet_User_ID", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database1: $UID", Toast.LENGTH_SHORT).show()

                request.id = result.getString(result.getColumnIndex(COL_REQUEST_ID)).toInt()
                request.request_user_id = "$UID".toInt()
                request.emailFrom = result.getString(result.getColumnIndex(COL_REQUEST_EMAIL_FROM)).toString()
                request.emailTo = result.getString(result.getColumnIndex(COL_REQUEST_EMAIL_TO)).toString()
                request.date = result.getString(result.getColumnIndex(COL_REQUEST_Date)).toString()
                request.acceptDate = result.getString(result.getColumnIndex(COL_REQUEST_ACCEPTED_DATE)).toString()
                request.accept = result.getString(result.getColumnIndex(COL_REQUEST_ACCEPT)).toString()
                request.dismiss = result.getString(result.getColumnIndex(COL_REQUEST_DISMISS)).toString()
                request.later = result.getString(result.getColumnIndex(COL_REQUEST_LATER)).toString()
                request.dismissDate = result.getString(result.getColumnIndex(COL_REQUEST_DISMISS_DATE)).toString()

                Toast.makeText(context, "request_user_id from database: " + request.request_user_id, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()


                list.add(request)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // ****************************************************************************************************** Read Records Requests ***************************************************************************************************************

    fun readRequestsRecords(UID: Int):  ArrayList<Requests>{
        var list:  ArrayList<Requests> = ArrayList<Requests>()
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query = "SELECT * FROM $TABLE_NAME INNER JOIN $TABLE_NAME8 ON $TABLE_NAME.$COL_ID = $TABLE_NAME8.$COL_REQUEST_USER_ID WHERE $TABLE_NAME8.$COL_REQUEST_USER_ID = $UID "

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var request = Requests()
                Toast.makeText(context, "UID from database1: $UID", Toast.LENGTH_SHORT).show()

                request.id = result.getString(result.getColumnIndex(COL_REQUEST_ID)).toInt()
                request.request_user_id = "$UID".toInt()
                request.emailFrom = result.getString(result.getColumnIndex(COL_REQUEST_EMAIL_FROM)).toString()
                request.emailTo = result.getString(result.getColumnIndex(COL_REQUEST_EMAIL_TO)).toString()
                request.date = result.getString(result.getColumnIndex(COL_REQUEST_Date)).toString()
                request.acceptDate = result.getString(result.getColumnIndex(COL_REQUEST_ACCEPTED_DATE)).toString()
                request.accept = result.getString(result.getColumnIndex(COL_REQUEST_ACCEPT)).toString()
                request.dismiss = result.getString(result.getColumnIndex(COL_REQUEST_DISMISS)).toString()
                request.later = result.getString(result.getColumnIndex(COL_REQUEST_LATER)).toString()
                request.dismissDate = result.getString(result.getColumnIndex(COL_REQUEST_DISMISS_DATE)).toString()

                Toast.makeText(context, "request_user_id from database: " + request.request_user_id, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()


                list.add(request)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // ****************************************************************************************************** get Records count Request ***************************************************************************************************************

    fun getRecordsCountRequests(): Int{
        var countQuery = "SELECT * FROM $TABLE_NAME8"
        var db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor = db.rawQuery(countQuery, null)

        var count: Int = cursor.count
        cursor.close()
        return count
    }

    // ****************************************************************************************************** Update Request ***************************************************************************************************************

    fun updateRequest(request: Requests, uIDR: Int){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME8"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_REQUEST_ACCEPT, request.accept)
                cv.put(COL_REQUEST_DISMISS, request.dismiss)
                cv.put(COL_REQUEST_LATER, request.later)

                db.update(TABLE_NAME8, cv, "$COL_REQUEST_ID= $uIDR", null)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }

    fun deleteRequest(IDR: Int){ // delete the wallet with ID
        val db = this.writableDatabase

        db.delete(TABLE_NAME8, "$COL_REQUEST_ID=?", arrayOf(IDR.toString()))
        db.close()
    }

    // ****************************************************************************************************** Insert Dismiss Request ***************************************************************************************************************

    fun insertDismissReq(request: Requests, id: Int){
        var idOfUser: Int = id
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_DISMISS_USER_ID, id)
        contentValues.put(COL_DISMISS_EMAIL_FROM, request.emailFrom)
        contentValues.put(COL_DISMISS_EMAIL_TO, request.emailTo)
        contentValues.put(COL_DISMISS_Date, request.dismissDate)

        Toast.makeText(context, "request:  $id", Toast.LENGTH_SHORT).show()

        val result = database.insert(TABLE_NAME9, null, contentValues)

        if (result == 0.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }

    // ****************************************************************************************************** INSERT TRANSACTION ***************************************************************************************************************

    fun insertTransaction(transaction: TransactionTable,  id: Int) {
        var idOfUser: Int = id
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_Transaction_Type, transaction.type)
        contentValues.put(COL_Transaction_User_ID, id)
        contentValues.put(COL_Transaction_Category, transaction.category)
        contentValues.put(COL_Transaction_Amount, transaction.amount)
        contentValues.put(COL_Transaction_Note, transaction.note)
        contentValues.put(COL_Transaction_Date, transaction.date)
        // contentValues.put(COL_Transaction_wallet, transaction.wallet)
        //contentValues.put(COL_Transaction_SET_Event, transaction.event)
        //contentValues.put(COL_Transaction_Event_ID, transaction.event_id)
        //contentValues.put(COL_Transaction_SET_Remind, transaction.remind)
        //contentValues.put(COL_Transaction_Date_Remind, transaction.date_remind)
        //contentValues.put(COL_Transaction_Photos, transaction.photos)


        val result = database.insert(TABLE_NAME3, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    // ****************************************************************************************************** INSERT Main Income ***************************************************************************************************************

    fun insertMainIncome(MainIncome: MainIncome, id: Int) {
        var idOfUser: Int = id
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_MainIncome_User_ID, id)
        contentValues.put(COL_MainIncome_Income, MainIncome.income)
        contentValues.put(COL_MainIncome_Saving, MainIncome.saving)
        contentValues.put(COL_MainIncome_From_Date, MainIncome.first_date)
        contentValues.put(COL_MainIncome_To_Date, MainIncome.final_date)

        val result = database.insert(TABLE_NAME7, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    // ****************************************************************************************************** Read TRANSACTION ***************************************************************************************************************

    fun readTransaction(UID: Int): MutableList<TransactionTable>{
        var list: MutableList<TransactionTable> = ArrayList()
        var UserTID = UID
        val query = "SELECT * FROM $TABLE_NAME INNER JOIN $TABLE_NAME3 ON $TABLE_NAME.$COL_ID = $TABLE_NAME3.$COL_Transaction_User_ID WHERE $TABLE_NAME3.$COL_Transaction_User_ID = $UID"
        val db = this.readableDatabase
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var transaction = TransactionTable()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()

                transaction.id = result.getString(result.getColumnIndex(COL_Transaction_ID)).toInt()
                transaction.user_id = "$UID".toInt()
                transaction.type = result.getString(result.getColumnIndex(COL_Transaction_Type))
                transaction.category = result.getString(result.getColumnIndex(COL_Transaction_Category))
                transaction.amount = result.getString(result.getColumnIndex(COL_Transaction_Amount)).toInt()
                transaction.note = result.getString(result.getColumnIndex(COL_Transaction_Note))
                transaction.date = result.getString(result.getColumnIndex(COL_Transaction_Date))
                // transaction.wallet = result.getString(result.getColumnIndex(COL_Transaction_wallet))
                //transaction.photos = result.getString(result.getColumnIndex(COL_Transaction_Photos)).toString()
                Toast.makeText(context, "transaction_User_ID from database: " + transaction.user_id, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()

                list.add(transaction)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // ****************************************************************************************************** Read MainIncome ***************************************************************************************************************

    fun readMainIncome(UID: Int): MutableList<MainIncome>{
        var list: MutableList<MainIncome> = ArrayList()
        var UserTID = UID
        val query = "SELECT * FROM $TABLE_NAME INNER JOIN $TABLE_NAME7 ON $TABLE_NAME.$COL_ID = $TABLE_NAME7.$COL_Transaction_User_ID WHERE $TABLE_NAME7.$COL_Transaction_User_ID = $UID"
        val db = this.readableDatabase
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var main_income = MainIncome()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()

                main_income.id = result.getString(result.getColumnIndex(COL_MainIncome_ID)).toInt()
                main_income.user_id = "$UID".toInt()
                main_income.income = result.getString(result.getColumnIndex(COL_Transaction_Type)).toInt()
                main_income.saving = result.getString(result.getColumnIndex(COL_Transaction_Category)).toInt()
                main_income.first_date = result.getString(result.getColumnIndex(COL_Transaction_Amount))
                main_income.final_date = result.getString(result.getColumnIndex(COL_Transaction_Note))

                Toast.makeText(context, "main_income_User_ID from database: " + main_income.user_id, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()

                list.add(main_income)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // ****************************************************************************************************** Insert Wallet ***************************************************************************************************************

    fun insertWallet(wallet: Wallets, id: Int){
        var idOfUser: Int = id
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_Wallet_User_ID, id)
        contentValues.put(COL_Wallet_Name, wallet.wallet_name)
        contentValues.put(COL_Wallet_Type, wallet.wallet_type)
        contentValues.put(COL_Wallet_Currency, wallet.currency)
        contentValues.put(COL_Wallet_Balance, wallet.balance)
        contentValues.put(COL_Wallet_Include_Flag, wallet.include_flag)

        Toast.makeText(context, "USER2:  $id", Toast.LENGTH_SHORT).show()

        val result = database.insert(TABLE_NAME4, null, contentValues)

        if (result == 0.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }

    // ****************************************************************************************************** Read Wallet ***************************************************************************************************************

    fun readWallet(UID: Int): MutableList<Wallets>{
        var list: MutableList<Wallets> = ArrayList()
        var UserWID = UID
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query = "SELECT * FROM $TABLE_NAME INNER JOIN $TABLE_NAME4 ON $TABLE_NAME.$COL_ID = $TABLE_NAME4.$COL_Wallet_User_ID WHERE $TABLE_NAME4.$COL_Wallet_User_ID = $UID"

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var wallet = Wallets()
                //Toast.makeText(context, "USER22:  $COL_Wallet_User_ID", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database1: $UID", Toast.LENGTH_SHORT).show()

                wallet.id = result.getString(result.getColumnIndex(COL_Wallet_ID)).toInt()
                wallet.wallet_User_ID = "$UID".toInt()
                wallet.wallet_name = result.getString(result.getColumnIndex(COL_Wallet_Name)).toString()
                wallet.wallet_type = result.getString(result.getColumnIndex(COL_Wallet_Type))
                wallet.currency = result.getString(result.getColumnIndex(COL_Wallet_Currency)).toString()
                wallet.balance = result.getString(result.getColumnIndex(COL_Wallet_Balance)).toString()
                wallet.include_flag = result.getString(result.getColumnIndex(COL_Wallet_Include_Flag)).toBoolean()

                Toast.makeText(context, "wallet_User_ID from database: " + wallet.wallet_User_ID, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()


                list.add(wallet)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // ****************************************************************************************************** Update Wallet ***************************************************************************************************************

    fun updateWallet(wallet: Wallets, uIDW: Int){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME4"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_Wallet_Name, wallet.wallet_name)
                cv.put(COL_Wallet_Type, wallet.wallet_type)
                cv.put(COL_Wallet_Currency, wallet.currency)
                cv.put(COL_Wallet_Balance, wallet.balance)

                db.update(TABLE_NAME4, cv, "$COL_Wallet_ID= $uIDW", null)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
    fun deleteWallet(IDW: Int){ // delete the wallet with ID
        val db = this.writableDatabase

        db.delete(TABLE_NAME4, "$COL_Wallet_ID=?", arrayOf(IDW.toString()))
        db.close()
    }

    // ====================================================================================================== Sellers ================================================================================================================

    // ****************************************************************************************************** Insert seller ***************************************************************************************************************

    fun insertDataSeller(seller: Sellers){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_Seller_Name, seller.userName)
        cv.put(COL_Seller_PASSWORD, seller.password)
        cv.put(COL_Seller_EMAIL, seller.email)
        cv.put(COL_Seller_AGE, seller.age)
        cv.put(COL_Seller_Image, seller.image)

        var result = db.insert(TABLE_NAME5, null, cv)
        if(result == -1.toLong()){
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        }
    }

    // ****************************************************************************************************** read seller ***************************************************************************************************************

    fun readDataSeller(): MutableList<Sellers>{
        var list: MutableList<Sellers> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME5"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var seller = Sellers()
                seller.id = result.getString(result.getColumnIndex(COL_Seller_ID)).toInt()
                seller.userName = result.getString(result.getColumnIndex(COL_Seller_Name)).toString()
                seller.email = result.getString(result.getColumnIndex(COL_Seller_EMAIL)).toString()
                seller.password = result.getString(result.getColumnIndex(COL_Seller_PASSWORD)).toString()
                seller.age = result.getString(result.getColumnIndex(COL_Seller_AGE)).toInt()
                seller.image = result.getString(result.getColumnIndex(COL_Seller_Image)).toString()

                list.add(seller)

            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // ****************************************************************************************************** Update seller ***************************************************************************************************************

    fun updateDataSeller(seller: Sellers){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME5"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_Seller_PASSWORD, seller.password)
                db.update(TABLE_NAME5, cv, "$COL_Seller_EMAIL= ?", arrayOf(seller.email))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
    // ****************************************************************************************************** Update seller ***************************************************************************************************************

    fun updateImageSeller(seller: Sellers){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME5"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_Seller_Image, seller.image)
                db.update(TABLE_NAME5, cv, "$COL_Seller_EMAIL= ?", arrayOf(seller.email))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
    // ****************************************************************************************************** Update All seller ***************************************************************************************************************

    fun updateSeller(seller: Sellers){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME5"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_Seller_Name, seller.userName)
                cv.put(COL_Seller_PASSWORD, seller.password)
                cv.put(COL_Seller_EMAIL, seller.email)
                cv.put(COL_Seller_AGE, seller.age)
                cv.put(COL_Seller_Image, seller.image)

                db.update(TABLE_NAME5, cv, null, null)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }

    // ****************************************************************************************************** Insert Item ***************************************************************************************************************

    fun insertItem(item: Items, id: Int){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_Item_User_ID, id)
        cv.put(COL_Item_Name, item.item_name)
        cv.put(COL_Item_Amount, item.item_amount)
        cv.put(COL_Item_Price, item.item_price)
        cv.put(COL_Item_Description, item.item_desc)
        cv.put(COL_Item_Avatar, item.item_avatar)
        cv.put(COL_Item_Country, item.item_country)
        cv.put(COL_Item_Status, item.item_status)
        cv.put(COL_Item_Category, item.item_category)
        cv.put(COL_Item_Appropriate_Age, item.item_Appropriate_Age)
        cv.put(COL_Item_DateAdded, item.item_dateAdded)
        cv.put(COL_Item_UpdateDate, item.item_updateDate)

        var result = db.insert(TABLE_NAME6, null, cv)
        if(result == -1.toLong()){
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        }
    }

    // ****************************************************************************************************** Read Item ***************************************************************************************************************

    fun readItem(UID: Int): MutableList<Items>{
        var list: MutableList<Items> = ArrayList()
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query = "SELECT * FROM $TABLE_NAME5 INNER JOIN $TABLE_NAME6 ON $TABLE_NAME5.$COL_Seller_ID = $TABLE_NAME6.$COL_Item_User_ID WHERE $TABLE_NAME6.$COL_Item_User_ID = $UID"

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var item = Items()
                Toast.makeText(context, "UID from database1: $UID", Toast.LENGTH_SHORT).show()

                item.id = result.getString(result.getColumnIndex(COL_Item_ID)).toInt()
                item.item_User_ID = "$UID".toInt()
                item.item_name = result.getString(result.getColumnIndex(COL_Item_Name)).toString()
                item.item_amount = result.getString(result.getColumnIndex(COL_Item_Amount)).toInt()
                item.item_price = result.getString(result.getColumnIndex(COL_Item_Price)).toFloat()
                item.item_desc = result.getString(result.getColumnIndex(COL_Item_Description)).toString()
                item.item_avatar = result.getString(result.getColumnIndex(COL_Item_Avatar)).toString()
                item.item_country = result.getString(result.getColumnIndex(COL_Item_Country)).toString()
                item.item_status = result.getString(result.getColumnIndex(COL_Item_Status)).toString()
                item.item_category = result.getString(result.getColumnIndex(COL_Item_Category)).toString()
                item.item_Appropriate_Age = result.getString(result.getColumnIndex(COL_Item_Appropriate_Age)).toString()
                item.item_dateAdded = result.getString(result.getColumnIndex(COL_Item_DateAdded)).toString()
                item.item_updateDate = result.getString(result.getColumnIndex(COL_Item_UpdateDate)).toString()

                Toast.makeText(context, "wallet_User_ID from database: " + item.item_User_ID, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()


                list.add(item)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
    // ****************************************************************************************************** Read Item ***************************************************************************************************************

    fun readItemForUser(): MutableList<Items>{
        var list: MutableList<Items> = ArrayList()
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query = "SELECT * FROM $TABLE_NAME6 "

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var item = Items()

                item.id = result.getString(result.getColumnIndex(COL_Item_ID)).toInt()
                item.item_User_ID = result.getString(result.getColumnIndex(COL_Item_User_ID)).toInt()
                item.item_name = result.getString(result.getColumnIndex(COL_Item_Name)).toString()
                item.item_amount = result.getString(result.getColumnIndex(COL_Item_Amount)).toInt()
                item.item_price = result.getString(result.getColumnIndex(COL_Item_Price)).toFloat()
                item.item_desc = result.getString(result.getColumnIndex(COL_Item_Description)).toString()
                item.item_avatar = result.getString(result.getColumnIndex(COL_Item_Avatar)).toString()
                item.item_country = result.getString(result.getColumnIndex(COL_Item_Country)).toString()
                item.item_status = result.getString(result.getColumnIndex(COL_Item_Status)).toString()
                item.item_category = result.getString(result.getColumnIndex(COL_Item_Category)).toString()
                item.item_Appropriate_Age = result.getString(result.getColumnIndex(COL_Item_Appropriate_Age)).toString()
                item.item_dateAdded = result.getString(result.getColumnIndex(COL_Item_DateAdded)).toString()
                item.item_updateDate = result.getString(result.getColumnIndex(COL_Item_UpdateDate)).toString()

                Toast.makeText(context, "wallet_User_ID from database: " + item.item_User_ID, Toast.LENGTH_SHORT).show()


                list.add(item)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
    // ****************************************************************************************************** Update Item ***************************************************************************************************************

    fun updateItem(uIDIt: Int, item: Items){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME6"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(COL_Item_Name, item.item_name)
                cv.put(COL_Item_Amount, item.item_amount)
                cv.put(COL_Item_Price, item.item_price)
                cv.put(COL_Item_Description, item.item_desc)
                cv.put(COL_Item_Avatar, item.item_avatar)
                cv.put(COL_Item_Country, item.item_country)
                cv.put(COL_Item_Status, item.item_status)
                cv.put(COL_Item_Category, item.item_category)
                cv.put(COL_Item_Appropriate_Age, item.item_Appropriate_Age)
                cv.put(COL_Item_UpdateDate, item.item_updateDate)

                db.update(TABLE_NAME6, cv, "$COL_Item_ID = $uIDIt", null)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }
    fun deleteItem(IDIt: Int){ // delete the item with ID
        val db = this.writableDatabase

        db.delete(TABLE_NAME6, "$COL_Item_ID=?", arrayOf(IDIt.toString()))
        db.close()
    }
    fun deleteAllItems(){ // delete all items
        val db = this.writableDatabase
        db.delete(TABLE_NAME6, null, null)
        db.close()
    }
    // search
    fun searchItem(UID: Int, query1: String): ArrayList<Items>{
        var list: ArrayList<Items> = ArrayList<Items>()
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query =
            "SELECT * FROM $TABLE_NAME6 WHERE $TABLE_NAME6.$COL_Item_Name = ?"

        val result = db.rawQuery(query, arrayOf("$query1"))
        if (result.moveToFirst()) {
            do {
                var item = Items()
                Toast.makeText(context, "UID from database1: $UID", Toast.LENGTH_SHORT).show()

                item.id = result.getString(result.getColumnIndex(COL_Item_ID)).toInt()
                item.item_User_ID = "$UID".toInt()
                item.item_name = result.getString(result.getColumnIndex(COL_Item_Name)).toString()
                item.item_amount = result.getString(result.getColumnIndex(COL_Item_Amount)).toInt()
                item.item_price = result.getString(result.getColumnIndex(COL_Item_Price)).toFloat()
                item.item_desc = result.getString(result.getColumnIndex(COL_Item_Description)).toString()
                item.item_avatar = result.getString(result.getColumnIndex(COL_Item_Avatar)).toString()
                item.item_country = result.getString(result.getColumnIndex(COL_Item_Country)).toString()
                item.item_status = result.getString(result.getColumnIndex(COL_Item_Status)).toString()
                item.item_category = result.getString(result.getColumnIndex(COL_Item_Category)).toString()
                item.item_Appropriate_Age = result.getString(result.getColumnIndex(COL_Item_Appropriate_Age)).toString()
                item.item_dateAdded = result.getString(result.getColumnIndex(COL_Item_DateAdded)).toString()
                item.item_updateDate = result.getString(result.getColumnIndex(COL_Item_UpdateDate)).toString()

                Toast.makeText(context, "wallet_User_ID from database: " + item.item_User_ID, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()


                list.add(item)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
    fun searchItemWithAge(query1: String, Age: String): ArrayList<Items>{
        var list: ArrayList<Items> = ArrayList<Items>()
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query = "SELECT * FROM $TABLE_NAME6 WHERE $TABLE_NAME6.$COL_Item_Name = ? AND $TABLE_NAME6.$COL_Item_Appropriate_Age = ?"

        val result = db.rawQuery(query, arrayOf("$query1", "$Age"))
        if (result.moveToFirst()) {
            do {
                var item = Items()
                Toast.makeText(context, "age from database: " + item.item_Appropriate_Age, Toast.LENGTH_SHORT).show()

                item.id = result.getString(result.getColumnIndex(COL_Item_ID)).toInt()
                item.item_User_ID = result.getString(result.getColumnIndex(COL_Item_User_ID)).toInt()
                item.item_name = result.getString(result.getColumnIndex(COL_Item_Name)).toString()
                item.item_amount = result.getString(result.getColumnIndex(COL_Item_Amount)).toInt()
                item.item_price = result.getString(result.getColumnIndex(COL_Item_Price)).toFloat()
                item.item_desc = result.getString(result.getColumnIndex(COL_Item_Description)).toString()
                item.item_avatar = result.getString(result.getColumnIndex(COL_Item_Avatar)).toString()
                item.item_country = result.getString(result.getColumnIndex(COL_Item_Country)).toString()
                item.item_status = result.getString(result.getColumnIndex(COL_Item_Status)).toString()
                item.item_category = result.getString(result.getColumnIndex(COL_Item_Category)).toString()
                item.item_Appropriate_Age = "$Age"
                item.item_dateAdded = result.getString(result.getColumnIndex(COL_Item_DateAdded)).toString()
                item.item_updateDate = result.getString(result.getColumnIndex(COL_Item_UpdateDate)).toString()

                Toast.makeText(context, "wallet_User_ID from database: " + item.item_User_ID, Toast.LENGTH_SHORT).show()

                list.add(item)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
    /*fun getAllRecords(orderBy: String): ArrayList<AdapterRecordItems>{
        var recordList: ArrayList<AdapterRecordItems> = ArrayList<AdapterRecordItems>()
        var selectQuery: String = "SELECT * FROM $TABLE_NAME6 ORDER BY $orderBy"

        var db: SQLiteDatabase = this.writableDatabase
        var cursor: Cursor = db.rawQuery(selectQuery, null)

        // looping through all records and add to list
        if(cursor.moveToFirst()){
            do{
                var item: Items = Items("", "", "", "", "", "", "", "", "", "", "")
                )
            }
        }
    }*/
    //read records
    fun readItemRecords(UID: Int, orderBy: String):  ArrayList<Items>{
        var list:  ArrayList<Items> = ArrayList<Items>()
        val db = this.readableDatabase

        //val query = "SELECT * FROM $TABLE_NAME4 WHERE $COL_Wallet_User_ID = ?"
        val query = "SELECT * FROM $TABLE_NAME5 INNER JOIN $TABLE_NAME6 ON $TABLE_NAME5.$COL_Seller_ID = $TABLE_NAME6.$COL_Item_User_ID WHERE $TABLE_NAME6.$COL_Item_User_ID = $UID ORDER BY $orderBy "

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var item = Items()
                Toast.makeText(context, "UID from database1: $UID", Toast.LENGTH_SHORT).show()

                item.id = result.getString(result.getColumnIndex(COL_Item_ID)).toInt()
                item.item_User_ID = "$UID".toInt()
                item.item_name = result.getString(result.getColumnIndex(COL_Item_Name)).toString()
                item.item_amount = result.getString(result.getColumnIndex(COL_Item_Amount)).toInt()
                item.item_price = result.getString(result.getColumnIndex(COL_Item_Price)).toFloat()
                item.item_desc = result.getString(result.getColumnIndex(COL_Item_Description)).toString()
                item.item_avatar = result.getString(result.getColumnIndex(COL_Item_Avatar)).toString()
                item.item_country = result.getString(result.getColumnIndex(COL_Item_Country)).toString()
                item.item_status = result.getString(result.getColumnIndex(COL_Item_Status)).toString()
                item.item_category = result.getString(result.getColumnIndex(COL_Item_Category)).toString()
                item.item_Appropriate_Age = result.getString(result.getColumnIndex(COL_Item_Appropriate_Age)).toString()
                item.item_dateAdded = result.getString(result.getColumnIndex(COL_Item_DateAdded)).toString()
                item.item_updateDate = result.getString(result.getColumnIndex(COL_Item_UpdateDate)).toString()

                Toast.makeText(context, "wallet_User_ID from database: " + item.item_User_ID, Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "UID from database: $UID", Toast.LENGTH_SHORT).show()


                list.add(item)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    // get Records count
    fun getRecordsCount(): Int{
        var countQuery = "SELECT * FROM $TABLE_NAME6"
        var db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor = db.rawQuery(countQuery, null)

        var count: Int = cursor.count
        cursor.close()
        return count
    }


    // = = = = = = = = = = = = = = = Add Goal = = = = = = = = = = = = = = = =

    public fun saveGoal(
        id: Int, user: Int, wallet: Int, name: String?, amount: Double, currency: String?, targetDate: String?,
        color: Int, achieve: Int, starred: Int, status: Int
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COl_GOAL_USER_ID, user)
        contentValues.put(COl_GOAL_WALLET_ID, wallet)
        contentValues.put(COL_GOAL_NAME, name)
        contentValues.put(COL_GOAL_AMOUNT, amount)
        contentValues.put(COL_GOAL_CURRENCY, currency)
        contentValues.put(COL_GOAL_TARGET_DATE, targetDate)
        contentValues.put(COL_GOAL_COLOR, color)
        contentValues.put(COL_GOAL_ACHIEVE, achieve)
        contentValues.put(COL_GOAL_STARRED, starred)
        contentValues.put(COL_GOAL_STATUS, status)

        if(id < 0){
            db.insert(TABLE_GOAl, null, contentValues)
        } else {
            db.update(TABLE_GOAl, contentValues, "$COL_GOAL_ID= $id", null)
        }

        db.close()
        return true
    }

    /*
    * This method taking two arguments
    * first one is the id of the goal for which
    * we have to update the sync status
    * and the second one is the status that will be changed
    * */
    fun updateGoalStatus(id: Int, status: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_GOAL_STATUS, status)
        db.update(TABLE_GOAl, contentValues, COL_GOAL_ID + "=" + id, null)
        db.close()
        return true
    }

    /*
    * this method will give us all the goal stored in sqlite
    * */
    fun getGoals(user: Int): Cursor? {
        val db = this.readableDatabase
        val sql = "SELECT * FROM " + TABLE_GOAl  + " WHERE " + COl_DEBT_USER_ID + " = " + user + ";"
        return db.rawQuery(sql, null)
    }

    /*
    * this method is for getting all the unsynced goal
    * so that we can sync it with database
    * */
    fun getUnsyncedGoals(): Cursor? {
        val db = this.readableDatabase
        val sql = "SELECT * FROM " + TABLE_GOAl + " WHERE " + COL_GOAL_STATUS + " = 0;"
        return db.rawQuery(sql, null)
    }

    // = = = = = = = = = = = = = = = GOAL TRANSACTION = = = = = = = = = = = = = = = =
    public fun saveGoalTransaction(
        id: Int, goal: Int, type: Int, amount: Double, currency: String, date: String, time: String, memo: String?, status: Int
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_GOAL_TRANSACTION_GOAL_ID, goal)
        contentValues.put(COL_GOAL_TRANSACTION_TYPE, type)
        contentValues.put(COL_GOAL_TRANSACTION_AMOUNT, amount)
        contentValues.put(COL_GOAL_TRANSACTION_CURRENCY, currency)
        contentValues.put(COL_GOAL_TRANSACTION_DATE, date)
        contentValues.put(COL_GOAL_TRANSACTION_TIME, time)
        contentValues.put(COL_GOAL_TRANSACTION_MEMO, memo)
        contentValues.put(COL_GOAL_TRANSACTION_STATUS, status)

        if(id < 0){
            db.insert(TABLE_GOAL_TRANSACTION, null, contentValues)
        } else {
            db.update(TABLE_GOAL_TRANSACTION, contentValues, "$COL_GOAL_TRANSACTION_ID= $id", null)
        }
        db.close()
        return true
    }

    fun getGoalTransactionGroupByDate(goalId: Int): Cursor? {
        val db = this.readableDatabase
        val sql = "SELECT "+ COL_GOAL_TRANSACTION_DATE +" FROM " + TABLE_GOAL_TRANSACTION + " WHERE " + COL_GOAL_TRANSACTION_GOAL_ID + " = " + goalId + " GROUP BY " + COL_GOAL_TRANSACTION_DATE + ";"
        return db.rawQuery(sql, null)
    }

    fun getGoalTransaction(goalId: Int): Cursor? {
        val db = this.readableDatabase
        val sql = "SELECT * FROM " + TABLE_GOAL_TRANSACTION + " WHERE " + COL_GOAL_TRANSACTION_GOAL_ID + " = " + goalId + ";"
        Log.d("Main", "Amount = " )
        return db.rawQuery(sql, null)
    }

    fun updateGoalIsStarred(id: Int, star: Int, status: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_GOAL_STARRED, star)
        contentValues.put(COL_GOAL_STATUS, status)
        db.update(TABLE_GOAl, contentValues, COL_GOAL_ID + "=" + id, null)
        db.close()
        return true
    }

    // = = = = = = = = = = = = = = = Add Debt = = = = = = = = = = = = = = = =
    public fun saveDebt(
        id: Int, user: Int, wallet: Int, type: Int, name: String?, description: String?, amount: Double, currency: String?, date: String?,
        time: String?, color: Int, status: Int
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COl_DEBT_USER_ID, user)
        contentValues.put(COL_DEBT_WALLET_ID, wallet)
        contentValues.put(COL_DEBT_TYPE, type)
        contentValues.put(COL_DEBT_NAME, name)
        contentValues.put(COL_DEBT_DESCRIPTION, description)
        contentValues.put(COL_DEBT_AMOUNT, amount)
        contentValues.put(COL_DEBT_CURRENCY, currency)
        contentValues.put(COL_DEBT_DATE, date)
        contentValues.put(COL_DEBT_TIME, time)
        contentValues.put(COL_DEBT_COLOR, color)
        contentValues.put(COL_DEBT_STATUS, status)

        if(id < 0){
            db.insert(TABLE_DEBT, null, contentValues)
        } else {
            db.update(TABLE_DEBT, contentValues, "$COL_DEBT_ID= $id", null)
        }

        db.close()
        return true
    }

    fun getDebts(user: Int): Cursor? {
        val db = this.readableDatabase
        val sql = "SELECT * FROM " + TABLE_DEBT + " WHERE " + COl_DEBT_USER_ID + " = " + user + ";"
        return db.rawQuery(sql, null)
    }

}


