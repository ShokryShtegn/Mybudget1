package com.example.mybudget

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition
object GlobalVariable {
    var id_ui = 0
    var id_ui2 = 0
    var currSymbol = ""
    var currSymbol2 = ""

    var signPopUp_parent = 0
    var signPopUp_child = 0
    var signPopUp_seller = 0
    var signPopUp_nothing = 0

    var idUserInSeller = 0
    var imageData: Uri? = Uri.EMPTY
    var imageSeller: Uri? = Uri.EMPTY
    var emailSeller: String = ""
    var itemId: String = ""
    var sellerName: String = ""
    var sellerPass: String = ""
    var sellerAge: Int = 0
    var userAgeHome: Int = 0

    var userEmailA: String = ""
    var userNameA: String = ""
    var userPassA: String = ""
    var userAgeA: Int = 0
    var userTypeA: String = ""
    var userImageA: Uri? = Uri.EMPTY
    var userRelationA: String = ""
    var userRelationEmailA: String = ""
    var userEmailSign: String = ""
    var requestNot: Boolean = false
    var userEmailToRelation: String = ""
    var userNameRelation: String = ""
    var userEmailRelation: String = ""

    var user_id: Int = 0
    var userIdRelationFrom: Int = 0
    var userIdRelationTo: Int = 0
    var requestId: String = ""
    var userEmailReq: String = ""
    var userIdA: Int = 0
}
class MainActivity : AppCompatActivity() {
    lateinit var mSlideViewPager: ViewPager
    lateinit var mDotLayout: LinearLayout
    lateinit var sliderAdapter: SliderAdapter

    private lateinit var mNextBtn: Button
    private lateinit var mBackBtn: Button

    var b: Boolean = false
    var mCurrentPage: Int = 0
    private lateinit var mDots: Array<TextView?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*delete.setOnClickListener({
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
        })*/
        mSlideViewPager = findViewById(R.id.slideViewPager)
        mDotLayout = findViewById(R.id.dotsLayout)

        sliderAdapter = SliderAdapter(this)
        mSlideViewPager.adapter = sliderAdapter

        mNextBtn = findViewById(R.id.next) as Button
        mBackBtn = findViewById(R.id.back) as Button

        addDotsIndicator(0)
        mSlideViewPager.addOnPageChangeListener(viewListener)

        mNextBtn.setOnClickListener({
            if(mNextBtn.text.equals("Finish") && mCurrentPage == 2){
                val intent = Intent(this, Main::class.java)
                startActivity(intent)
            }
            mSlideViewPager.setCurrentItem(mCurrentPage + 1)
        })

            mBackBtn.setOnClickListener({
                mSlideViewPager.setCurrentItem(mCurrentPage - 1)
            })
    }
    fun addDotsIndicator(position: Int) {
        mDots = arrayOfNulls<TextView?>(3)
        mDotLayout.removeAllViews()
        for (i in 0 until mDots!!.size)
        {
            mDots!![i] = TextView(this)
            mDots!![i]?.setText(Html.fromHtml("&#8226;"))
            mDots!![i]?.setTextSize(35F)
            mDots!![i]?.setTextColor(resources.getColor(R.color.colorPrimary))
            mDotLayout.addView(mDots!![i])
        }
        if (mDots.size > 0)
        {
            mDots[position]?.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }
    var viewListener:ViewPager.OnPageChangeListener = object: ViewPager.OnPageChangeListener {
        override fun onPageScrolled(i:Int, v:Float, i1:Int) {
        }
        override fun onPageSelected(i:Int) {
            addDotsIndicator(i)
            mCurrentPage = i

            if(i == 0){
                mNextBtn.isEnabled = true
                mBackBtn.isEnabled = false
                mBackBtn.visibility = View.INVISIBLE

                mNextBtn.setText("Next")
                mBackBtn.setText("")
            } else if(i == mDots.size-1){
                mNextBtn.isEnabled = true
                mBackBtn.isEnabled = true
                mBackBtn.visibility = View.VISIBLE

                mNextBtn.setText("Finish")
                mBackBtn.setText("Back")
            } else{
                mNextBtn.isEnabled = true
                mBackBtn.isEnabled = true
                mBackBtn.visibility = View.VISIBLE

                mNextBtn.setText("Next")
                mBackBtn.setText("Back")
            }
        }
        override fun onPageScrollStateChanged(i:Int) {
        }
    }
}


