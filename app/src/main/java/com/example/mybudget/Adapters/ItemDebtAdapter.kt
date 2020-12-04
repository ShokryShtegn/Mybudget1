package com.example.mybudget.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget.Global
import com.example.mybudget.R
import com.example.mybudget.models.Debt
import com.example.mybudget.models.Goal
import java.util.ArrayList

class ItemDebtAdapter(debts: MutableList<Debt>, context: Context, listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<ItemDebtAdapter.MyViewHolder?>() {
    var debts: MutableList<Debt>
    private val context: Context
    private val mListener: RecyclerViewClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.debt_row, parent, false)
        return MyViewHolder(view, mListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult", "ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.run {
            mName.setText(debts[position].getName())
            mAmount.setText(debts[position].getAmount().toString() + " " + debts[position].getCurrency().toString())
            if(debts[position].getType() == 0){
                mAmount.setTextColor(R.color.BLUE)
            } else{
                mAmount.setTextColor(R.color.RED)
            }
            mDescription.setText(debts[position].getDate())
        }
    }

    override fun getItemCount(): Int {
        return debts.size
    }

    inner class MyViewHolder(itemView: View, listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val mListener: RecyclerViewClickListener
        val mName: TextView
        val mAmount: TextView
        val mDescription: TextView
        var mLayout: LinearLayout
        private val mRowContainer: RelativeLayout
        override fun onClick(v: View) {
            when (v.id) {
                R.id.row_container -> mListener.onRowClick(mRowContainer, adapterPosition)
                else -> {
                }
            }
        }

        init {
            mName = itemView.findViewById(R.id.name)
            mAmount = itemView.findViewById(R.id.amount)
            mDescription = itemView.findViewById(R.id.description)
            mRowContainer = itemView.findViewById(R.id.row_container)
            mListener = listener
            mRowContainer.setOnClickListener(this)
            mLayout = itemView.findViewById(R.id.firstCharLayout)
        }
    }

    interface RecyclerViewClickListener {
        fun onRowClick(view: View?, position: Int)
    }

    init {
        this.debts = debts
        this.context = context
        mListener = listener
    }
}