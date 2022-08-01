package com.foodapp.UI.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.activities.ProductActivity
import com.foodapp.UI.viewmodels.FavouriteViewModel
import com.foodapp.room.relations.FoodIngredientRelation
import com.squareup.picasso.Picasso

class FavouriteAdapter(val viewModel: FavouriteViewModel) :
    RecyclerView.Adapter<FavouriteAdapter.FoodViewHolder>() {

    var items: List<FoodIngredientRelation> = listOf()
    fun setData(data: List<FoodIngredientRelation>) {
        items = data
        notifyDataSetChanged()
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context!!

        val name: TextView = itemView.findViewById(R.id.name)
        val data: TextView = itemView.findViewById(R.id.data)
        val price: TextView = itemView.findViewById(R.id.price)

        val imageView: ImageView = itemView.findViewById(R.id.image)

        private val imageBtn: ImageButton = itemView.findViewById(R.id.menuBtn)
        private val card: LinearLayout = itemView.findViewById(R.id.item)

        fun bind(food: FoodIngredientRelation, viewModel: FavouriteViewModel) {

            Picasso.with(context).load(food.food.product.url).into(imageView)

            name.text = food.food.product.name
            data.text = "Calories : ${food.food.product.calories}"
            price.text = "₹ ${food.food.product.price}"

            imageBtn.setOnClickListener {
                val popUpMenu: PopupMenu = PopupMenu(context, it)
                popUpMenu.menuInflater.inflate(R.menu.popup_menu, popUpMenu.menu)
                popUpMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_delete -> {
                            viewModel.delete(food)
                        }
                        R.id.action_update -> {
                            viewModel.update(food, context)
                        }
                        else -> {}
                    }
                    true
                }
                popUpMenu.show()
            }

            card.setOnClickListener {
                val productActivity = ProductActivity()
                ProductActivity.food = food
                ProductActivity.ingredient = null
                val intent = Intent(context, productActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.favourite_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position], viewModel)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}