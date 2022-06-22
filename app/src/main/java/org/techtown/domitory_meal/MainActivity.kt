package org.techtown.domitory_meal

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.techtown.domitory_meal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    private var mealList : ArrayList<MealData> = arrayListOf()//meal

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

       doTask("https://www.gp.go.kr/supervisor/selectBbsNttList.do?bbsNo=509&key=2023")

    }


    fun doTask(url : String) {

        val scope = GlobalScope

        scope.launch{

            try{

                val document = Jsoup.connect(url).get()

                val entire_meal = document.select("tbody.text_center")
                //일주일간의 메뉴 가져오기

                val sunday = entire_meal.select("tr")[0]//일요일 메뉴 가져오기

                val morning = sunday.select("td")[0]//아침
                val lunch = sunday.select("td")[1]//점심
                val dinner = sunday.select("td")[2]//저녁

                val meal = morning.select("div.innerbox")
                val descSplitList = meal.html().split("<span>").toTypedArray()//<span>기준으로 나누기

                var descResult = ""

                descSplitList.forEach {
                    descResult += it + "\n"
                }

                mealList.add(MealData(descResult))

                val adapter = RecyclerMealAdapter(mealList)
                mBinding.lstMeal.adapter = adapter

            }catch (e : Exception) {e.printStackTrace()}

        }

    }







}