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


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


       doTask("https://www.gp.go.kr/supervisor/selectBbsNttList.do?bbsNo=509&key=2023")
       //기숙사 식사 메뉴 url

        mBinding.update.setOnClickListener{

            doTask("https://www.gp.go.kr/supervisor/selectBbsNttList.do?bbsNo=509&key=2023")
        }

    }


    fun doTask(url : String) {

        mealList.clear()

        val scope = GlobalScope

        //코루틴 비동기 적용
        scope.launch{

                //SSL 체크
                if(url.indexOf("https://") >= 0){
                    JsoupCrawlerExample.setSSL();
                }//https:로 시작하는경우 setSSL() 실행하여 우회

                val document = Jsoup.connect(url).get()

                val entire_meal = document.select("tbody.text_center")
                //일주일간의 메뉴 가져오기


                for(i : Int in 0..6){

                    val day_meal = entire_meal.select("tr")[i]//하루 메뉴 가져오기

                    var date = day_meal.select("th")[0].select("div.innerbox").html().split("<br>").toTypedArray()//날짜 가져오기
                    var date_String = ""

                    date.forEach {

                        date_String += it.replace("<span","").replace("class=","").replace("</span>","")
                            .replace(">Today","").replace("today_icon","오늘의 메뉴!")
                        //쓸데없는 html 태그 제거

                    }


                    //아침 메뉴 가져오기
                    val breakfast = day_meal.select("td")[0]

                    var meal = breakfast.select("div.innerbox")
                    var descSplitList = meal.html().split("</span>").toTypedArray()//<span>기준으로 나누기

                    var descResult = ""

                    descSplitList.forEach {

                        var sort = it.replace("<span>","").replace("&lt;","").replace("&gt;","")
                            //쓸데없는 html 태그 제거

                        descResult += sort + "\n"
                    }

                    val breakfast_menu = descResult


                    //점심 메뉴 가져오기
                    val lunch = day_meal.select("td")[1]

                    meal = lunch.select("div.innerbox")
                    descSplitList = meal.html().split("</span>").toTypedArray()//<span>기준으로 나누기

                    descResult = ""

                    descSplitList.forEach {

                        var sort = it.replace("<span>","").replace("&lt;","").replace("&gt;","")
                        //쓸데없는 html 태그 제거

                        descResult += sort + "\n"
                    }

                    val lunch_menu = descResult


                    //저녁 메뉴 가져오기
                    val dinner = day_meal.select("td")[2]

                    meal = dinner.select("div.innerbox")
                    descSplitList = meal.html().split("</span>").toTypedArray()//<span>기준으로 나누기

                    descResult = ""

                    descSplitList.forEach {

                        var sort = it.replace("<span>","").replace("&lt;","").replace("&gt;","")
                        //쓸데없는 html 태그 제거

                        descResult += sort + "\n"
                    }

                    val dinner_menu = descResult


                    runOnUiThread {

                        mealList.add(MealData(date_String,breakfast_menu,lunch_menu,dinner_menu))
                        val adapter = RecyclerMealAdapter(mealList)
                        mBinding.lstMeal.adapter = adapter

                    }



                }


        }

    }







}