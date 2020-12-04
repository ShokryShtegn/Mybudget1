package com.example.mybudget.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget.Global
import com.example.mybudget.Global.Companion.GOAL_COLOR
import com.example.mybudget.Global.Companion.myDrawable
import com.example.mybudget.R
import com.example.mybudget.models.SubItem

class SubItemAdapter(private var subItemList: MutableList<SubItem>) : RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder>() {


    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): SubItemViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.sub_item_row, viewGroup, false)
        return SubItemViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(@NonNull subItemViewHolder: SubItemViewHolder, i: Int) {
        val subItem: SubItem = subItemList[i]
        subItemViewHolder.mSubItemImage.setImageResource(subItem.getSubItemImage())
        subItemViewHolder.mSubItemName.setText(subItem.getSubItemName())
        subItemViewHolder.mSubItemAmount.setText(subItem.getSubItemAmount().toString())
        subItemViewHolder.mSubItemTime.setText(subItem.getSubItemTime())
        subItemViewHolder.mSubItemMemo.setText(subItem.getSubItemMemo())
        subItemViewHolder.mSubItemImageLayout.setBackgroundResource(myDrawable(GOAL_COLOR))
    }

    override fun getItemCount(): Int {
        return subItemList.size
    }

    class SubItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var mSubItemImage: ImageView
        var mSubItemName: TextView
        var mSubItemAmount: TextView
        var mSubItemTime: TextView
        var mSubItemMemo: TextView
        var mSubItemImageLayout: LinearLayout

        init {
            mSubItemImage = itemView.findViewById(R.id.type)
            mSubItemName = itemView.findViewById(R.id.name)
            mSubItemAmount = itemView.findViewById(R.id.amount)
            mSubItemTime = itemView.findViewById(R.id.time)
            mSubItemMemo = itemView.findViewById(R.id.memo)
            mSubItemImageLayout = itemView.findViewById(R.id.image_layout)
        }
    }

    interface SubRecyclerViewClickListener {
        fun onRowClick(view: View?, position: Int)
    }
}
