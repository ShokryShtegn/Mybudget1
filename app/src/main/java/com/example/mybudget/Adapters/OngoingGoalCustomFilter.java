package com.example.mybudget.Adapters;

import android.widget.Filter;

import com.example.mybudget.models.Goal;

import java.util.ArrayList;
import java.util.List;

public class OngoingGoalCustomFilter extends Filter {

    OngoingGoalAdapter adapter;
    ArrayList<Goal> filterList;

    public OngoingGoalCustomFilter(List<Goal> filterList, OngoingGoalAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList = (ArrayList<Goal>) filterList;
    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Goal> filteredGoals = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName1().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredGoals.add(filterList.get(i));
                }
            }

            results.count=filteredGoals.size();
            results.values=filteredGoals;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        //adapter.goals= (ArrayList<Goal>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();

    }
}