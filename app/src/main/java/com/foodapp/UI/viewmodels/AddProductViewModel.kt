package com.foodapp.UI.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.models.Food
import com.foodapp.models.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddProductViewModel : ViewModel() {
    var productList = app.repositoryFood.getAllProducts(app.db)
    var foodComposite: Food = app.builder.factory.makeIngredient { }

    val foodLiveData: MutableLiveData<Food> by lazy {
        MutableLiveData<Food>()
    }

    companion object {
        lateinit var app: AppClass
    }

    init {
        foodLiveData.value = foodComposite
    }

    fun addProduct(food: FoodIngredientRelation) {
        CoroutineScope(Dispatchers.Default).launch {

            val foodData = app.builder.factory.convertFood(food)
            addAllSubProducts(foodData, food)
            foodData.returnPrice()
            foodData.returnCalories()

            foodComposite.add(foodData)
            foodComposite.returnCalories()
            foodComposite.returnPrice()

            foodLiveData.postValue(foodComposite)
        }
    }

    fun removeProduct(food: FoodIngredientRelation) {
        CoroutineScope(Dispatchers.Default).launch {

            val foodData = app.builder.factory.convertFood(food)
            addAllSubProducts(foodData, food)
            foodData.returnCalories()
            foodData.returnPrice()

            val product =
                foodComposite.ingredients.first {
                    (food.food.product.name == it.name
                            && food.food.product.url == it.url
                            && food.food.product.calories == it.calories
                            && food.food.product.price == it.price)
                }

            product.let {
                foodComposite.remove(it)
                foodComposite.returnCalories()
                foodComposite.returnPrice()

                foodLiveData.postValue(foodComposite)
            }
        }
    }

    private fun addAllSubProducts(foodData: Ingredient, food: FoodIngredientRelation) {
        CoroutineScope(Dispatchers.Default).launch {

            val productsList: List<FoodIngredientRelation> =
                app.repositoryFood.getAllSubProducts(app.db, food.food.foodId!!)
                    .collect() as List<FoodIngredientRelation>

            productsList.forEach {

                val foodSubData = app.builder.factory.convertFood(it)
                addAllSubProducts(foodSubData, it)
                foodData.add(foodSubData)

                foodData.returnCalories()
                foodData.returnPrice()
            }

        }
    }

    fun makeProduct() {
        foodComposite.url =
            "https://th.bing.com/th/id/R.4fbde595384ea1c9833df3c9468588be?rik=LU6gYxH7raAxIQ&riu=http%3a%2f%2fclipart-library.com%2fimg%2f1472879.png&ehk=5KNiNQZJeP9P6ldx9r2lleyi5Cixe72gJSafH%2fphAyU%3d&risl=&pid=ImgRaw&r=0"
        app.repositoryFood.insertFoodData(app.db, foodComposite)
        foodComposite = app.builder.factory.makeIngredient { }
        foodLiveData.value = foodComposite
    }
}