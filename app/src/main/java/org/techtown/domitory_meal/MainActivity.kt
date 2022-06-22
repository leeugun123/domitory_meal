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
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.techtown.domitory_meal.databinding.ActivityMainBinding
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding
    private var mealList : ArrayList<MealData> = arrayListOf()//meal

    private val Time_array = listOf("아침","점심","저녁")


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


       doTask("https://www.gp.go.kr/supervisor/selectBbsNttList.do?bbsNo=509&key=2023")
       //기숙사 식사 메뉴 url

    }


    fun doTask(url : String) {

        val scope = GlobalScope

        //코루틴 비동기 적용
        scope.launch{

                //SSL 체크
                if(url.indexOf("https://") >= 0){
                    JsoupCrawlerExample.setSSL();
                }

                val document = Jsoup.connect(url).get()

                val entire_meal = document.select("tbody.text_center")
                //일주일간의 메뉴 가져오기

                for(i : Int in 0..6){

                    val day = entire_meal.select("tr")[i]//일요일 메뉴 가져오기


                    for(j: Int in 0..2){

                        val mealTime = day.select("td")[j]

                        val meal = mealTime.select("div.innerbox")
                        var descSplitList = meal.html().split("</span>").toTypedArray()//<span>기준으로 나누기

                        var descResult = ""

                        descSplitList.forEach {

                            var sort = it.replace("<span>","").replace("&lt;","").replace("&gt;","")
                            //쓸데없는 html 태그 제거

                            descResult += sort + "\n"
                        }


                        runOnUiThread {

                            mealList.add(MealData(Time_array[j],descResult))
                            val adapter = RecyclerMealAdapter(mealList)
                            mBinding.lstMeal.adapter = adapter
                        }


                    }
                }


        }

    }







}