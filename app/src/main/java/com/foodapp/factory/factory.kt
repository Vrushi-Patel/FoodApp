import com.foodapp.models.Ingredient

interface FoodFactory {
    fun makeIngredient(
        function: Ingredient.() -> Unit
    ): Ingredient
}

class FoodFactoryImpl : FoodFactory {
    override infix fun makeIngredient(function: Ingredient.() -> Unit): Ingredient {
        return Ingredient().apply(function)
    }
}
