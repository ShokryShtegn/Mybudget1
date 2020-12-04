package com.example.mybudget.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.anychart.anychart.AnyChart
import com.anychart.anychart.DataEntry
import com.bumptech.glide.util.Util
import com.example.mybudget.DataBaseHandler
import com.example.mybudget.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.util.*
import kotlin.collections.ArrayList


class ChartFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        val UserIDLog = bundle?.getString("User_id")
        Toast.makeText(context, UserIDLog.toString(), Toast.LENGTH_SHORT).show()
        val view: View = inflater.inflate(R.layout.fragment_chart, container, false)
        val context = activity as Context
        val db = DataBaseHandler(context)
        val Determine1 = view.findViewById<Spinner>(R.id.determine1) as Spinner
        val Determine2 = view.findViewById<Spinner>(R.id.determine2) as Spinner
        val Month = view.findViewById<Spinner>(R.id.month) as Spinner
        val Year1 = view.findViewById<Spinner>(R.id.year1) as Spinner
        val Year2 = view.findViewById<Spinner>(R.id.year2) as Spinner
        val Show1 = view.findViewById<Button>(R.id.show1) as Button
        val Show2 = view.findViewById<Button>(R.id.show2) as Button
        val PChart = view.findViewById<PieChart>(R.id.pie_chart) as PieChart
        var result1_bar: String = ""
        var result2_bar: String = ""
        var result1_pie: String = ""
        var result2_pie: String = ""
        var month: String = ""
        var CategoryIncome = ArrayList<String>()
        var CategoryExpenses = ArrayList<String>()
        var January:Int = 0
        var February :Int = 0
        var March :Int = 0
        var April :Int = 0
        var May :Int = 0
        var June :Int = 0
        var July :Int = 0
        var August :Int = 0
        var September :Int = 0
        var October :Int = 0
        var November :Int = 0
        var December :Int = 0

        val D = resources.getStringArray(R.array.Determine)
        Determine1.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            D
        )
        Determine1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result1_bar = Determine2.selectedItem.toString()
                Toast.makeText(context, "Category is: " + result1_bar, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }

        Determine2.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            D
        )
        Determine2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                month = Determine2.selectedItem.toString()
                Toast.makeText(context, "Category is: " + month, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }

        val M = resources.getStringArray(R.array.Months)
        Month.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            M
        )
        Month.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result1_bar = Month.selectedItem.toString()
                Toast.makeText(context, "Category is: " + result1_bar, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }



        val years = ArrayList<String>()
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 2000..thisYear) {
            years.add(i.toString())
        }
        Year1.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            years
        )
        Year1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result2_bar = Year2.selectedItem.toString()
                Toast.makeText(context, "Category is: " + result2_bar, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }



        Year2.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            years
        )
        Year2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result2_pie= Year2.selectedItem.toString()
                Toast.makeText(context, "Category is: " + result2_pie, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "No Item Selected", Toast.LENGTH_SHORT).show()
            }
        }

        var dU = db.readData()
        for (j in 0..(dU.size - 1)) {
            if (UserIDLog != null) {
                if (dU[j].id == UserIDLog.toInt()) {
                    var data1 = db.readTransaction(UserIDLog.toInt())
                    if (data1.size == 0) {
                        Toast.makeText(
                            context,
                            "No Transactions for this user: " + UserIDLog,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else
                        for (i in 0..(data1.size - 1)) {
                            if(data1[i].type.equals(
                                    "Incomes",
                                    true
                                )
                            ) {
                                for (j in 0..(CategoryIncome.size - 1)) {
                                    if (!(CategoryIncome[j].equals(data1[i].category,true)) ) {
                                        CategoryIncome.add(data1[i].category)
                                        break
                                    }

                                }

                            }

                            else if(data1[i].type.equals(
                                    "Expenses",
                                    true
                                )
                            ) {
                                for (j in 0..(CategoryExpenses.size - 1)) {
                                    if (!(CategoryExpenses[j].equals(data1[i].category,true)) ) {
                                        CategoryExpenses.add(data1[i].category)
                                        break
                                    }

                                }

                            }
                        }

                }

            }
        }


        Show1.setOnClickListener({

            if (result1_bar.equals("Incomes", true)) {
                var dU = db.readData()
                for (j in 0..(dU.size - 1)) {
                    if (UserIDLog != null) {
                        if (dU[j].id == UserIDLog.toInt()) {
                            var Numbers = IntArray(CategoryIncome.size)
                            for (i in 0..Numbers.size - 1)
                                Numbers[i] = 0
                            var data1 = db.readTransaction(UserIDLog.toInt())
                            if (data1.size == 0) {
                                Toast.makeText(
                                    context,
                                    "No Transactions for this user: " + UserIDLog,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                for (i in 0..(data1.size - 1)) {
                                    var parts = (data1[i].date.split("-"))
                                    if (data1[i].type.equals(
                                            "Incomes",
                                            true
                                        ) && parts[1].equals(month, true) && parts[2].equals(
                                            result2_bar,
                                            true
                                        )
                                    ) {
                                        for (j in 0..(CategoryIncome.size - 1)) {
                                            if (data1[i].category.equals(CategoryIncome[j], true))
                                                Numbers[j] += data1[i].amount

                                        }

                                    }
                                }

                            }
                        }

                    }
                }
            }
            else if (result1_bar.equals("Expenses", true)) {
                var dU = db.readData()
                for (j in 0..(dU.size - 1)) {
                    if (UserIDLog != null) {
                        if (dU[j].id == UserIDLog.toInt()) {
                            var Numbers = IntArray(CategoryExpenses.size)
                            for (i in 0 .. Numbers.size-1)
                                Numbers[i] = 0
                            var data1 = db.readTransaction(UserIDLog.toInt())
                            if (data1.size == 0) {
                                Toast.makeText(
                                    context,
                                    "No Transactions for this user: " + UserIDLog,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                for (i in 0..(data1.size - 1)) {
                                    var parts = (data1[i].date.split("-"))
                                    if (data1[i].type.equals(
                                            "Expenses",
                                            true
                                        ) && parts[1].equals(month, true) && parts[2].equals(
                                            result2_bar,
                                            true
                                        )
                                    ) {
                                        for (j in 0..(CategoryExpenses.size - 1)) {
                                            if (data1[i].category.equals(CategoryExpenses[j], true))
                                                Numbers[j] += data1[i].amount

                                        }

                                    }
                                }
                            }



                        }

                    }
                }

            }

        })















        Show2.setOnClickListener({
            January = 0
            February = 0
            March = 0
            April = 0
            May = 0
            June = 0
            July = 0
            August = 0
            September = 0
            October = 0
            November = 0
            December = 0

            if (result1_pie.equals("Incomes", true)) {
                var dU = db.readData()
                for (j in 0..(dU.size - 1)) {
                    if (UserIDLog != null) {
                        if (dU[j].id == UserIDLog.toInt()) {
                            var data1 = db.readTransaction(UserIDLog.toInt())
                            if (data1.size == 0) {
                                Toast.makeText(
                                    context,
                                    "No Transactions for this user: " + UserIDLog,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                for (i in 0..(data1.size - 1)) {
                                    var parts = (data1[i].date.split("-"))

                                    if (data1[i].type.equals(
                                            "Incomes",
                                            true
                                        ) && parts[2].equals(result2_pie, true)
                                    ) {
                                        if (parts[1].equals("1"))
                                            January += data1[i].amount
                                        else if (parts[1].equals("2"))
                                            February += data1[i].amount
                                        else if (parts[1].equals("3"))
                                            March += data1[i].amount
                                        else if (parts[1].equals("4"))
                                            April += data1[i].amount
                                        else if (parts[1].equals("5"))
                                            May += data1[i].amount
                                        else if (parts[1].equals("6"))
                                            June += data1[i].amount
                                        else if (parts[1].equals("7"))
                                            July += data1[i].amount
                                        else if (parts[1].equals("8"))
                                            August += data1[i].amount
                                        else if (parts[1].equals("9"))
                                            September += data1[i].amount
                                        else if (parts[1].equals("10"))
                                            October += data1[i].amount
                                        else if (parts[1].equals("11"))
                                            November += data1[i].amount
                                        else if (parts[1].equals("12"))
                                            December += data1[i].amount


                                    }

                                }
                                setDataPie(
                                    PChart,
                                    January,
                                    February,
                                    March,
                                    April,
                                    May,
                                    June,
                                    July
                                );
                                break
                            }
                        }
                        break
                    }
                }


            } else if (result1_pie.equals("Expenses", true)) {
                var dU = db.readData()
                for (j in 0..(dU.size - 1)) {
                    if (UserIDLog != null) {
                        if (dU[j].id == UserIDLog.toInt()) {
                            var data1 = db.readTransaction(UserIDLog.toInt())
                            if (data1.size == 0) {
                                Toast.makeText(
                                    context,
                                    "No Transactions for this user: " + UserIDLog,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                for (i in 0..(data1.size - 1)) {
                                    var parts = (data1[i].date.split("-"))
                                    if (data1[i].type.equals(
                                            "Expenses",
                                            true
                                        ) && parts[2].equals(result2_pie, true)
                                    ) {
                                        if (parts[1].equals("1"))
                                            January += data1[i].amount
                                        else if (parts[1].equals("2"))
                                            February += data1[i].amount
                                        else if (parts[1].equals("3"))
                                            March += data1[i].amount
                                        else if (parts[1].equals("4"))
                                            April += data1[i].amount
                                        else if (parts[1].equals("5"))
                                            May += data1[i].amount
                                        else if (parts[1].equals("6"))
                                            June += data1[i].amount
                                        else if (parts[1].equals("7"))
                                            July += data1[i].amount
                                        else if (parts[1].equals("8"))
                                            August += data1[i].amount
                                        else if (parts[1].equals("9"))
                                            September += data1[i].amount
                                        else if (parts[1].equals("10"))
                                            October += data1[i].amount
                                        else if (parts[1].equals("11"))
                                            November += data1[i].amount
                                        else if (parts[1].equals("12"))
                                            December += data1[i].amount

                                    }

                                }
                                setDataPie(
                                    PChart,
                                    January,
                                    February,
                                    March,
                                    April,
                                    May,
                                    June,
                                    July
                                );
                                break
                            }
                        }
                        break
                    }
                }

            }
        })
        setDataPie(PChart,1,2,3,4,5,6,7)

        return view
    }






    private fun setDataPie(
        PChart: PieChart,
        January: Int,
        February: Int,
        March: Int,
        April: Int,
        May: Int,
        June: Int,
        July: Int
    ) {
        val p_chart: PieChart = PChart
        // Set the data and color to the pie chart
        p_chart.addPieSlice(
            PieModel(
                "January",
                January.toFloat(),
                Color.parseColor("#FFA726")
            )

        );
        p_chart.addPieSlice(
            PieModel(
                "February",
                February.toFloat(),
                Color.parseColor("#66BB6A")
            )
        );
        p_chart.addPieSlice(
            PieModel(
                "March",
                March.toFloat(),
                Color.parseColor("#EF5350")
            )
        );
        p_chart.addPieSlice(
            PieModel(
                "April",
                April.toFloat(),
                Color.parseColor("#29B6F6")
            )
        );
        p_chart.addPieSlice(
            PieModel(
                "May",
                May.toFloat(),
                Color.parseColor("#7B5B14")
            )
        );
        p_chart.addPieSlice(
            PieModel(
                "June",
                June.toFloat(),
                Color.parseColor("#D5D5D5")
            )
        );
        p_chart.addPieSlice(
            PieModel(
                "July",
                July.toFloat(),
                Color.parseColor("#000000")
            )
        );


        p_chart.startAnimation()

    }





}