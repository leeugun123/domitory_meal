package org.techtown.domitory_meal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.techtown.domitory_meal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding

    var meal_text : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        //mBinding!!.menuText?.text = "dddddd"

        CoroutineScope(Dispatchers.Main).async {

            val document = Jsoup.connect("https://www.gp.go.kr/supervisor/selectBbsNttList.do?bbsNo=509&key=2023").get()

            val entire_meal = document.select("tbody.text_center")

            val monday = entire_meal.select("tr")[0]
            val monday_meal = monday.select("div.innerbox")
            val descSplitList = monday_meal.html().split("<span>").toTypedArray()
            var descResult = ""
            descSplitList.forEach {
                descResult += it + "\n"
            }

            mBinding!!.menuText?.text = "dddddd"

        }//비동기로 구현 , 크롤링


    }

    /*
    fun doTask(url : String) {

        val document = Jsoup.connect(url).get()

        val day = document.select("div").select("tbody")


        val monday = day.select("tr").select("div.innerbox")

        val descSplitList = monday.html().split("<span>").toTypedArray()
        var descResult = ""

        descSplitList.forEach {
            descResult += it + "\n"
        }

        //mBinding?.menuText?.text = descResult
        mBinding!!.menuText?.text = "dddddd"

    }
*/






}