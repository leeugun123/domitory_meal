package org.techtown.domitory_meal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.meal.view.*
import org.techtown.domitory_meal.databinding.ActivityMainBinding.bind

class RecyclerMealAdapter(private val meals : ArrayList<MealData>) : RecyclerView.Adapter<RecyclerMealAdapter.ViewHolder>() {

    override fun getItemCount(): Int = meals.size

    override fun onBindViewHolder(holder: RecyclerMealAdapter.ViewHolder, position: Int) {

        val meal = meals[position]
        val listener = View.OnClickListener { it ->

        }

        holder.apply{
            bind(listener,meal)
            itemView.tag = meal
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMealAdapter.ViewHolder {

        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.meal,parent,false)
        return RecyclerMealAdapter.ViewHolder(inflatedView)

    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view : View = v

        fun bind(listener: View.OnClickListener, meal : MealData){
            view.meal_time.text = meal.mealTime
            view.menu.text = meal.meal

        }

    }

}