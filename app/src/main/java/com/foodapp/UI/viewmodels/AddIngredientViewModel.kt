package com.foodapp.UI.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.models.Food
import com.foodapp.room.relations.FoodIngredientRelation
import kotlinx.coroutines.flow.Flow

class AddIngredientViewModel : ViewModel() {

    var ingredientList: Flow<List<com.foodapp.room.entities.Ingredient>>? = null

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

    fun addProduct(food: com.foodapp.room.entities.Ingredient) {

        foodComposite.add(app.builder.factory.convertIngredient(food))
        foodComposite.returnCalories()
        foodComposite.returnPrice()

        foodLiveData.postValue(foodComposite)
    }

    fun removeProduct(food: com.foodapp.room.entities.Ingredient) {

        var product: Food? = null
        try {
            product = foodComposite.ingredients.first {
                (food.product.name == it.name
                        && food.product.url == it.url
                        && food.product.calories == it.calories
                        && food.product.price == it.price)
            }
        } catch (e: Exception) {
            Log.e("Error", e.toString())
        }

        product.let {
            foodComposite.ingredients.remove(it)
            foodComposite.returnCalories()
            foodComposite.returnPrice()

            foodLiveData.postValue(foodComposite)
        }
    }

    fun getProducts() {
        ingredientList = AddIngredientViewModel.app.repositoryFood.getAllIngredients(
            AddIngredientViewModel.app.db
        )
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
            app.db,
            foodComposite,
            food
        )
        reset()
    }

    private fun reset() {
        foodComposite = app.builder.factory.makeIngredient { }
        foodLiveData.value = foodComposite
    }

    suspend fun setFoodData(food: FoodIngredientRelation) {
        ingredientList = app.repositoryFood.getAllIngredientsWithBasic(
            AddProductViewModel.app.db,
            food.food.foodId!!
        )
        app.repositoryFood.getAllIngredients(AddProductViewModel.app.db, food.food.foodId!!)
            .collect {
                it?.let { i ->

                    foodComposite = app.builder.factory.convertFood(food)
                    foodComposite.returnPrice()
                    foodComposite.returnCalories()
                    foodLiveData.value = foodComposite

                    i.forEach { d ->
                        selectedItems.add(d.ingredientId!!)
                    }

                    selectedItemLiveData.value = selectedItems
                }
            }
    }
}