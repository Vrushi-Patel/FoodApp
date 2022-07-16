package com.foodapp.UI.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.activities.HomeActivity
import com.squareup.picasso.Picasso
import com.foodapp.models.Food

class CategoryAdapter(val items: MutableList<Food>) :
    RecyclerView.Adapter<CategoryAdapter.FoodViewHolder>() {
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val card: CardView = itemView.findViewById(R.id.item)
        fun bind(food: Food) {
            Picasso.with(context).load(food.url).into(imageView)
            card.setOnClickListener {
                val homeActivity = HomeActivity()
                HomeActivity.setData(food)
                val intent = Intent(context, homeActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.category_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}