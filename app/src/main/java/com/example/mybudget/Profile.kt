package com.example.mybudget

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.blogspot.atifsoftwares.circularimageview.CircularImageView
import com.google.android.material.navigation.NavigationView
import com.theartofdev.edmodo.cropper.CropImage
import org.w3c.dom.Text
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel

class Profile: AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar3: Toolbar
    lateinit var drawerLayout3: DrawerLayout
    lateinit var navView3: NavigationView
    var context: Context = this
    var storagePermissions1 = arrayOf<String?>() // only storage
    private final var GALLERY_REQUEST_CODE: Int = 123
    private final var CAMERA_REQUEST_CODE: Int = 100
    private final var STORAGE_REQUEST_CODE: Int = 101
    private final var IMAGE_PICK_CAMERA_CODE: Int = 102
    private final var IMAGE_PICK_GALLERY_CODE: Int = 103
    lateinit var imagePerson: CircularImageView
    lateinit var namePerson: TextView
    var UserEmailLog: String = ""
    val db = DataBaseHandler(context)
    var cameraPermissions = arrayOf<String?>() // camera and storage
    var storagePermissions = arrayOf<String?>() // only storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_seller)
        toolbar3 = findViewById<Toolbar>(R.id.toolbar3) as Toolbar
        setSupportActionBar(toolbar3)

        drawerLayout3 = findViewById<DrawerLayout>(R.id.drawer_layout3) as DrawerLayout
        navView3 = findViewById<NavigationView>(R.id.nav_view_seller) as NavigationView

        val toggle = ActionBarDrawerToggle(this, drawerLayout3, toolbar3, 0, 0)
        drawerLayout3.addDrawerListener(toggle)
        toggle.syncState()
        navView3.setNavigationItemSelectedListener(this)
        var imagePath = findViewById<ImageView>(R.id.sellerIv)
        var nameSeller = findViewById<TextView>(R.id.Name) as TextView
        var emailSeller = findViewById<TextView>(R.id.email) as TextView
        var passSeller = findViewById<TextView>(R.id.password) as TextView
        var ageSeller = findViewById<TextView>(R.id.age) as TextView

        cameraPermissions = arrayOf<String?>(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermissions = arrayOf<String?>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermissions1 = arrayOf<String?>(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        UserEmailLog = GlobalVariable.emailSeller
        Toast.makeText(context, "Email in seller home light: " + UserEmailLog, Toast.LENGTH_SHORT).show()

        var dataSellerProfile = db.readDataSeller()
        for(i in 0 ..(dataSellerProfile.size - 1)){
            if(dataSellerProfile[i].id == GlobalVariable.idUserInSeller){
                if(dataSellerProfile[i].image.equals("null")){
                    // no image record
                    imagePath.setImageResource(R.drawable.ic_profile_photo)
                }else{
                    // have image in record
                    if (!checkStoragePermission1()) {
                        requestStoragePermission1()
                    }else {
                        imagePath.setImageURI(Uri.parse(dataSellerProfile[i].image))
                    }
                }
                //imagePath.setImageURI(Uri.parse(dataSellerProfile[i].image))
                nameSeller.setText(dataSellerProfile[i].userName)
                emailSeller.setText(dataSellerProfile[i].email)
                passSeller.setText(dataSellerProfile[i].password)
                ageSeller.setText(dataSellerProfile[i].age.toString())
            }
        }
        val hView: View = navView3.getHeaderView(0)
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

                namePerson.setText(GlobalVariable.sellerName)
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
        drawerLayout3.closeDrawer(GravityCompat.START)
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

    fun checkStoragePermission1(): Boolean{
        // check if storage permission is enabled or not

        var resultPer = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        return resultPer
    }
    fun requestStoragePermission1(){
        // request the storage permission
        ActivityCompat.requestPermissions(context as Activity, storagePermissions1, STORAGE_REQUEST_CODE)
    }
}