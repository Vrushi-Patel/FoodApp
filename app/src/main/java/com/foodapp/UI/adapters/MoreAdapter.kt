package com.foodapp.UI.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.activities.ProductActivity
import com.foodapp.room.entities.Ingredient
import com.squareup.picasso.Picasso

class MoreAdapter :
    RecyclerView.Adapter<MoreAdapter.FoodViewHolder>() {
    var items: List<Ingredient> = listOf()
    fun setData(data: List<Ingredient>) {
        items = data
        notifyDataSetChanged()
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val name: TextView = itemView.findViewById(R.id.name)
        val imageView: ImageView = itemView.findViewById(R.id.productImage)
        val card: CardView = itemView.findViewById(R.id.item)
        fun bind(food: Ingredient) {
            Picasso.with(context).load(food.product.url).into(imageView)
            name.text = food.product.name
            card.setOnClickListener {
                val productActivity = ProductActivity()
                ProductActivity.ingredient = food
                ProductActivity.food = null
                val intent = Intent(context, productActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.more_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}