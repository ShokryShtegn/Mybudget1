package com.example.mybudget

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.blogspot.atifsoftwares.circularimageview.CircularImageView
import com.google.android.material.navigation.NavigationView
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.content_item.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AddItem : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar2: Toolbar
    lateinit var drawerLayout2: DrawerLayout
    lateinit var navView2: NavigationView
    var context: Context = this
    var categoryArray= arrayOf<String?>(
        "Clothes",
        "Colours",
        "Comic",
        "book",
        "Drinks",
        "Electronic",
        "Films",
        "Food",
        "Animals",
        "Birds",
        "Books",
        "Buildings",
        "Cars",
        "Furniture",
        "Hotels",
        "Jobs",
        "Liquids",
        "Shapes",
        "Toys",
        "Tools"
    )
    var ageGroup = arrayOf<String?>("0-5", "6-15", "16-22", "23-30", "31-50", "more than 50")
    var result: String = ""
    var result2: String = ""
    var id: String = ""
    var idInt: Int = 0
    var nameIt: String = ""
    var amountIt: String = ""
    var amountItInt: Int = 0
    var priceIt: String = ""
    var priceItFloat: Float = 0.0f
    var descIt: String = ""
    lateinit var imageIt: Uri
    var countryIt: String = ""
    var statusIt: String = ""
    var categoryIt: String = ""
    var appropriateAgeIt: String = ""
    var positionVal: Int = 0
    var positionValAge: Int = 0

    var isEditMode: Boolean = false
    private final var GALLERY_REQUEST_CODE: Int = 123;
    private final var CAMERA_REQUEST_CODE: Int = 100;
    private final var STORAGE_REQUEST_CODE: Int = 101;
    private final var IMAGE_PICK_CAMERA_CODE: Int = 102;
    private final var IMAGE_PICK_GALLERY_CODE: Int = 103;


    //private final var GALLERY_REQUEST_CODE1: Int = 123;
    private final var CAMERA_REQUEST_CODE1: Int = 104;
    private final var STORAGE_REQUEST_CODE1: Int = 105;
    private final var IMAGE_PICK_CAMERA_CODE1: Int = 106;
    private final var IMAGE_PICK_GALLERY_CODE1: Int = 107;
    // arrays of permissions
    var cameraPermissions = arrayOf<String?>() // camera and storage
    var storagePermissions = arrayOf<String?>() // only storage
    var storagePermissions1 = arrayOf<String?>() // only storage

    lateinit var imagePerson: CircularImageView
    lateinit var namePerson: TextView
    var UserEmailLog: String = ""

    lateinit var imageView: ImageView
    lateinit var btnPhoto: Button
    final var request_code: Int = 1
    val db = DataBaseHandler(context)
    var indexCategory: Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_items)
        toolbar2 = findViewById<Toolbar>(R.id.toolbar2) as Toolbar
        setSupportActionBar(toolbar2)

        drawerLayout2 = findViewById<DrawerLayout>(R.id.drawer_layout2) as DrawerLayout
        navView2 = findViewById<NavigationView>(R.id.nav_view_item) as NavigationView

        val toggle = ActionBarDrawerToggle(this, drawerLayout2, toolbar2, 0, 0)
        drawerLayout2.addDrawerListener(toggle)
        toggle.syncState()
        navView2.setNavigationItemSelectedListener(this)

        category.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            categoryArray
        )

        category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                positionVal = position
                result = category.selectedItem.toString()
                Toast.makeText(context, "Category is: " + result, Toast.LENGTH_SHORT).show()
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }
        agePersons.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            ageGroup
        )

        agePersons.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                positionValAge = position
                result2 = agePersons.selectedItem.toString()
                Toast.makeText(context, "Age is: " + result2, Toast.LENGTH_SHORT).show()
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }

        imageView = findViewById<ImageView>(R.id.selectImage) as ImageView
        btnPhoto = findViewById<Button>(R.id.photo) as Button

        // get data from intent
        var intent2: Intent = getIntent()
        isEditMode = intent2.getBooleanExtra("isEditMode", false)

        if(isEditMode){
            // update data
            toolbar2.setTitle("Update data")

            id = intent2.getStringExtra("ID").toString()
            idInt = id.toInt()
            nameIt = intent2.getStringExtra("NAME").toString()
            amountIt = intent2.getStringExtra("AMOUNT").toString()
            amountItInt = amountIt.toInt()
            priceIt = intent2.getStringExtra("PRICE").toString()
            priceItFloat = priceIt.toFloat()
            descIt = intent2.getStringExtra("DESC").toString()
            imageIt = Uri.parse(intent2.getStringExtra("IMAGE").toString())
            countryIt = intent2.getStringExtra("COUNTRY").toString()
            statusIt = intent2.getStringExtra("STATUS").toString()
            categoryIt = intent2.getStringExtra("CATEGORY").toString()
            appropriateAgeIt = intent2.getStringExtra("APPROPRIATEAGE").toString()


            // set data to views
            itemName.setText(nameIt)
            descOfItem.setText(descIt)
            availableAmount.setText(amountIt)
            price.setText(priceIt)
            country.setText(countryIt)
            status.setText(statusIt)
            for(j in 0 ..(categoryArray.size - 1)){
                if(categoryArray[j].equals(categoryIt)){
                    indexCategory = j
                    category.setSelection(j)
                }
            }
            for(j in 0 ..(ageGroup.size - 1)){
                if(ageGroup[j].equals(appropriateAgeIt)){
                    agePersons.setSelection(j)
                }
            }
            // if no image was selected while adding data, imageUri value will be null
            if(imageIt.toString().equals("null")){
                // no image, set default
                imageView.setImageResource(R.drawable.ic_item_black2)
            }else{
                // have image, set
                imageView.setImageURI(imageIt)
            }

        }else{
            // add data
            toolbar2.setTitle("Add data")
        }
        cameraPermissions = arrayOf<String?>(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermissions = arrayOf<String?>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        UserEmailLog = GlobalVariable.emailSeller
        Toast.makeText(context, "Email in seller home light: " + UserEmailLog, Toast.LENGTH_SHORT).show()
        storagePermissions1 = arrayOf<String?>(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        val hView: View = navView2.getHeaderView(0)
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
                if(data3[j].image.equals("null")){
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
            imagePickDialog1()
        })
        btnPhoto.setOnClickListener({
            //var intent = Intent()
            //intent.setType("image/*")
            //intent.setAction(Intent.ACTION_GET_CONTENT)
            //startActivityForResult(Intent.createChooser(intent, "Pick an image"), GALLERY_REQUEST_CODE)
            imagePickDialog();
        })

        saveItem.setOnClickListener({
            if (isEditMode) {
                // update data
                val now = LocalDateTime.now()
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                if (!GlobalVariable.imageData.toString().equals("")) {
                    var item = Items(
                        itemName.text.toString(),
                        availableAmount.text.toString().toInt(),
                        price.text.toString().toFloat(),
                        descOfItem.text.toString(),
                        GlobalVariable.imageData.toString(),
                        country.text.toString(),
                        status.text.toString(),
                        result,
                        result2,
                        formatter.format(now),
                        formatter.format(now)
                    )
                    db.updateItem(idInt, item)
                    Toast.makeText(this, "Updated...", Toast.LENGTH_SHORT).show()
                } else {
                    var item = Items(
                        itemName.text.toString(),
                        availableAmount.text.toString().toInt(),
                        price.text.toString().toFloat(),
                        descOfItem.text.toString(),
                        imageIt.toString(),
                        country.text.toString(),
                        status.text.toString(),
                        result,
                        result2,
                        formatter.format(now),
                        formatter.format(now)
                    )
                    db.updateItem(idInt, item)
                    Toast.makeText(this, "Updated...", Toast.LENGTH_SHORT).show()
                }

            } else {
                // new data
                val now = LocalDateTime.now()
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                var data2 = db.readItem(GlobalVariable.idUserInSeller)
                if (data2.size == 0)
                    if ((itemName.text.toString().isNotEmpty()) &&
                        (descOfItem.text.toString().isNotEmpty()) &&
                        (availableAmount.text.toString().isNotEmpty()) &&
                        (price.text.toString().isNotEmpty()) && country.text.toString()
                            .isNotEmpty() && category.toString().isNotEmpty()
                    ) {
                        var item = Items(
                            itemName.text.toString(),
                            availableAmount.text.toString().toInt(),
                            price.text.toString().toFloat(),
                            descOfItem.text.toString(),
                            GlobalVariable.imageData.toString(),
                            country.text.toString(),
                            status.text.toString(),
                            result,
                            result2,
                            formatter.format(now),
                            formatter.format(now)
                        )
                        db.insertItem(item, GlobalVariable.idUserInSeller)
                        Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
                        var data3 = db.readItem(GlobalVariable.idUserInSeller)
                        Toast.makeText(
                            context,
                            "Hello From if: " + data3.get(0).item_User_ID,
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(
                            context, "ID: " + data3.get(0).id.toString() + " name: " + data3.get(
                                0
                            ).item_name + " amount: " + data3.get(0).item_amount, Toast.LENGTH_SHORT
                        ).show()
                    }
                for (i in 0..(data2.size - 1)) {
                    if ((itemName.text.toString().isNotEmpty()) &&
                        (descOfItem.text.toString().isNotEmpty()) &&
                        (availableAmount.text.toString().isNotEmpty()) &&
                        (price.text.toString().isNotEmpty()) &&
                        country.text.toString().isNotEmpty() &&
                        category.toString().isNotEmpty()) {
                        if(!itemName.text.toString().equals(data2[i].item_name)) {
                            var item = Items(
                                itemName.text.toString(),
                                availableAmount.text.toString().toInt(),
                                price.text.toString().toFloat(),
                                descOfItem.text.toString(),
                                GlobalVariable.imageData.toString(),
                                country.text.toString(),
                                status.text.toString(),
                                result,
                                result2,
                                formatter.format(now),
                                formatter.format(now)
                            )
                            db.insertItem(item, GlobalVariable.idUserInSeller)
                            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
                        }
                        var data3 = db.readItem(GlobalVariable.idUserInSeller)
                        Toast.makeText(context, "Hello From if: " + data3.get(i).item_User_ID, Toast.LENGTH_SHORT).show()
                        Toast.makeText(context, "ID: " + data3.get(i).id.toString() + " name: " + data3.get(i).item_name + " amount: " + data3.get(i).item_amount, Toast.LENGTH_SHORT).show()
                        break
                    } else if ((itemName.text.toString().length < 0) ||
                        (descOfItem.text.toString().length < 0) ||
                        (availableAmount.text.toString().length < 0) || (country.text.toString().length < 0) || (!result.equals(
                            ""
                        ))
                    ) {
                        Toast.makeText(this, "Please fill All Data's", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
        drawerLayout2.closeDrawer(GravityCompat.START)
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
                    GlobalVariable.imageData = data.data
                    imageView.setImageURI(GlobalVariable.imageData)
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

                imageView.setImageURI(GlobalVariable.imageData)
                //var photo: Bitmap? = data?.getExtras()?.get("data") as Bitmap?
                copyFileOrDirectory("" + (GlobalVariable.imageData), "" + getDir("MyBudgetImages",
                        MODE_PRIVATE
                    )
                )

            } else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                // cropped image received
                var resultCrop: CropImage.ActivityResult = CropImage.getActivityResult(data)
                if(resultCode == RESULT_OK){
                    var resultUri: Uri = resultCrop.uri
                    GlobalVariable.imageData = resultUri
                    // set Image
                    imageView.setImageURI(resultUri)
                    copyFileOrDirectory(
                        "" + (GlobalVariable.imageData), "" + getDir(
                            "MyBudgetImages",
                            MODE_PRIVATE
                        )
                    )
                }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //error
                    var error: Exception = resultCrop.error
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
                }
            }
            if(requestCode == IMAGE_PICK_GALLERY_CODE1){
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
                    copyFileOrDirectory2(
                        "" + (data.data), "" + getDir(
                            "MyBudgetImages",
                            MODE_PRIVATE
                        )
                    )
                }
            }else if(requestCode == IMAGE_PICK_CAMERA_CODE1){
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
                copyFileOrDirectory2("" + (GlobalVariable.imageSeller), "" + getDir("MyBudgetImages", MODE_PRIVATE))

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
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
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
        GlobalVariable.imageData = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        var cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, GlobalVariable.imageData)
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
            GlobalVariable.imageData = Uri.parse(desDir.path) // uri of saved image
            Log.d("ImagePath", "copyFile: " + GlobalVariable.imageData)
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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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
            CAMERA_REQUEST_CODE1 -> {
                if (grantResults.size > 0) {
                    // if allowed return true otherwise return false
                    var cameraAccepted: Boolean =
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                    var storageAccepted: Boolean =
                        grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted) {
                        // both permission allowed
                        pickFromCamera1()
                    } else {
                        Toast.makeText(
                            this,
                            "Camera & Storage permissions are required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            STORAGE_REQUEST_CODE1 -> {
                if (grantResults.size > 0) {
                    // if allowed return true otherwise return false
                    var storageAccepted: Boolean =
                        grantResults[0] == PackageManager.PERMISSION_GRANTED

                    if (storageAccepted) {
                        // permission allowed
                        pickFromGallery1()
                    } else {
                        Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
    /*fun insertImg(id: Int, img: Bitmap?) {
        val data: ByteArray = getBitmapAsByteArray(img) // this is a function
        insertStatement_logo.bindLong(1, id)
        insertStatement_logo.bindBlob(2, data)
        insertStatement_logo.executeInsert()
        insertStatement_logo.clearBindings()
    }*/
   /* private fun createDirectoryAndSaveFile(imageToSave: Bitmap, fileName: String) {
        val direct = File(Environment.getExternalStorageDirectory().toString() + "/DirName")
        if (!direct.exists()) {
            val wallpaperDirectory = File("/sdcard/DirName/")
            wallpaperDirectory.mkdirs()
        }
        val file = File("/sdcard/DirName/", fileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
    fun getBitmap(path: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val f = File(path)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
            imageView.setImageBitmap(bitmap)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return bitmap
    }*/
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

    fun imagePickDialog1(){
        // options to display in dialog
        var options = arrayOf<String?>("Camera", "Gallery")
        // dialog
        var builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //title
        builder.setTitle("Pick Image From")
        // set Items/options

        builder.setItems(options, { _, which ->
            val selected = options[which]
            if (which == 0) {
                // camera clicked
                if (!checkCameraPermission2()) {
                    requestCameraPermission2()
                } else {
                    // permission already granted
                    pickFromCamera1()
                }
            } else if (which == 1) {
                if (!checkStoragePermission2()) {
                    requestStoragePermission2()
                } else {
                    pickFromGallery1()
                }
            }

        })
        // create/show dialog
        builder.create().show()
    }
    fun pickFromCamera1(){
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
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE1)
    }
    fun pickFromGallery1(){
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
            startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE1)
        } else {
            intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE1)
        }
    }
    fun checkStoragePermission2(): Boolean{
        // check if storage permission is enabled or not

        var resultPer = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        return resultPer
    }
    fun requestStoragePermission2(){
        // request the storage permission
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE1)
    }
    fun checkCameraPermission2(): Boolean{
        // check if Camera permission is enabled or not

        var resultPer = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED)
        var resultPer1 = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        return resultPer && resultPer1
    }
    fun requestCameraPermission2(){
        // request the camera permissions
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE1)
    }
    fun copyFileOrDirectory2(srcDir: String, desDir: String){
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
                    copyFileOrDirectory2(src1, dst1)
                }
            }else{
                copyFile2(src, des)
            }
        }catch (e: Exception){
            Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
            Log.d("Error_Image: ", "" + e.message)
        }
    }
    //@Throws(IOException::class)
    fun copyFile2(srcDir: File, desDir: File){

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