package com.foodapp.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.UI.viewmodels.AddIngredientViewModel
import com.foodapp.UI.viewmodels.AddProductViewModel
import com.foodapp.room.relations.FoodIngredientRelation
import com.squareup.picasso.Picasso
import com.foodapp.room.entities.Ingredient as ingredientsRoom

class IngredientSelectorAdapter : RecyclerView.Adapter<IngredientSelectorAdapter.FoodViewHolder>() {

    var items: List<*> = listOf<ingredientsRoom>()

    lateinit var viewModel: Any

    fun setData(data: List<*>, viewModel: Any) {

        items = data
        this.viewModel = viewModel

        notifyDataSetChanged()
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val context = itemView.context!!

        val name: TextView = itemView.findViewById(R.id.name)
        val data: TextView = itemView.findViewById(R.id.data)
        val price: TextView = itemView.findViewById(R.id.price)

        val imageView: ImageView = itemView.findViewById(R.id.image)

        private val card: LinearLayout = itemView.findViewById(R.id.item)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        fun bind(
            food: Any?,
            viewModel: Any?,
            resetList: () -> Unit
        ) {

            if (food is ingredientsRoom) {

                Picasso.with(context).load(food.product.url).into(imageView)

                name.text = food.product.name
                data.text = "Calories : ${food.product.calories}"
                price.text = "₹ ${food.product.price}"

                (viewModel as AddIngredientViewModel)
                checkBox.isChecked = viewModel.selectedItems.contains(food.ingredientId!!)

                card.setOnClickListener {

                    if (viewModel.selectedItems.contains(food.ingredientId!!)) {
                        viewModel.removeProduct(food)
                        viewModel.selectedItems.remove(food.ingredientId!!)
                    } else {
                        viewModel.addProduct(food)
                        viewModel.selectedItems.add(food.ingredientId!!)
                    }

                    resetList()
                }

            } else if (food is FoodIngredientRelation) {

                Picasso.with(context).load(food.food.product.url).into(imageView)

                name.text = food.food.product.name
                data.text = "Calories : ${food.food.product.calories}"
                price.text = "₹ ${food.food.product.price}"

                (viewModel as AddProductViewModel)
                checkBox.isChecked = viewModel.selectedItems.contains(food.food.foodId!!)

                card.setOnClickListener {

                    if (viewModel.selectedItems.contains(food.food.foodId!!)) {

                        viewModel.removeProduct(food)
                        viewModel.selectedItems.remove(food.food.foodId!!)

                    } else {

                        viewModel.addProduct(food)
                        viewModel.selectedItems.add(food.food.foodId!!)

                    }

                    resetList()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val context = parent.context

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.select_item, parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position], viewModel) { notifyDataSetChanged() }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}