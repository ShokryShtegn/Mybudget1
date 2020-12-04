package com.example.mybudget

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.SyncStateContract
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.blogspot.atifsoftwares.circularimageview.CircularImageView
import com.google.android.material.navigation.NavigationView
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import kotlin.properties.Delegates

class RecordDetailActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var recordID: String
    private var recordIDInt by Delegates.notNull<Int>()
    var context: Context = this
    lateinit var db: DataBaseHandler
    lateinit var profileIv1: ImageView
    lateinit var bioTv1: TextView
    lateinit var name1: TextView
    lateinit var amount1: TextView
    lateinit var desc1: TextView
    lateinit var price1: TextView
    lateinit var country1: TextView
    lateinit var status1: TextView
    lateinit var category1: TextView
    lateinit var appropriateAge1: TextView
    lateinit var dateAdded1: TextView
    lateinit var updateDate1: TextView
    var storagePermissions1 = arrayOf<String?>() // only storage
    private final var STORAGE_REQUEST_CODE1: Int = 101;
    lateinit var toolbar4: Toolbar
    lateinit var drawerLayout4: DrawerLayout
    lateinit var navView4: NavigationView
    private final var GALLERY_REQUEST_CODE: Int = 123
    private final var CAMERA_REQUEST_CODE: Int = 100
    private final var STORAGE_REQUEST_CODE: Int = 101
    private final var IMAGE_PICK_CAMERA_CODE: Int = 102
    private final var IMAGE_PICK_GALLERY_CODE: Int = 103
    lateinit var imagePerson: CircularImageView
    lateinit var namePerson: TextView
    var UserEmailLog: String = ""
    var cameraPermissions = arrayOf<String?>() // camera and storage
    var storagePermissions = arrayOf<String?>() // only storage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_item)
        toolbar4 = findViewById<Toolbar>(R.id.toolbar4) as Toolbar
        setSupportActionBar(toolbar4)

        drawerLayout4 = findViewById<DrawerLayout>(R.id.drawer_layout4) as DrawerLayout
        navView4 = findViewById<NavigationView>(R.id.nav_view_item_details) as NavigationView

        val toggle = ActionBarDrawerToggle(this, drawerLayout4, toolbar4, 0, 0)
        drawerLayout4.addDrawerListener(toggle)
        toggle.syncState()
        navView4.setNavigationItemSelectedListener(this)
        db = DataBaseHandler(context)
        // get record id from adapter
        var intent: Intent = getIntent()

        recordID = intent.getStringExtra("Record_id").toString()
        recordIDInt = recordID.toInt()

        profileIv1 = findViewById<ImageView>(R.id.profileIv) as ImageView
        //bioTv1 = findViewById<TextView>(R.id.bio) as TextView
        name1 = findViewById<TextView>(R.id.Name) as TextView
        amount1 = findViewById<TextView>(R.id.amount) as TextView
        desc1 = findViewById<TextView>(R.id.description) as TextView
        price1 = findViewById<TextView>(R.id.price) as TextView
        country1 = findViewById<TextView>(R.id.country) as TextView
        status1 = findViewById<TextView>(R.id.status) as TextView
        category1 = findViewById<TextView>(R.id.category) as TextView
        appropriateAge1 = findViewById<TextView>(R.id.appropriateAge) as TextView
        dateAdded1 = findViewById<TextView>(R.id.dateAdded) as TextView
        updateDate1 = findViewById<TextView>(R.id.dateUpdate) as TextView

        cameraPermissions = arrayOf<String?>(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermissions = arrayOf<String?>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermissions1 = arrayOf<String?>(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        UserEmailLog = GlobalVariable.emailSeller
        Toast.makeText(context, "Email in seller home light: " + UserEmailLog, Toast.LENGTH_SHORT).show()

        showRecordDetails()

        val hView: View = navView4.getHeaderView(0)
        namePerson = hView.findViewById<TextView>(R.id.personName) as TextView
        imagePerson = hView.findViewById<CircularImageView>(R.id.addPhoto) as CircularImageView
        Toast.makeText(this, "hello1", Toast.LENGTH_SHORT).show()
        var data3 = db.readDataSeller()
        Toast.makeText(this, "Size: "+data3.size, Toast.LENGTH_SHORT).show()
        for(j in 0..(data3.size-1)) {
            Toast.makeText(this, "email1: "+data3[j].email.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "email2: " + UserEmailLog, Toast.LENGTH_SHORT).show()

            if((data3.get(j).email.equals(UserEmailLog, false))){
                Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()

                namePerson.setText(data3[j].userName)
                /*if(data3[j].image.equals(null)){
                    imagePerson.setImageResource(R.drawable.ic_add_photo_white)
                }else{
                    imagePerson.setImageURI(Uri.parse(data3[j].image))
                }*/
                if(data3[j].image.equals("")){
                    // no image record
                    imagePerson.setImageResource(R.drawable.ic_add_photo_white)
                }else{
                    // have image in record
                    if (!checkStoragePermission1()) {
                        requestStoragePermission1()
                    }else {
                        imagePerson.setImageURI(Uri.parse(data3[j].image))
                    }
                }
                break
            }
        }
        imagePerson.setOnClickListener({
            imagePickDialog()
        })
    }
    fun showRecordDetails(){
        // get Record details

        // query to select record based on record id
        var selectQuery: String = "SELECT * FROM $TABLE_NAME6 WHERE $COL_Item_ID = $recordIDInt"
        var db1: SQLiteDatabase = db.writableDatabase
        var cursor: Cursor = db1.rawQuery(selectQuery, null)

        // keep checking in whole db for that record
        if(cursor.moveToFirst()){
            do{
                var id: Int = cursor.getInt(cursor.getColumnIndex(COL_Item_ID)).toInt()
                var name: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_Name))
                var amount: Int = cursor.getString(cursor.getColumnIndex(COL_Item_Amount)).toInt()
                var price: Float = cursor.getString(cursor.getColumnIndex(COL_Item_Price)).toFloat()
                var desc: String = cursor.getString(cursor.getColumnIndex(COL_Item_Description))
                var image: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_Avatar))
                var country: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_Country))
                var status: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_Status))
                var category: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_Category))
                var appropriateAge: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_Appropriate_Age))
                var dateAdded: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_DateAdded))
                var updateDate: String = ""+ cursor.getString(cursor.getColumnIndex(COL_Item_UpdateDate))

                name1.text = name
                amount1.text = amount.toString()
                price1.text = price.toString()
                desc1.text = desc
                country1.text = country
                status1.text = status
                category1.text = category
                appropriateAge1.text = appropriateAge
                dateAdded1.text = dateAdded
                updateDate1.text = updateDate

                // if user doesn't attach image then imageUri will be null, so set a default image in that case
                if(image.equals("")){
                    // no image record
                    profileIv1.setImageResource(R.drawable.ic_item_black2)
                }else{
                    // have image in record
                    if (!checkStoragePermission1()) {
                        requestStoragePermission1()
                    }else{
                        profileIv1.setImageURI(Uri.parse(image))
                    }
                }

            }while(cursor.moveToNext())
        }
        // close db connection
        db1.close()
    }
    fun checkStoragePermission1(): Boolean{
        // check if storage permission is enabled or not

        var resultPer = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        return resultPer
    }
    fun requestStoragePermission1(){
        // request the storage permission
        ActivityCompat.requestPermissions(this, storagePermissions1, STORAGE_REQUEST_CODE1)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_Dashboard -> {
                Toast.makeText(this, "Dashboard clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SellerHomeLight::class.java)
                startActivity(intent)
            }
            R.id.nav_profile -> {
                val intent = Intent(this, Profile::class.java)
                intent.putExtra("sellerID", GlobalVariable.idUserInSeller)
                startActivity(intent)
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_messages -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_new_item -> {
                val intent = Intent(this, AddItem::class.java)
                intent.putExtra("sellerID", GlobalVariable.idUserInSeller)
                startActivity(intent)
                Toast.makeText(this, "add Item clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_update -> {
                val intent = Intent(this, Update::class.java)
                intent.putExtra("sellerID", GlobalVariable.idUserInSeller)
                startActivity(intent)
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, Main::class.java)
                startActivity(intent)
            }
        }
        drawerLayout4.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // image picked from camera or gallery will be received here
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK ){
            // image is picked
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                // picked from gallery

                //crop image
                if (data != null) {
                    Toast.makeText(
                        this,
                        "helllllllllllllllllllllllllllllllllllll",
                        Toast.LENGTH_SHORT
                    ).show()
                    /*CropImage.activity(data.data).setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this)*/
                    GlobalVariable.imageSeller = data.data
                    imagePerson.setImageURI(GlobalVariable.imageSeller)
                    var seller = Sellers(GlobalVariable.sellerName, GlobalVariable.sellerPass, GlobalVariable.emailSeller, GlobalVariable.sellerAge, GlobalVariable.imageSeller.toString())
                    db.updateImageSeller(seller)
                    /*getBitmap(GlobalVariable.imageData.toString())?.let {
                        createDirectoryAndSaveFile(
                            it, "MyBudgetImages")
                    }*/
                    copyFileOrDirectory(
                        "" + (data.data), "" + getDir(
                            "MyBudgetImages",
                            MODE_PRIVATE
                        )
                    )
                }
            }else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                // picked from camera

                //crop image
                /*CropImage.activity(GlobalVariable.imageData).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this)*/
                //val path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

                imagePerson.setImageURI(GlobalVariable.imageSeller)
                var seller = Sellers(GlobalVariable.sellerName, GlobalVariable.sellerPass, GlobalVariable.emailSeller, GlobalVariable.sellerAge, GlobalVariable.imageSeller.toString())
                db.updateImageSeller(seller)
                //var photo: Bitmap? = data?.getExtras()?.get("data") as Bitmap?
                copyFileOrDirectory("" + (GlobalVariable.imageSeller), "" + getDir("MyBudgetImages", MODE_PRIVATE))

            } else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                // cropped image received
                var resultCrop: CropImage.ActivityResult = CropImage.getActivityResult(data)
                if(resultCode == RESULT_OK){
                    var resultUri: Uri = resultCrop.uri
                    GlobalVariable.imageSeller = resultUri
                    // set Image
                    imagePerson.setImageURI(resultUri)
                    copyFileOrDirectory(
                        "" + (GlobalVariable.imageSeller), "" + getDir(
                            "MyBudgetImages",
                            MODE_PRIVATE
                        )
                    )
                    var seller = Sellers(GlobalVariable.sellerName, GlobalVariable.sellerPass, GlobalVariable.emailSeller, GlobalVariable.sellerAge, GlobalVariable.imageSeller.toString())
                    db.updateImageSeller(seller)
                }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //error
                    var error: Exception = resultCrop.error
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
                }
            }
            //GlobalVariable.imageData = data.data
            //imageView.setImageURI(GlobalVariable.imageData)
            // var image: Image = imageView.getIm
        }
    }
    fun imagePickDialog(){
        // options to display in dialog
        var options = arrayOf<String?>("Camera", "Gallery")
        // dialog
        var builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(
            this
        )
        //title
        builder.setTitle("Pick Image From")
        // set Items/options

        builder.setItems(options, { _, which ->
            val selected = options[which]
            if (which == 0) {
                // camera clicked
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    // permission already granted
                    pickFromCamera()
                }
            } else if (which == 1) {
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    pickFromGallery()
                }
            }

        })
        // create/show dialog
        builder.create().show()
    }
    fun pickFromCamera(){
        // insert to pick image from camera, the image will be returned in onActivityResult

        var values: ContentValues = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Image title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description")

        //put image uri
        GlobalVariable.imageSeller = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        var cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, GlobalVariable.imageSeller)
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE)
    }
    fun pickFromGallery(){
        // insert to pick image from gallery, the image will be returned in onActivityResult
        //var galleryIntent: Intent = Intent(Intent.ACTION_PICK)
        //galleryIntent.setType("image/*")
        //galleryIntent.setAction(Intent.ACTION_GET_CONTENT)
        //startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE)

        val intent: Intent

        if (Build.VERSION.SDK_INT < 19) {
            intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "*/*"
            startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE)
        } else {
            intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE)
        }
    }
    fun checkStoragePermission(): Boolean{
        // check if storage permission is enabled or not

        var resultPer = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        return resultPer
    }
    fun requestStoragePermission(){
        // request the storage permission
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE)
    }
    fun checkCameraPermission(): Boolean{
        // check if Camera permission is enabled or not

        var resultPer = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED)
        var resultPer1 = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        return resultPer && resultPer1
    }
    fun requestCameraPermission(){
        // request the camera permissions
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // result of permission allowed/denied
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    // if allowed return true otherwise return false
                    var cameraAccepted: Boolean =
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                    var storageAccepted: Boolean =
                        grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted) {
                        // both permission allowed
                        pickFromCamera()
                    } else {
                        Toast.makeText(
                            this,
                            "Camera & Storage permissions are required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    // if allowed return true otherwise return false
                    var storageAccepted: Boolean =
                        grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (storageAccepted) {
                        // permission allowed
                        pickFromGallery()
                    } else {
                        Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
    fun copyFileOrDirectory(srcDir: String, desDir: String){
        // create folder in specified directory
        try{
            var src: File = File(srcDir)
            var des: File = File(desDir, src.name)
            if(src.isDirectory){
                var files = src.list()
                var filesLength: Int = files.size
                for(file in files){
                    var src1: String = File(src, file).path
                    var dst1: String = des.path
                    copyFileOrDirectory(src1, dst1)
                }
            }else{
                copyFile(src, des)
            }
        }catch (e: Exception){
            Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
            Log.d("Error_Image: ", "" + e.message)
        }
    }
    //@Throws(IOException::class)
    fun copyFile(srcDir: File, desDir: File){

        if(!desDir.parentFile.exists()){
            desDir.mkdirs() // create if not exist
        }
        if(!desDir.exists()){
            desDir.createNewFile()
        }
        var source : FileChannel? = null
        var destination: FileChannel? = null

        try{
            source = FileInputStream(srcDir).channel
            destination = FileOutputStream(desDir).channel
            destination.transferFrom(source, 0, source.size())
            GlobalVariable.imageSeller = Uri.parse(desDir.path) // uri of saved image
            Log.d("ImagePath", "copyFile: " + GlobalVariable.imageSeller)
        }catch (e: Exception){
            // if there is any error saving image
            Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
            Log.d("Error_Image2: ", "" + e.message)
        }finally {
            // close resources
            if(source != null){
                source.close()
            }
            if(destination != null){
                destination.close()
            }
        }
    }

}