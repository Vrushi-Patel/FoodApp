package com.foodapp.UI.adapters

import UserOperations
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.activities.HomeActivity
import com.foodapp.models.Food
import com.squareup.picasso.Picasso

class CartAdapter(val items: MutableList<Food>, userOperation: UserOperations) :
    RecyclerView.Adapter<CartAdapter.FoodViewHolder>() {
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val card: LinearLayout = itemView.findViewById(R.id.item)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val data: TextView = itemView.findViewById(R.id.data)
        fun bind(food: Food) {
            price.text = "₹ " + food.price.toString()
            name.text = food.name
            data.text = food.toString()
            Picasso.with(context).load(food.url).into(imageView)
            card.setOnClickListener {
                val homeActivity = HomeActivity()
//                HomeActivity.food = food
                val intent = Intent(context, homeActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.product_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}