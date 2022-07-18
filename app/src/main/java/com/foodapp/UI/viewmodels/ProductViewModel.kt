package com.foodapp.UI.viewmodels

import FoodFactoryImpl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.builder.FoodBuilderImpl
import com.foodapp.models.Food

class ProductViewModel : ViewModel() {
    val builder = FoodBuilderImpl(FoodFactoryImpl())

    private val _categoryList: MutableLiveData<MutableList<Food>> = MutableLiveData(mutableListOf())
    private val _moreList: MutableLiveData<MutableList<Food>> = MutableLiveData(mutableListOf())
    val categoryList: LiveData<MutableList<Food>>
        get() = _categoryList
    val moreList: LiveData<MutableList<Food>>
        get() = _moreList

    // Just Adding the Items to category list and more list statically.
    // Afterword the items are going to be added dynamically

    init {
        val categories = mutableListOf(
            builder.makeVegBurger(),
            builder.makeCoke(),
            builder.makeNonVegBurger(),
            builder.makeFries(),
            builder.makeIceCream(),
        )
        _categoryList.value?.addAll(categories)

        val moreItems = mutableListOf(
            builder.makeBurgerCombo(),
            builder.makeNonVegBurgerMeal(),
            builder.makeNonVegBurgerMeal(),
        )
        _moreList.value?.addAll(moreItems)

    }

}