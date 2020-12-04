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
import com.example.mybudget.models.Goal
import java.util.ArrayList

class AchieveGoalAdapter (goals: MutableList<Goal>, context: Context, listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<AchieveGoalAdapter.MyViewHolder?>() {
    var goals: MutableList<Goal>
    var goalsFilter: MutableList<Goal>
    private val context: Context
    private val mListener: RecyclerViewClickListener
    //var filter: OngoingGoalCustomFilter? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.achieve_goal_row, parent, false)
        return MyViewHolder(view, mListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.run {
            mName.setText(goals[position].getName())
            mAmount.setText(goals[position].getTargetDate())
            mAchieveAt.setText(goals[position].getTargetDate())
        }
    }

    override fun getItemCount(): Int {
        return goals.size
    }


    inner class MyViewHolder(itemView: View, listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val mListener: RecyclerViewClickListener
        val mAmount: TextView
        val mName: TextView
        val mAchieveAt: TextView
        private val mRowContainer: RelativeLayout
        override fun onClick(v: View) {
            when (v.id) {
                R.id.row_container -> mListener.onRowClick(mRowContainer, adapterPosition)
                else -> {
                }
            }
        }

        init {
            mAmount = itemView.findViewById(R.id.goal_amount)
            mName = itemView.findViewById(R.id.name)
            mAchieveAt = itemView.findViewById(R.id.achieved_at)
            mRowContainer = itemView.findViewById(R.id.row_container)
            mListener = listener
            mRowContainer.setOnClickListener(this)
        }
    }

    interface RecyclerViewClickListener {
        fun onRowClick(view: View?, position: Int)
    }

    init {
        this.goals = goals
        goalsFilter = goals
        this.context = context
        mListener = listener
    }
}


