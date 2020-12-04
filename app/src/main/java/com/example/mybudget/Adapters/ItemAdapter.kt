package com.example.mybudget.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget.R
import com.example.mybudget.models.Item
import com.example.mybudget.models.SubItem

class ItemAdapter(private var itemList: MutableList<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    val viewPool = RecyclerView.RecycledViewPool()

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull itemViewHolder: ItemViewHolder, i: Int) {
        itemViewHolder.setIsRecyclable(false)
        val item: Item = itemList[i]
        itemViewHolder.mItemDayOfMonth.setText(item.getItemDayOfMonth())
        itemViewHolder.mItemDayOfWeek.setText(item.getItemDayOfWeek())
        itemViewHolder.mItemMonthYear.setText(item.getItemMonthYear())
        itemViewHolder.mItemTotalAmount.setText(item.getSubItemTotalAmount())

        // Create layout manager with initial prefetch item count
        val layoutManager = LinearLayoutManager(
            itemViewHolder.mSubItemRecyclerView.getContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        layoutManager.setInitialPrefetchItemCount(item.getSubItemList()!!.size)

        // Create sub item view adapter
        val subItemAdapter = SubItemAdapter(item.getSubItemList()!!)
        itemViewHolder.mSubItemRecyclerView.setLayoutManager(layoutManager)
        itemViewHolder.mSubItemRecyclerView.setAdapter(subItemAdapter)
        itemViewHolder.mSubItemRecyclerView.setRecycledViewPool(viewPool)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mItemDayOfMonth: TextView
        val mItemDayOfWeek: TextView
        val mItemMonthYear: TextView
        val mItemTotalAmount: TextView
        val mSubItemRecyclerView: RecyclerView

        init {
            mItemDayOfMonth = itemView.findViewById(R.id.dd)
            mItemDayOfWeek = itemView.findViewById(R.id.day)
            mItemMonthYear = itemView.findViewById(R.id.MM_yyyy)
            mItemTotalAmount = itemView.findViewById(R.id.total_amount)
            mSubItemRecyclerView = itemView.findViewById(R.id.sub_item_rv)
        }
    }

}
