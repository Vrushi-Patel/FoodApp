package com.foodapp.UI.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.models.Food
import com.foodapp.models.Ingredient
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddProductViewModel : ViewModel() {

    var productList: Flow<List<FoodIngredientRelation>>? = null

    var foodComposite: Food = app.builder.factory.makeIngredient { }
    var selectedItems: MutableList<Int> = mutableListOf()

    val selectedItemLiveData: MutableLiveData<MutableList<Int>> by lazy {
        MutableLiveData<MutableList<Int>>()
    }
    val foodLiveData: MutableLiveData<Food> by lazy {
        MutableLiveData<Food>()
    }

    companion object {
        lateinit var app: AppClass
    }

    init {
        foodLiveData.value = foodComposite
        selectedItemLiveData.value = selectedItems
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

            var product: Food? = null
            try {
                product = foodComposite.ingredients.first {
                    (food.food.product.name == it.name
                            && food.food.product.url == it.url
                            && food.food.product.calories == it.calories
                            && food.food.product.price == it.price)
                }
            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }

            product?.let {
                foodComposite.remove(it)
                foodComposite.returnCalories()
                foodComposite.returnPrice()

                foodLiveData.postValue(foodComposite)
            }
        }
    }

    private fun addAllSubProducts(foodData: Ingredient, food: FoodIngredientRelation) {
        CoroutineScope(Dispatchers.Default).launch {

            val productsList: List<FoodIngredientRelation>? =
                app.repositoryFood.getAllSubProducts(app.db, food.food.foodId!!)
                    .collect() as List<FoodIngredientRelation>?

            productsList?.let {
                productsList.forEach {
                    val foodSubData = app.builder.factory.convertFood(it)
                    addAllSubProducts(foodSubData, it)
                    foodData.add(foodSubData)

                    foodData.returnCalories()
                    foodData.returnPrice()
                }
            }
        }
    }

    fun getProducts(){
        productList =
            app.repositoryFood.getAllProducts(AddProductViewModel.app.db)
    }

    fun makeProduct() {
        foodComposite.url =
            "https://th.bing.com/th/id/R.4fbde595384ea1c9833df3c9468588be?rik=LU6gYxH7raAxIQ&riu=http%3a%2f%2fclipart-library.com%2fimg%2f1472879.png&ehk=5KNiNQZJeP9P6ldx9r2lleyi5Cixe72gJSafH%2fphAyU%3d&risl=&pid=ImgRaw&r=0"

        app.repositoryFavouriteFood.createFavouriteFood(app.repositoryFood, app.db, foodComposite)
        reset()
    }

    fun updateProduct(food: FoodIngredientRelation) {
        foodComposite.url =
            "https://th.bing.com/th/id/R.4fbde595384ea1c9833df3c9468588be?rik=LU6gYxH7raAxIQ&riu=http%3a%2f%2fclipart-library.com%2fimg%2f1472879.png&ehk=5KNiNQZJeP9P6ldx9r2lleyi5Cixe72gJSafH%2fphAyU%3d&risl=&pid=ImgRaw&r=0"

        app.repositoryFavouriteFood.updateFavouriteFood(
            app.repositoryFood,
            app.db, foodComposite, food
        )
        reset()
    }

    private fun reset() {
        foodComposite = AddIngredientViewModel.app.builder.factory.makeIngredient { }
        foodLiveData.value = foodComposite
    }

    suspend fun setFoodData(food: FoodIngredientRelation) {
        productList = app.repositoryFood.getAllProducts(app.db, food.food.foodId!!)
        app.repositoryFood.getAllSubProducts(app.db, food.food.foodId!!).collect {
            it?.let { i ->

                foodComposite = app.builder.factory.convertFood(food)
                foodComposite.returnPrice()
                foodComposite.returnCalories()

                i.forEach { d ->
                    addProduct(d)
                    selectedItems.add(d.food.foodId!!)
                }

                selectedItemLiveData.value = selectedItems
            }
        }
    }
}