package com.foodapp.models

import UserOperations


// Composite
// Has Operations
interface Food {
    var calories: Double
    var price: Double
    var name: String
    var url: String
    var ingredients: MutableList<Food>
    fun operation(): UserOperations
    fun returnPrice(): Double
    fun returnCalories(): Double
    fun getIterator(): MutableIterator<Food?>
    fun add(food: Food)
    fun remove(food: Food)
    fun getChild(index: Int): Food?
}

// Component & Leaf Both
class Ingredient : Food {
    override var url: String = ""
    override var ingredients: MutableList<Food> = mutableListOf()
    override fun operation(): UserOperations {
        return UserOperations()
    }

    override fun returnPrice(): Double {
        getIterator().forEach {
            price += (it?.price ?: 0.0)
        }
        return price
    }

    override fun returnCalories(): Double {
        getIterator().forEach {
            calories += (it?.calories ?: 0.0)
        }
        return calories
    }

    override fun getIterator(): FoodIterator {
        return FoodIterator()
    }

    override fun add(food: Food) {
        ingredients.add(food)
    }

    override fun remove(food: Food) {
        ingredients.remove(food)
    }

    override fun getChild(index: Int): Food {
        return ingredients[index]
    }


    inner class FoodIterator : MutableIterator<Food?> {
        var currentIndex = 0;
        override fun hasNext(): Boolean {
            return currentIndex < ingredients.size
        }

        override fun next(): Food? {
            println(" : -> $currentIndex")
            return ingredients[currentIndex++]
        }

        override fun remove() {
            ingredients.removeAt(--currentIndex)
        }
    }

    override var calories: Double = 0.0
    override var price: Double = 0.0
    override var name: String = ""
    override fun toString(): String {
        return "{ calories = $calories, price = $price, name = $name, url = $url }"
    }
}