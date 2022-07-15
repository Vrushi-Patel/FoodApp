package food_app_assignment.factory

import food_app_assignment.models.*

fun products(function: Foods.() -> Unit): MutableList<Product> {
    val products = mutableListOf<Product>()  // changed
    val foods = Foods().apply(function) as MutableList<Product>
    products.addAll(foods)
    return products
}

fun ingredients(function: Foods.() -> Unit): MutableList<Ingredient> {
    val ingredient = mutableListOf<Ingredient>()  // changed
    val foods = Foods().apply(function) as MutableList<Ingredient>
    ingredient.addAll(foods)
    return ingredient
}

interface FoodFactory {
    fun makeIngredient(
        function: Ingredient.() -> Unit
    ): Ingredient

    fun makeMeal(
        function: Meal.() -> Unit
    ): Meal

    fun makeProduct(
        function: Product.() -> Unit
    ): Product

    fun makeCombo(
        function: Meal.() -> Unit
    ): Meal
}

class FoodFactoryImpl : FoodFactory {
    override infix fun makeIngredient(function: Ingredient.() -> Unit): Ingredient {
        return Ingredient().apply(function)
    }

    override infix fun makeMeal(function: Meal.() -> Unit): Meal {
        val meal = Meal().apply(function)
        meal.isCombo = false
        return meal
    }

    override infix fun makeProduct(
        function: Product.() -> Unit
    ): Product {
        return Product().apply(function)
    }

    override infix fun makeCombo(function: Meal.() -> Unit): Meal {
        val meal = Meal().apply(function)
        meal.isCombo = true
        return meal
    }


}
