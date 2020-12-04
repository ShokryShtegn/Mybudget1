package com.example.mybudget

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.choose_relations.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Relations: AppCompatActivity() {
    var resultRadio: String = ""
    var db = DataBaseHandler(this)
    var context: Context = this
    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext, android.R.string.yes, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, homeLight::class.java)
        intent.putExtra("User_Email",GlobalVariable.userEmailSign)
        startActivity(intent)
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Maybe", Toast.LENGTH_SHORT).show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_relations)

        radioRelations.setOnCheckedChangeListener({ group, checkedId ->
            if(checkedId == R.id.Father){
                resultRadio = "Father"
                Toast.makeText(this, "Father", Toast.LENGTH_SHORT).show()
            }
            if(checkedId == R.id.Mother){
                resultRadio = "Mother"
                Toast.makeText(this, "Mother", Toast.LENGTH_SHORT).show()
            }
            if(checkedId == R.id.child){
                resultRadio = "Child"
                Toast.makeText(this, "child", Toast.LENGTH_SHORT).show()
            }
            if(checkedId == R.id.wife){
                resultRadio = "Wife"
                Toast.makeText(this, "wife", Toast.LENGTH_SHORT).show()
            }
            if(checkedId == R.id.husband){
                resultRadio = "Husband"
                Toast.makeText(this, "husband", Toast.LENGTH_SHORT).show()
            }

        })
        radioRelations2.setOnCheckedChangeListener({ group, checkedId2 ->
            if(checkedId2 == R.id.sister){
                resultRadio = "Sister"
                Toast.makeText(this, "sister", Toast.LENGTH_SHORT).show()
            }
            if(checkedId2 == R.id.brother){
                resultRadio = "Brother"
                Toast.makeText(this, "brother", Toast.LENGTH_SHORT).show()
            }

        })
        saveRelation.setOnClickListener({
            var dataUpdateUserRelation = db.readData()
            for(i in 0 ..(dataUpdateUserRelation.size - 1)){
                if(GlobalVariable.userEmailSign.equals(dataUpdateUserRelation[i].email, false) && (emailRelation.text.toString().length > 0) && !resultRadio.equals("")){
                    var dataReadUser = db.readData()
                    for(j in 0 ..(dataReadUser.size - 1)){
                        if(dataReadUser[j].email.equals(emailRelation.text.toString(), false) && (!emailRelation.text.toString().equals(dataUpdateUserRelation[i].email))){
                            GlobalVariable.userIdRelationFrom = dataUpdateUserRelation[i].id
                            GlobalVariable.userIdRelationTo = dataReadUser[j].id
                            GlobalVariable.userNameRelation = dataUpdateUserRelation[i].userName
                            GlobalVariable.userEmailToRelation = emailRelation.text.toString()
                            GlobalVariable.userEmailRelation = dataUpdateUserRelation[i].email

                            var user = User(dataUpdateUserRelation[i].userName, dataUpdateUserRelation[i].password, dataUpdateUserRelation[i].email, dataUpdateUserRelation[i].userType, dataUpdateUserRelation[i].age, dataUpdateUserRelation[i].image, resultRadio, emailRelation.text.toString())
                            db.updateRelationUser(user)

                            val now = LocalDateTime.now()
                            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            var insertRequest = Requests(dataUpdateUserRelation[i].email, dataReadUser[j].email, formatter.format(now), "", "", "", "", "")
                            db.insertRequest(insertRequest, dataReadUser[j].id)

                            relationOptionDialog()
                            /*val intent = Intent(this, homeLight::class.java)
                            intent.putExtra("User_Email",GlobalVariable.userEmailSign)
                            startActivity(intent)*/
                        }else if(emailRelation.text.toString().equals(dataUpdateUserRelation[i].email)){
                            Toast.makeText(this, "You can't do relation with your self", Toast.LENGTH_SHORT).show()
                        }else if(dataReadUser[j].email.equals(emailRelation.text.toString())){
                            Toast.makeText(this, "This email doesn't exist", Toast.LENGTH_SHORT).show()
                        } else if(resultRadio.equals("")){
                            Toast.makeText(this, "Please choose one of choices", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
        cancel.setOnClickListener({
            val intent = Intent(this, homeLight::class.java)
            intent.putExtra("User_Email",GlobalVariable.userEmailSign)
            startActivity(intent)
        })
    }
    private fun relationOptionDialog(){
        val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Request")
            setMessage("The request send to user.")
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            GlobalVariable.requestNot = true
            //setNegativeButton(android.R.string.no, negativeButtonClick)
            show()
        }
    }

}