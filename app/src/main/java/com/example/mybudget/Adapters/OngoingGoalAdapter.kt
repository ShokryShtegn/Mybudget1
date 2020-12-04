package com.example.mybudget.Adapters;

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget.Global.Companion.daysLeft
import com.example.mybudget.R
import com.example.mybudget.models.Goal
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class OngoingGoalAdapter(goals: MutableList<Goal>, context: Context, listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<OngoingGoalAdapter.MyViewHolder?>(), Filterable {
    var goals: MutableList<Goal>
    var goalsFilter: MutableList<Goal>
    private val context: Context
    private val mListener: RecyclerViewClickListener
    var filter: OngoingGoalCustomFilter? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.ongoing_goal_row, parent, false)
        return MyViewHolder(view, mListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.run {
            mName.setText(goals[position].getName())
            mTargetDate.setText(goals[position].getTargetDate())
            mLeft.setText(daysLeft(goals[position].getTargetDate()) + " days left")
            mProgressBar.max = 100
            mProgressBar.progress = 67
        }
        val star: Int = goals[position].getStarred()
        if (star == 1) {
            holder.mStar.setImageResource(R.drawable.staron)
        } else {
            holder.mStar.setImageResource(R.drawable.starof)
        }
    }

    override fun getItemCount(): Int {
        return goals.size
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = OngoingGoalCustomFilter(goalsFilter as ArrayList<Goal>, this)
        }
        return filter as OngoingGoalCustomFilter
    }

    inner class MyViewHolder(itemView: View, listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val mListener: RecyclerViewClickListener
        val mName: TextView
        val mTargetDate: TextView
        val mLeft: TextView
        val mStar: ImageView
        val mProgressBar: ProgressBar
        private val mRowContainer: RelativeLayout
        override fun onClick(v: View) {
            when (v.id) {
                R.id.row_container -> mListener.onRowClick(mRowContainer, adapterPosition)
                R.id.star -> mListener.onStarClick(mStar, adapterPosition)
                else -> {
                }
            }
        }

        init {
            mName = itemView.findViewById(R.id.name)
            mTargetDate = itemView.findViewById(R.id.target_date)
            mLeft = itemView.findViewById(R.id.left)
            mStar = itemView.findViewById(R.id.star)
            mRowContainer = itemView.findViewById(R.id.row_container)
            mListener = listener
            mRowContainer.setOnClickListener(this)
            mStar.setOnClickListener(this)
            mProgressBar = itemView.findViewById(R.id.progres_bar)
        }
    }

    interface RecyclerViewClickListener {
        fun onRowClick(view: View?, position: Int)
        fun onStarClick(view: View?, position: Int)
    }

    init {
        this.goals = goals
        goalsFilter = goals
        this.context = context
        mListener = listener
    }
}