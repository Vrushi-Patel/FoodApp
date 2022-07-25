package com.foodapp.UI.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.activities.ProductActivity
import com.foodapp.room.entities.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation
import com.squareup.picasso.Picasso

class ProductIngredientAdapter(val items: List<Ingredient>) :
    RecyclerView.Adapter<ProductIngredientAdapter.FoodViewHolder>() {
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val card: LinearLayout = itemView.findViewById(R.id.item)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val data: TextView = itemView.findViewById(R.id.data)
        fun bind(food: Ingredient) {
            price.text = "₹ " + food.product.price.toString()
            name.text = food.product.name
            data.text = food.toString()
            Picasso.with(context).load(food.product.url).into(imageView)
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

class ProductSubProductAdapter(val items: List<FoodIngredientRelation>) :
    RecyclerView.Adapter<ProductSubProductAdapter.FoodViewHolder>() {
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val card: LinearLayout = itemView.findViewById(R.id.item)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val data: TextView = itemView.findViewById(R.id.data)
        fun bind(food: FoodIngredientRelation) {
            price.text = "₹ " + food.food.product.price.toString()
            name.text = food.food.product.name
            data.text = food.toString()
            Picasso.with(context).load(food.food.product.url).into(imageView)
            card.setOnClickListener {
                val productActivity = ProductActivity()
                ProductActivity.ingredient = null
                ProductActivity.food = food
                val intent = Intent(context, productActivity::class.java)
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