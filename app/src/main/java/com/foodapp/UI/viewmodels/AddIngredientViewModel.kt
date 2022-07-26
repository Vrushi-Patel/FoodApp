package com.foodapp.UI.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.AppClass
import com.foodapp.models.Food

class AddIngredientViewModel : ViewModel() {

    var ingredientList = app.repositoryFood.getAllIngredients(app.db)
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

    fun addProduct(food: com.foodapp.room.entities.Ingredient) {

        foodComposite.add(app.builder.factory.convertIngredient(food))
        foodComposite.returnCalories()
        foodComposite.returnPrice()

        foodLiveData.postValue(foodComposite)
    }

    fun removeProduct(food: com.foodapp.room.entities.Ingredient) {

        val product =
            foodComposite.ingredients.first {
                (food.product.name == it.name
                        && food.product.url == it.url
                        && food.product.calories == it.calories
                        && food.product.price == it.price)
            }

        product.let {
            foodComposite.ingredients.remove(it)
            foodComposite.returnCalories()
            foodComposite.returnPrice()

            foodLiveData.postValue(foodComposite)
        }
    }

    fun makeProduct() {
        foodComposite.url =
            "https://th.bing.com/th/id/R.4fbde595384ea1c9833df3c9468588be?rik=LU6gYxH7raAxIQ&riu=http%3a%2f%2fclipart-library.com%2fimg%2f1472879.png&ehk=5KNiNQZJeP9P6ldx9r2lleyi5Cixe72gJSafH%2fphAyU%3d&risl=&pid=ImgRaw&r=0"
        app.repositoryFood.insertFoodData(app.db, foodComposite)
        foodComposite = AddProductViewModel.app.builder.factory.makeIngredient { }
        foodLiveData.value = foodComposite
    }
}