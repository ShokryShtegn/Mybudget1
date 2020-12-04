package com.example.mybudget

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.toColor
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.sign_dialog_child.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mIsShowPass = false
    private var mIsShowPass2 = false
    private var radioValue: String = ""
    var parentAge: Int = 0
    var childName: String = ""
    var parName: String = ""
    var namePCh: String = ""
    var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_sign_up, container, false)
        var context: Context = (getActivity()?.getApplicationContext() ?: context) as Context

        var sign = SignUp()
        val db = DataBaseHandler(context)
        var signBtn = view.findViewById<Button>(R.id.btn_insert)
        //var readBtn = view.findViewById<Button>(R.id.btn_read)
        //var updateBtn = view.findViewById<Button>(R.id.btn_update)
        //var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        var showPass = view.findViewById<AppCompatImageView>(R.id.ivShowHidePass)
        var showPass2 = view.findViewById<AppCompatImageView>(R.id.ivShowHidePass2)
        var groupRadio = view.findViewById<RadioGroup>(R.id.radioGroup)
        showPass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
        showPass2.setOnClickListener {
            mIsShowPass2 = !mIsShowPass2
            showPassword2(mIsShowPass2)
        }
        groupRadio.setOnCheckedChangeListener({ group, checkedId ->
            if(checkedId == R.id.user){
                Toast.makeText(context, user.text.toString(), Toast.LENGTH_SHORT).show()
                radioValue = user.text.toString()
                Toast.makeText(context, "The value of radio is: " + radioValue, Toast.LENGTH_SHORT).show()
                GlobalVariable.signPopUp_parent = 1

                signBtn.setOnClickListener {
                    var data2 = db.readData()
                    if (data2.size == 0)
                        if ((UserName.text.toString().isNotEmpty()) &&
                            (age.text.toString().isNotEmpty()) &&
                            (emailField.text.toString().isNotEmpty()) &&
                            (passField.text.toString().length > 4) &&
                            (emailField.text.toString().contains(char = '@', false)) &&
                            verifPass.text.toString().equals(passField.text.toString(), false) && !radioValue.equals("")) {
                            var user = User(
                                UserName.text.toString(),
                                passField.text.toString(),
                                emailField.text.toString(),
                                radioValue,
                                age.text.toString().toInt(),
                                "",
                                "",
                                ""
                            )
                            db.insertData(user)
                            userEmail = emailField.text.toString()
                            GlobalVariable.userEmailSign = userEmail
                            relationOptionDialog()
                            Log.d("signBtn", "Selected")

                        }
                    for (i in 0..(data2.size - 1)) {
                        if ((UserName.text.toString().length > 0) &&
                            (age.text.toString().isNotEmpty()) &&
                            (emailField.text.toString().length > 0) &&
                            (passField.text.toString().length > 4) &&
                            (emailField.text.toString().contains(char = '@', false)) && ((!data2.get(i).email.equals(emailField.text.toString(), false))) &&
                            verifPass.text.toString().equals(passField.text.toString(), false) && (!data2.get(i).userName.toString().equals(UserName.text.toString(), false)) &&
                            !radioValue.equals("")) {

                            var user = User(
                                UserName.text.toString(),
                                passField.text.toString(),
                                emailField.text.toString(),
                                radioValue,
                                age.text.toString().toInt(),
                                "",
                                "",
                                ""
                            )
                            db.insertData(user)
                            userEmail = emailField.text.toString()
                            GlobalVariable.userEmailSign = userEmail
                            relationOptionDialog()
                            Log.d("ID: ", userEmail.toString())
                            Log.d("signBtn", "Selected")
                            break
                        } else if ((UserName.text.toString().length < 0) ||
                            (emailField.text.toString().length < 0) ||
                            (passField.text.toString().length < 0) || (age.text.toString().length < 0)) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "Please fill All Data's", Snackbar.LENGTH_LONG).show()
                            }
                        } else if (!emailField.text.toString().contains('@', false)) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "E_mail must have '@'.", Snackbar.LENGTH_LONG).show()
                            }
                        } else if ((data2.get(i).email.toString().equals(emailField.text.toString(), false))) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "E_mail is already exist.", Snackbar.LENGTH_LONG).show()
                            }
                        } else if (!verifPass.text.toString().equals(passField.text.toString(), false)) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "Password not matched.", Snackbar.LENGTH_LONG).show()
                            }
                        } else if (data2.get(i).userName.toString().equals(UserName.text.toString(), false)) {
                            view?.let { it1 ->
                                Snackbar.make(it1, "Username is already exist.", Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }else if (radioValue.equals("")) {
                            view?.let { it1 ->
                                Snackbar.make(it1, "please, choose the type pf your account.", Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }else if(checkedId == R.id.seller){
                Toast.makeText(context, seller.text.toString(), Toast.LENGTH_SHORT).show()
                radioValue = seller.text.toString()
                Toast.makeText(context, "The value of radio is: " + radioValue, Toast.LENGTH_SHORT).show()
                GlobalVariable.signPopUp_seller = 1
                signBtn.setOnClickListener {
                    var data3 = db.readDataSeller()
                    if (data3.size == 0)
                        if ((UserName.text.toString().isNotEmpty()) &&
                            (age.text.toString().isNotEmpty()) &&
                            (emailField.text.toString().isNotEmpty()) &&
                            (passField.text.toString().length > 4) &&
                            (emailField.text.toString().contains(char = '@', false)) &&
                            verifPass.text.toString().equals(passField.text.toString(), false) && !radioValue.equals("")) {
                            var seller = Sellers(
                                UserName.text.toString(),
                                passField.text.toString(),
                                emailField.text.toString(),
                                age.text.toString().toInt(),
                                ""
                            )
                            db.insertDataSeller(seller)
                            var userEmail = emailField.text.toString()
                            Log.d("signBtn", "Selected")
                            GlobalVariable.emailSeller = userEmail
                            val intent = Intent(getActivity(), SellerHomeLight::class.java)
                            intent.putExtra("User_Email", userEmail)
                            getActivity()?.startActivity(intent)
                        }
                    for (i in 0..(data3.size - 1)) {
                        if ((UserName.text.toString().length > 0) &&
                            (age.text.toString().isNotEmpty()) &&
                            (emailField.text.toString().length > 0) &&
                            (passField.text.toString().length > 4) &&
                            (emailField.text.toString().contains(char = '@', false)) && ((!data3.get(i).email.equals(emailField.text.toString(), false))) &&
                            verifPass.text.toString().equals(passField.text.toString(), false) && (!data3.get(i).userName.toString().equals(UserName.text.toString(), false)) &&
                            !radioValue.equals("")) {

                            var seller = Sellers(
                                UserName.text.toString(),
                                passField.text.toString(),
                                emailField.text.toString(),
                                age.text.toString().toInt(),
                                ""
                            )
                            db.insertDataSeller(seller)
                            var userEmail = emailField.text.toString()
                            Log.d("ID: ", userEmail.toString())
                            Log.d("signBtn", "Selected")
                            GlobalVariable.emailSeller = userEmail
                            val intent = Intent(getActivity(), SellerHomeLight::class.java)
                            intent.putExtra("User_Email", userEmail)
                            getActivity()?.startActivity(intent)
                            break
                        } else if ((UserName.text.toString().length < 0) ||
                            (emailField.text.toString().length < 0) ||
                            (passField.text.toString().length < 0) || (age.text.toString().length < 0)) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "Please fill All Data's", Snackbar.LENGTH_LONG).show()
                            }
                        } else if (!emailField.text.toString().contains('@', false)) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "E_mail must have '@'.", Snackbar.LENGTH_LONG).show()
                            }
                        } else if ((data3.get(i).email.toString().equals(emailField.text.toString(), false))) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "E_mail is already exist.", Snackbar.LENGTH_LONG).show()
                            }
                        } else if (!verifPass.text.toString().equals(passField.text.toString(), false)) {
                            view?.let {
                                    it1 -> Snackbar.make(it1, "Password not matched.", Snackbar.LENGTH_LONG).show()
                            }
                        } else if (data3.get(i).userName.toString().equals(UserName.text.toString(), false)) {
                            view?.let { it1 ->
                                Snackbar.make(it1, "Username is already exist.", Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }else if (radioValue.equals("")) {
                            view?.let { it1 ->
                                Snackbar.make(it1, "please, choose the type pf your account.", Snackbar.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }
            }
        })
        /*readBtn.setOnClickListener({
            var data = db.readData()
            tvResult.text = ""
            for (i in 0..(data.size - 1)) {
                tvResult.append(
                    data.get(i).id.toString() + " " + data.get(i).userName.toString() + " " + data.get(
                        i
                    ).email.toString() + " " + data.get(i).password.toString() + "\n"
                )
                Log.d("btn_read", "Selected")
            }
        })
        updateBtn.setOnClickListener({
            db.updateData()
            btn_read.performClick()
            Log.d("btn_update", "Selected")
        })
        deleteBtn.setOnClickListener({
            db.deleteData()
            btn_read.performClick()
            Log.d("btn_delete", "Selected")
        })*/
        // Inflate the layout for this fragment
        return view
    }
    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            // To show the password
            passField.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ivShowHidePass.setImageResource(R.drawable.ic_hide_password_24)
        } else {
            // To hide the password
            passField.transformationMethod = PasswordTransformationMethod.getInstance()
            ivShowHidePass.setImageResource(R.drawable.ic_show_password_eye_24)
        }
        // This line of code to put the pointer at the end of the password string
        passField.setSelection(passField.text.toString().length)
    }
    private fun showPassword2(isShow: Boolean) {
        if (isShow) {
            // To show the password
            verifPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ivShowHidePass2.setImageResource(R.drawable.ic_hide2_password_24)
        } else {
            // To hide the password
            verifPass.transformationMethod = PasswordTransformationMethod.getInstance()
            ivShowHidePass2.setImageResource(R.drawable.ic_show2_password_24)
        }
        // This line of code to put the pointer at the end of the password string
        verifPass.setSelection(verifPass.text.toString().length)
    }
    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        val intent = Intent(getActivity(), Relations::class.java)
        intent.putExtra("User_Email", userEmail)
        getActivity()?.startActivity(intent)
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        val intent = Intent(getActivity(), homeLight::class.java)
        intent.putExtra("User_Email", userEmail)
        getActivity()?.startActivity(intent)
    }
    private fun relationOptionDialog(){
        // options to display in dialog
       /* var options = arrayOf<String?>("Yes", "No")
        // dialog
        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(" You can do relation between your account and your child or wife account, do you want to do that? ").setItems(options, { _, which ->
            // handle option click
            if (which == 0) {
                // Yes
                val intent = Intent(getActivity(), Relations::class.java)
                intent.putExtra("User_Email", userEmail)
                getActivity()?.startActivity(intent)
            } else if (which == 1) {
                // No
                val intent = Intent(getActivity(), homeLight::class.java)
                intent.putExtra("User_Email", userEmail)
                getActivity()?.startActivity(intent)
            }
        }).create().show() // show dialog*/

        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setTitle("Relation")
            setMessage(" You can do relation between your account and your child or wife account, do you want to do that? ")
            setPositiveButton("Yes", DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int ->
                val intent = Intent(getActivity(), Relations::class.java)
                intent.putExtra("User_Email", userEmail)
                getActivity()?.startActivity(intent)
            }))
            setNegativeButton(android.R.string.no, { dialog: DialogInterface, which: Int ->
                // Create an explicit intent for an Activity in your app

                val intent = Intent(getActivity(), homeLight::class.java)
                intent.putExtra("User_Email", userEmail)
                getActivity()?.startActivity(intent)
            })
            show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}