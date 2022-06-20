package org.techtown.domitory_meal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        CoroutineScope(Dispatchers.Main).async {
            doTask("https://www.gp.go.kr/supervisor/selectBbsNttList.do?bbsNo=509&key=2023")
        }//비동기로 구현 , 크롤링



    }

    fun doTask(url : String){

        var breakfast_Menu : String = ""
        var lunch_Menu : String = ""
        var dinner_Menu : String = ""

        Single.fromCallable{

            try{

                val doc = Jsoup.connect("https://www.gp.go.kr/supervisor/selectBbsNttList.do?bbsNo=509&key=2023").get()

                val element : Elements = doc.select("div")




            }

        }


    }


}