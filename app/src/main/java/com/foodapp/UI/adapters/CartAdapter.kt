package com.foodapp.UI.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.viewmodels.CartViewModel
import com.foodapp.room.relations.CartFoodRelation
import com.squareup.picasso.Picasso

class CartAdapter(val viewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.FoodViewHolder>() {
    var items: List<CartFoodRelation> = listOf()

    class FoodViewHolder(itemView: View, val viewModel: CartViewModel) :
        RecyclerView.ViewHolder(itemView) {

        val context: Context = itemView.context
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)

        private val card: LinearLayout = itemView.findViewById(R.id.item)
        private val btnAdd: ImageButton = itemView.findViewById(R.id.plusIcon)
        private val btnRemove: ImageButton = itemView.findViewById(R.id.minusIcon)
        private val count: TextView = itemView.findViewById(R.id.count)

        fun bind(food: CartFoodRelation) {
            price.text = "₹ " + food.food.product.price.toString()
            name.text = food.food.product.name
            count.text = food.cartData.quantity.toString()
            Picasso.with(context).load(food.food.product.url).into(imageView)
            btnAdd.setOnClickListener {
                viewModel.addToCart(food)
            }
            btnRemove.setOnClickListener {
                viewModel.removeFromCart(food)
            }
            card.setOnClickListener {
                viewModel.openItemPage(food, context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.cart_item, parent, false)
        return FoodViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(it: List<CartFoodRelation>) {
        items = it
        notifyDataSetChanged()
    }
}