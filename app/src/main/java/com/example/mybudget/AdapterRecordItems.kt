package com.example.mybudget

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull

class AdapterRecordItems(var context: Context, var recordList: ArrayList<Items>): RecyclerView.Adapter<AdapterRecordItems.HolderRecord>() {
    private var cont: Context = context
    private var recordList1: ArrayList<Items> = recordList
    var storagePermissions = arrayOf<String?>() // only storage
    private final var STORAGE_REQUEST_CODE: Int = 101;
    var db = DataBaseHandler(context)
    class HolderRecord(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemName = itemView.findViewById<TextView>(R.id.itemName) as TextView
        //var itemDesc = itemView.findViewById<TextView>(R.id.itemDesc)  as TextView
        //var itemCountry = itemView.findViewById<TextView>(R.id.itemCountry) as TextView
        //var itemStatus = itemView.findViewById<TextView>(R.id.itemStatus) as TextView
        //var itemCategory = itemView.findViewById<TextView>(R.id.itemCategory) as TextView
        var itemImage = itemView.findViewById<ImageView>(R.id.profileIv) as ImageView
        var itemAmount = itemView.findViewById<TextView>(R.id.itemAmount) as TextView
        var itemPrice = itemView.findViewById<TextView>(R.id.itemPrice) as TextView
        var moreBtn = itemView.findViewById<ImageButton>(R.id.moreBtn) as ImageButton
        //var AppropriateAge = itemView.findViewById<TextView>(R.id.appropriateAge) as TextView
        //var dateAdded = itemView.findViewById<TextView>(R.id.dateAdded) as TextView
        var updateDate = itemView.findViewById<TextView>(R.id.dateUpdate) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRecordItems.HolderRecord {
        // inflate layout
        var view: View = LayoutInflater.from(context).inflate(R.layout.items_record, parent, false)

        return HolderRecord(view)
    }

    override fun getItemCount(): Int {
        return recordList.size // return size of list/number of records
    }

    override fun onBindViewHolder(holder: HolderRecord, position: Int) {
        // get data, set data, handle view clicks in this method


        Toast.makeText(context, "position: $position", Toast.LENGTH_SHORT).show()
        // get data
        var item: Items = recordList[position]
        var id: Int = item.getId()
        var name: String = item.getItemName()
        var amount: Int = item.getItemAmount()
        var price: Float = item.getItemPrice()
        var desc: String = item.getItemDesc()
        var image: String = item.getItemAvatar()
        var country: String = item.getItemCountry()
        var status: String = item.getItemStatus()
        var category: String = item.getItemCategory()
        var appropriateAge: String = item.getItemAppropriateAge()
        var dateAdded: String = item.getDateAdded()
        var updateDate: String = item.getUpdateDate()

        GlobalVariable.itemId = id.toString()
        // set data to views
        holder.itemName.setText(name)
        holder.itemAmount.setText(amount.toString())
        holder.itemPrice.setText(price.toString())
        //holder.itemCategory.setText(category)
        //holder.itemDesc.setText(desc)
        //holder.itemCountry.setText(country)
        //holder.itemStatus.setText(status)
        //holder.AppropriateAge.setText(appropriateAge)
        //holder.dateAdded.setText(dateAdded)
        holder.updateDate.setText(updateDate)

        storagePermissions = arrayOf<String?>(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        // if user doesn't attach image then imageUri will be null, so set a default image in that case
        if(image.equals("")){
            // no image record
            holder.itemImage.setImageResource(R.drawable.ic_item_black2)
        }else{
            // have image in record
            if (!checkStoragePermission()) {
                requestStoragePermission()
            }else {
                holder.itemImage.setImageURI(Uri.parse(image))
            }
        }

        // handle item clicks
        holder.itemView.setOnClickListener({
            // pass record id to next activity to show details of that record
            val intent: Intent = Intent(context, RecordDetailActivity::class.java)
            intent.putExtra("Record_id", id.toString())
            context.startActivity(intent)
        })

        // handle more button click listener (show options like edit, delete, etc..)
        holder.moreBtn.setOnClickListener({
            // show options menu
            showMoreDialog(
                position,
                id,
                "" + name,
                amount,
                price,
                "" + desc,
                "" + image,
                "" + country,
                "" + status,
                "" + category,
                "" + appropriateAge)
        })
        Log.d("ImagePath", "onBindViewHolder: "+  image)

    }
    private fun showMoreDialog(position: Int, id: Int, name: String, amount: Int, price: Float, desc: String, image: String, country: String, status: String, category: String, appropriateAge: String){
        var options = arrayOf<String?>("Edit", "Delete")
        // dialog
        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        // add items to dialog
        builder.setItems(options, { _, which ->
            // handle item clicks
            if(which == 0){
                // Edit is clicked

                // start AddUpdateUpdateRecordActivity to update existing record
                var intent: Intent = Intent(context, AddItem::class.java)
                intent.putExtra("ID", id.toString())
                intent.putExtra("NAME", name)
                intent.putExtra("AMOUNT", amount.toString())
                intent.putExtra("PRICE", price.toString())
                intent.putExtra("DESC", desc)
                intent.putExtra("IMAGE", image)
                intent.putExtra("COUNTRY", country)
                intent.putExtra("STATUS", status)
                intent.putExtra("CATEGORY", category)
                intent.putExtra("APPROPRIATEAGE", appropriateAge)
                intent.putExtra("isEditMode", true)
                context.startActivity(intent)
            } else if(which == 1){
                // delete is clicked
                db.deleteItem(id)
                //refresh record by calling avtivitie's onResume method
                var s = context as SellerHomeLight
                s.onResume()
            }
        })
        // show dialog
        builder.create().show()

    }
    fun checkStoragePermission(): Boolean{
        // check if storage permission is enabled or not

        var resultPer = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
        return resultPer
    }
    fun requestStoragePermission(){
        // request the storage permission
        ActivityCompat.requestPermissions(context as Activity, storagePermissions, STORAGE_REQUEST_CODE)
    }
}